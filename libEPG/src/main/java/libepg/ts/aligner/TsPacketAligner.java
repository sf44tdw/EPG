/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.ts.aligner;

import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import libepg.ts.packet.TsPacket;
import libepg.ts.packet.TsPacket.ADAPTATION_FIELD_CONTROL;
import libepg.ts.packet.TsPacketParcel;
import libepg.ts.packet.TsPacketParcel.MISSING_PACKET_FLAG;
import epgtools.loggerfactory.LoggerFactory;

/**
 * トランスポートエラーインジケータの確認を行い、エラーパケットを除去する。<br>
 * 巡回カウンタによる連送パケットの確認を行い、連送パケットを除去する。<br>
 * 巡回カウンタによる欠落チェックを行い、直前のパケットが欠落していればフラグをセットする。<br>
 * (ただし、アダプテーションフィールドのみのパケットの巡回カウンタは常に0なので、そういうパケットだけを集める場合は欠落扱いにはしない。)<br>
 *
 * @author normal
 */
public final class TsPacketAligner {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, TsPacketAligner.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final int pid;

    public TsPacketAligner(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return pid;
    }

    /**
     * 追加メソッドの戻り値
     */
    public static enum ADD_RESULT {
        /**
         * 問題なし。
         */
        OK("問題なし"),
        /**
         * 巡回カウンタの範囲内に同一パケットあり。
         */
        DUPLICATION("エラー 巡回カウンタの範囲内に同一パケットあり。"),
        /**
         * トランスポートエラーインジケータに問題あり。<br>
         */
        ERROR_PACKET("エラー トランスポートエラーインジケータに問題あり。"),
        /**
         * pid相違<br>
         */
        DEFFERENT_PID("エラー pid相違。");

        private final String message;

        private ADD_RESULT(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return super.toString() + "{" + "message=" + message + '}';
        }

    }

    /**
     * 最終的な保管場所
     */
    private List<TsPacketParcel> result = new ArrayList<>();
    /**
     * カウンタが1巡するまでの一時保管場所
     */
    private LinkedList<TsPacketParcel> temp = new LinkedList<>();

    /**
     * カウンタが1巡するまでの一時保管場所から最終保管場所へのオブジェクトの移動と、カウンタが1巡するまでの一時保管場所の初期化を実施。
     */
    private synchronized void tempToResult() {
        this.result.addAll(temp);
        this.temp.clear();
    }
    /**
     * 巡回カウンタ範囲内での重複検知用。巡回カウンタ昇順でソート。
     */
    private final TreeSet<TsPacket> packetDic = new TreeSet<>((TsPacket o1, TsPacket o2) -> (o1.getContinuity_counter() - o2.getContinuity_counter()));

    /**
     * 巡回カウンタ範囲内での重複検知用オブジェクトの初期化を実施。
     */
    private synchronized void clearDic() {
        this.packetDic.clear();
    }

    private class ContinuityChecker {

        /**
         * 直前に保管したパケット
         */
        private TsPacket previousPacket = null;

        /**
         * 引数で渡されたパケットの直前のパケットが欠落していないか確認する。<br>
         * アダプテーションフィールドしかないパケットの巡回カウンタは全て0であるため、これらは欠落なしとみなす。<br>
         * 前回パケットがnullの時は、渡されたパケットの巡回カウンタが0以外であればその前が欠落しているとみなす。<br>
         * 前回パケットがnullではない、かつ前回パケットの巡回カウンタが15で、渡されたパケットの巡回カウンタが0であれば欠落なしとみなす<br>
         *
         * それ以外の時は、今回のパケットの巡回カウンタ=前回のパケットの巡回カウンタ+1でない場合、<br>
         * 今回のパケットの直前のパケットがいくつか(16*n+m-1)欠落していることになる。<br>
         * n>=0, mは今回のパケットのカウンタ値引く前回のパケットのカウンタ値。0以上15以下。<br>
         * 16の倍数個の欠落があった場合、感知できない。<br>
         *
         * ※1回呼ぶごとに直前パケットが変化するので、同じパケットに対しては1回しか実行できない。<br>
         *
         * @param TsPacket o チェックしたいパケット。
         * @return 直前のパケットが欠落していたらfalse
         * @throws IllegalArgumentException
         * アダプテーションフィールドのみのパケット。かつ、巡回カウンタ0以外のパケットが渡されたとき。
         */
        public synchronized boolean isNotMissing(TsPacket o) throws IllegalArgumentException {
            TsPacket t = o;
            boolean ret = false;
            CHECK:
            {
                if (t.getAdaptation_field_control() == ADAPTATION_FIELD_CONTROL.ONLY_ADAPTATION_FIELD) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("アダプテーションフィールドのみ。巡回カウンタは常に0");
                    }
                    if (t.getContinuity_counter() == 0) {
                        ret = true;
                        break CHECK;
                    } else {
                        MessageFormat msg = new MessageFormat("アダプテーションフィールドのみのパケット。かつ、巡回カウンタ0以外。 パケット={0}");
                        Object[] parameters = {t.toString()};
                        throw new IllegalArgumentException(msg.format(parameters));
                    }
                }

                if ((this.previousPacket == null)) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("前回パケットなし");
                    }
                    if (t.getContinuity_counter() == 0) {
                        ret = true;
                        break CHECK;
                    } else {
                        ret = false;
                        break CHECK;
                    }
                }

                if ((this.previousPacket.getAdaptation_field_control() == t.getAdaptation_field_control()) && (t.getAdaptation_field_control() == ADAPTATION_FIELD_CONTROL.ONLY_ADAPTATION_FIELD)) {
                } else {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("ペイロード有り");
                        LOG.trace("ペイロード有り(15の次は0)");
                    }
                    if (((this.previousPacket.getContinuity_counter() == 15) && (t.getContinuity_counter() == 0))) {
                        ret = true;
                        break CHECK;
                    } else {
                        ret = false;
                    }

                    if (LOG.isTraceEnabled()) {
                        LOG.trace("ペイロード有り(通常のカウントアップ。)");
                    }
                    if ((this.previousPacket.getContinuity_counter() == (t.getContinuity_counter() - 1))) {
                        ret = true;
                        break CHECK;
                    } else {
                        ret = false;
                    }
                }

            }
            if (LOG.isTraceEnabled()) {
                String s;
                if (this.previousPacket == null) {
                    s = "null";
                } else {
                    s = Integer.toString(this.previousPacket.getContinuity_counter());
                }
                MessageFormat msg = new MessageFormat("直前パケット巡回カウンタ={0} 今回パケット巡回カウンタ={1} 重複判定結果 = {2}");
                Object[] parameters = {s, o.getContinuity_counter(), ret};
                LOG.trace(msg.format(parameters));

            }
            this.previousPacket = t;
            return ret;
        }

    };
    private ContinuityChecker checker = new ContinuityChecker();

    /**
     * パケットの確認を行った後、保管場所に追加する。<br>
     *
     */
    public final synchronized ADD_RESULT add(TsPacket o) {

        if (LOG.isTraceEnabled()) {
            LOG.trace("引数nullチェック");
        }
        if (o == null) {
            throw new NullPointerException("渡されたパケットがnullです。");
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("トランスポートエラーインジケータチェック");
        }
        if (o.getTransport_error_indicator() == 1) {
            return ADD_RESULT.ERROR_PACKET;
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("pidチェック");
        }
        if (o.getPid() != this.getPid()) {
            return ADD_RESULT.DEFFERENT_PID;
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("パケット重複チェック");
        }
        if ((this.packetDic == null)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("初回なので重複チェックオブジェクトを初期化");
            }
            this.clearDic();
        }
        if ((this.packetDic.size() > 0) && (((this.packetDic.last().getContinuity_counter() == 15) && (o.getContinuity_counter() == 0)) || ((this.packetDic.last().getContinuity_counter() == 0) && (o.getContinuity_counter() == 0)))) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("カウントが1めぐりしたので重複チェックオブジェクトを初期化");
            }
            this.clearDic();
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("重複チェック");
        }
        if (!this.packetDic.contains(o)) {
            this.packetDic.add(o);
        } else {
            return ADD_RESULT.DUPLICATION;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("巡回カウンタチェックと追加");
        }
        TsPacketParcel tempParcel;
        if (this.checker.isNotMissing(o)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("直前欠落なし");
            }
            tempParcel = new TsPacketParcel(o, MISSING_PACKET_FLAG.NOT_MISSING);
        } else {
            if (LOG.isTraceEnabled()) {
                LOG.trace("直前欠落あり");
            }
            tempParcel = new TsPacketParcel(o, MISSING_PACKET_FLAG.MISSING_JUST_BEFORE);
        }
        if ((this.temp.size() > 0) && ((this.temp.getLast().getPacket().getContinuity_counter() == 15) || (o.getContinuity_counter() == 0))) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("カウントが1めぐりしたので一時保管オブジェクトから最終保管オブジェクトへ移動");
            }
            this.tempToResult();
        }
        this.temp.add(tempParcel);
        if (LOG.isTraceEnabled()) {
            LOG.trace("最終保管オブジェクト要素数=" + this.result.size());
            LOG.trace("保管中の最終カウンタ値=" + this.temp.getLast().getPacket().getContinuity_counter());
        }
        return ADD_RESULT.OK;
    }

    /**
     * 現在保管されているパケットをすべて返却し、このオブジェクトからはすべてのパケットを消去する。
     *
     * @return
     */
    public final synchronized List<TsPacketParcel> getPackets() {
        result.addAll(temp);
        if (LOG.isTraceEnabled()) {
            LOG.trace("最終保管オブジェクト要素数=" + this.result.size());
        }
        temp.clear();
        packetDic.clear();
        checker = new ContinuityChecker();
        List<TsPacketParcel> tempRes = new ArrayList<>();
        tempRes.addAll(result);
        result.clear();
        return Collections.unmodifiableList(tempRes);
    }

}
