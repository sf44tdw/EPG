package libepg.ts.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import libepg.ts.aligner.TsPacketAligner;
import org.apache.commons.logging.Log;
import libepg.ts.packet.TsPacket;
import libepg.ts.packet.TsPacketParcel;
import epgtools.loggerfactory.LoggerFactory;

/**
 * 指定されたpid(複数選択可)を持つTSパケットを読み込み、エラーパケットの除去を行った上で、個々のpidごとに分別する。
 * 渡された最後のパケットの巡回カウンタが0xf以外の場合でも、欠落扱いにはしない。
 *
 * @author normal
 */
public class TsReader {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, TsReader.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private static final int EOF = -1;

    private final File TSFile;

    private final Set<Integer> pids;

    private final Long readLimit;

    /**
     * ファイルの先頭から数えて指定されたパケット数だけ読み込む。
     *
     * @param TSFile TSファイル
     * @param pids 読み込みたいpid(プログラムID)
     * @param readLimit 読み込みパケット数の最大値。 EOFまで読む場合はnull
     * @throws java.io.FileNotFoundException TSファイルが見つからなかった場合。
     *
     */
    public TsReader(File TSFile, Set<Integer> pids, Long readLimit) throws FileNotFoundException {
        this.TSFile = new File(TSFile.getAbsolutePath());
        if (this.TSFile.isFile() == false) {
            throw new FileNotFoundException("TSファイルが見つかりません。");
        }
        Set<Integer> s = new HashSet<>();
        if (pids != null) {
            for (Iterator<Integer> ite = pids.iterator(); ite.hasNext();) {
                s.add(ite.next());
            }
        }
        this.pids = Collections.unmodifiableSet(s);
        if (this.pids.isEmpty()) {
            throw new IllegalArgumentException("プログラムID(PID)の指定がありません。");
        }

        if (readLimit != null) {
            this.readLimit = readLimit;
            if (this.readLimit < 0) {
                throw new IllegalArgumentException("読み込みパケット数の最大値が0未満です。");
            }
        } else {
            this.readLimit = null;
        }
    }

    /**
     * EOFまで全部読む。
     *
     * @param TSFile TSファイル
     * @param pids 読み込みたいpid(プログラムID)
     * @throws java.io.FileNotFoundException TSファイルが見つからなかった場合。
     *
     */
    public TsReader(File TSFile, Set<Integer> pids) throws FileNotFoundException {
        this(TSFile, pids, null);
    }

    /**
     * TSファイルをEOFまで読むか。
     *
     * @return EOFまで読むならtrue。
     */
    public boolean isReadEOF() {
        return this.readLimit == null;
    }

    /**
     * @return パケットの最大読み込み数。EOFまで読むことになっている場合、取得しようとすると例外が発生する。
     */
    public long getReadLimit() {
        return readLimit;
    }

    public synchronized Map<Integer, List<TsPacketParcel>> getPackets() {

        ByteBuffer packetBuffer = ByteBuffer.allocate(TsPacket.TS_PACKET_BYTE_LENGTH.PACKET_LENGTH.getByteLength());
        byte[] byteData = new byte[1];

        //pidごとにまとめられた作業用マップ
        Map<Integer, TsPacketAligner> temp_ret = new ConcurrentHashMap<>();
        this.pids.stream().forEach((next) -> {
            temp_ret.put(next, new TsPacketAligner(next));
        });

        FileInputStream fis = null;
        PushbackInputStream pis = null;
        try {

            fis = new FileInputStream(this.TSFile);
            pis = new PushbackInputStream(fis);

            boolean tipOfPacket = false;//パケット先端発見済みフラグ

            long count = 0;

            //パケットの先端が見つかるまで1バイトずつ読んでゆく。
            while (pis.read(byteData) != EOF) {

                if (packetBuffer.remaining() == 0) {
                    byte[] BeforeCutDown = packetBuffer.array();
                    byte[] AfterCutDown = new byte[packetBuffer.position()];
                    System.arraycopy(BeforeCutDown, 0, AfterCutDown, 0, AfterCutDown.length);
                    TsPacket tsp = new TsPacket(AfterCutDown);

                    if (LOG.isTraceEnabled()) {
                        LOG.trace("1パケット分読み込んだら、内容を専用オブジェクトにコピーしてパケットバッファを初期化 ");
                        LOG.trace(tsp.toString());
                    }
                    packetBuffer.clear();
                    tipOfPacket = false;

                    //ヘッダに問題がなく、必要なPIDのパケットのみを回収する。
                    if ((tsp.checkHeader()) && (this.pids.contains(tsp.getPid()))) {
                        TsPacketAligner tspa = temp_ret.get(tsp.getPid());
                        TsPacketAligner.ADD_RESULT resp = tspa.add(tsp);
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("追加結果=" + resp);
                        }
                    }
                    count++;
                    if (!this.isReadEOF() && count >= this.getReadLimit()) {
                        if (LOG.isInfoEnabled()) {
                            LOG.info("指定された数のパケットを読み込みました。読み込みを中断します。 指定されたパケット数 = " + this.getReadLimit() + " 読み込みパケット数 = " + count);
                        }
                        break;
                    }
                }

                //最初にパケットの開始を示す値が来るまで、読み込んだ値を保存しない。
                if ((byteData[0] == TsPacket.TS_SYNC_BYTE) && (tipOfPacket == false)) {
                    tipOfPacket = true;
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("パケットの頭を読み取ったので、ストリームの読み込みを1バイト前に戻す。");
                    }
                    pis.unread(byteData);
                }

                if (tipOfPacket == true) {
                    byte[] tsPacketData = new byte[TsPacket.TS_PACKET_BYTE_LENGTH.PACKET_LENGTH.getByteLength()];
                    if (pis.read(tsPacketData) != EOF) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("パケットの頭を発見済みの場合、パケットバッファに、現在の位置からパケット長分の値を読み込む。");
                        }
                        packetBuffer.put(tsPacketData);
                    } else {
                        break;
                    }
                }

            }
            pis.close();
            fis.close();

        } catch (FileNotFoundException e) {
            LOG.fatal("ファイルが見つかりません。", e);
        } catch (IOException e) {
            LOG.fatal("ファイル読み込みに失敗。", e);
        }

        //作業用マップを戻り値用マップに変換
        Map<Integer, List<TsPacketParcel>> ret = new ConcurrentHashMap<>();
        // キーと値をループ
        temp_ret.entrySet().stream().forEach((entry) -> {
            // キーを取得
            Integer key = entry.getKey();
            // 値を取得
            List<TsPacketParcel> val = entry.getValue().getPackets();
            ret.put(key, Collections.unmodifiableList(val));
        });
        return Collections.unmodifiableMap(ret);
    }

}
