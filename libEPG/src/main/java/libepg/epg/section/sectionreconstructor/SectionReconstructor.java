/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.sectionreconstructor;

import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import libepg.epg.section.Section;
import libepg.epg.section.Section.CRC_STATUS;
import libepg.epg.section.TABLE_ID;
import libepg.ts.packet.TsPacket;
import libepg.ts.packet.TsPacketParcel;
import epgtools.loggerfactory.LoggerFactory;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;

/**
 * 指定したpidを持つTSパケットを受け取る。
 *
 * TSパケットのペイロードからセクションを取り出して分割、再結合し、専用のオブジェクトに格納する。
 * payload_unit_start_indicatorが1の場合、TSヘッダ4バイトの次に(そのペイロード内における最初の)セクションの開始位置を示すポインターフィールド(1バイト)がある。
 *
 * 1:payload_unit_start_indicatorが1、かつポインタフィールドが0であれば、ペイロードの2バイト目から数えて、次にpayload_unit_start_indicatorが1のパケットが来るまでが1セクション。<br>
 * 2:payload_unit_start_indicatorが1、かつポインタフィールドが0以外であれば、ペイロードの2バイト目から数えてポインタフィールドの値バイトまでが前のセクションの一部、<br>
 * ポインタフィールドの値バイト+1からペイロードの終端まで次のセクションの先頭。<br>
 * 3:payload_unit_start_indicatorが0であれば、そのペイロードにはセクションの先頭は存在しない。<br>
 *
 * payload_unit_start_indicatorが0の場合はポインターフィールドが無く、 ペイロード内にセクションの先頭が無い
 *
 *
 * PID の値が望みの PSI のものかを調べる payload_unit_start_indicator を見て先頭パケットを探す
 * adaptation_field_control と pointer_field を見て data_byte が何バイト目から始まるか調べる
 * 次にpayload_unit_start_indicator が 1 になるまで payload をバッファしておいて、次の先頭パケットの
 * pointer_field 分を取り込んで返す
 *
 * @author normal
 */
public final class SectionReconstructor {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, SectionReconstructor.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final List<TsPacketParcel> parcels;
    private final int pid;

    /**
     * @param parcels 欠落チェック済みのパケット
     * @param pid 特定のpid
     * @throws IllegalArgumentException 設定されたpid以外のpidを持つパケットが混じっていた場合。
     */
    public SectionReconstructor(List<TsPacketParcel> parcels, int pid) throws IllegalArgumentException {
        this.pid = pid;
        List<TsPacketParcel> tempParcels = new ArrayList<>();
        tempParcels.addAll(parcels);
        for (TsPacketParcel parcel : tempParcels) {
            if (this.pid == parcel.getPacket().getPid()) {
            } else {
                MessageFormat WRONG_PID = new MessageFormat("指定されたPid以外のPidを持つパケットが混じっています。指定されたPid={0} パケット={1}");
                Object[] parameters = {Integer.toHexString(this.getPid()), parcel.getPacket().toString()};
                throw new IllegalArgumentException(WRONG_PID.format(parameters));
            }
        }
        this.parcels = Collections.unmodifiableList(tempParcels);
    }

    public int getPid() {
        return pid;
    }

    /**
     * getSectionByteArrays()で取得したバイト列のリストをセクションのリストに変換する。CRCエラーのあるものはリストに追加しない。
     *
     * @return セクションのリスト。
     */
    public synchronized Set<Section> getSections() {
        Set<byte[]> section_byte = this.getSectionByteArrays();
        Set<Section> ret = new HashSet<>();
        for (byte[] b : section_byte) {
            final Section sec;
            Section s_t = null;
            try {
                s_t = new Section(b);
            } catch (IllegalArgumentException e) {
                LOG.warn("セクションの構築中に問題が発生しました。このセクションは無視されます。", e);
                s_t = null;
            } finally {
                sec = s_t;
            }
            if (sec != null) {
                if (sec.checkCRC() == CRC_STATUS.NO_CRC_ERROR) {
                    ret.add(sec);
                } else {
                    LOG.warn("CRCが正しくありません。このセクションは無視されます。");
                }
            }
        }
        return Collections.unmodifiableSet(ret);
    }

    /**
     * セクションのバイト列を再構成し、リスト化する。 既に再構成されている同一のセクションがあった場合はリストに追加しない。
     *
     * @return セクションを表すバイト列のリスト
     *
     */
    public synchronized Set<byte[]> getSectionByteArrays() {

        Set<byte[]> ret = new TreeSet<>((byte[] left, byte[] right) -> {
            for (int i = 0, j = 0; i < left.length && j < right.length; i++, j++) {
                int a = (left[i] & 0xff);
                int b = (right[j] & 0xff);
                if (a != b) {
                    return a - b;
                }
            }
            return left.length - right.length;
        });

        boolean first_start_indicator_found = false;

        ByteBuffer buf = null;

        PayLoadSplitter splitter = new PayLoadSplitter();

        for (TsPacketParcel parcel : parcels) {

            splitter.setPacket(parcel.getPacket());
            Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> t_map = splitter.getSplittedPayLoad();

            if ((buf == null) || (parcel.isMissingJustBefore() == TsPacketParcel.MISSING_PACKET_FLAG.MISSING_JUST_BEFORE)) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("直前のパケットが欠落していたか、バッファの初期化がなされていない場合、バッファを初期化する。");
                }
                if (buf == null) {
                    buf = ByteBuffer.allocate(TABLE_ID.MAX_SECTION_LENGTH.BYTE_4093.getMaxSectionLength());
                } else {
                    buf.clear();
                }
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("バッファ=" + Hex.encodeHexString(buf.array()));
            }
            if ((first_start_indicator_found == false) && (parcel.getPacket().getPayload_unit_start_indicator() == TsPacket.PAYLOAD_UNIT_START_INDICATOR.START_PES_OR_START_SECTION)) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("最初のセクション開始パケットを発見。");
                }
                first_start_indicator_found = true;
            }

            if (first_start_indicator_found == false) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("最初のセクション開始パケットを発見するまでは読み飛ばす。");
                }
            } else if (parcel.getPacket().getPayload_unit_start_indicator() == TsPacket.PAYLOAD_UNIT_START_INDICATOR.START_PES_OR_START_SECTION) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("セクションの先頭パケットの場合、ペイロードの必要な部分をバッファに格納");
                }
                if (buf.position() == 0) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("バッファサイズが0=前のセクションの一部は残っていない。->ペイロードの、2バイト目から最後まで、もしくは、ポインタフィールドの値バイト+1から最後までをバッファにコピー");
                    }
                    byte[] temp_array = null;
                    if (t_map.size() == 1) {
                        temp_array = t_map.get(PayLoadSplitter.PAYLOAD_PART_KEY.PAYLOAD_AFTER_2_BYTE);
                    } else {
                        temp_array = t_map.get(PayLoadSplitter.PAYLOAD_PART_KEY.NEXT_POINTER);
                    }
                    buf.put(temp_array);
                } else {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("バッファサイズが0ではない=前のセクションの一部は残っている。->ペイロードの、2バイト目からポインタフィールドの値バイトまでをバッファにコピーし、セクションを構築。\n完成したセクションを取り出してバッファをクリアした後、存在すればポインタフィールドの値バイト+1から最後までをバッファにコピー");
                    }
                    byte[] temp_array = null;
                    if (t_map.containsKey(PayLoadSplitter.PAYLOAD_PART_KEY.PAYLOAD_AFTER_2_BYTE)) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("前のパケットのペイロードのちょうど最後でセクションが終わっていた場合。");
                        }

                        this.addToReturnObject(buf, ret);

                        buf.clear();

                        temp_array = t_map.get(PayLoadSplitter.PAYLOAD_PART_KEY.PAYLOAD_AFTER_2_BYTE);
                        buf.put(temp_array);
                    } else {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("前のパケットのペイロードのちょうど最後でセクションが終わっていなかった場合。");
                        }
                        temp_array = t_map.get(PayLoadSplitter.PAYLOAD_PART_KEY.PREV_POINTER);
                        buf.put(temp_array);

                        this.addToReturnObject(buf, ret);

                        buf.clear();

                        temp_array = t_map.get(PayLoadSplitter.PAYLOAD_PART_KEY.NEXT_POINTER);
                        buf.put(temp_array);
                    }
                }
            } else {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("セクションの先頭パケットではない。->ポインタフィールドがない->ペイロードの内容すべてをバッファにコピー。");
                }
                buf.put(t_map.get(PayLoadSplitter.PAYLOAD_PART_KEY.ALL_PAYLOAD));
            }
        }

        return Collections.unmodifiableSet(ret);
    }

    /**
     * バッファからデータだけ抜き出して、コレクションに格納する。重複したものは無視する。
     * ByteBufferのarray()をそのまま使うと、無条件でバッファ長分の配列を受け取ることになる。
     *
     */
    private synchronized void addToReturnObject(ByteBuffer buf, Set<byte[]> dest) {
        byte[] BeforeCutDown = buf.array();
        byte[] AfterCutDown = new byte[buf.position()];
        System.arraycopy(BeforeCutDown, 0, AfterCutDown, 0, AfterCutDown.length);
        if (LOG.isTraceEnabled()) {
            MessageFormat msg1 = new MessageFormat("\n切り詰め前={0}\n切り詰め後={1}");
            Object[] parameters1 = {Hex.encodeHexString(BeforeCutDown), Hex.encodeHexString(AfterCutDown)};
            LOG.trace(msg1.format(parameters1));
        }
        if (dest.add(AfterCutDown)) {
            if (LOG.isTraceEnabled()) {
                MessageFormat msg2 = new MessageFormat("\n追加されたバイト列={0}");
                Object[] parameters2 = {Hex.encodeHexString(AfterCutDown)};
                LOG.trace(msg2.format(parameters2));
            }
        } else if (LOG.isTraceEnabled()) {
            MessageFormat msg3 = new MessageFormat("\n重複のため無視されたバイト列={0}");
            Object[] parameters3 = {Hex.encodeHexString(AfterCutDown)};
            LOG.trace(msg3.format(parameters3));
        }
    }

}
