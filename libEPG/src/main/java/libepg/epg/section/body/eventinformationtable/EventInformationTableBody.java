package libepg.epg.section.body.eventinformationtable;


import libepg.util.bytearray.ByteConverter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import libepg.epg.section.SectionBody;
import libepg.util.bytearray.ByteArraySplitter;
import org.apache.commons.codec.binary.Hex;


/**
 * イベント情報テーブル（EIT）（Event Information Table）
 */
public final class EventInformationTableBody extends SectionBody {

    public EventInformationTableBody(SectionBody sectionBody) {
        super(sectionBody);
    }

    /**
     * service_id(サービス識別):これは16ビットのフィールドで、
     * そのトランスポートストリーム内の他のサービスからこのサービスを識別するためのラベルの役割をする。
     * サービス識別は、対応するプログラムマップセクション内の放送番組番号識別（program_number）に等しい。
     */
    public final synchronized int getService_id() {
        byte[] t = new byte[2];
        System.arraycopy(this.getData(), 0, t, 0, t.length);
        int temp = ByteConverter.bytesToInt(t);
        return temp;
    }

    /**
     * 符号化ビットストリームを定義する項の中で使用する場合、その値が将来ISO で定義される拡張子として使用されるかもしれないことを表す。 2bit
     *
     * @return 3
     */
    public final synchronized int getReserved1() {
        byte t = this.getData()[2];
        int temp = ByteConverter.byteToInt(t);
        temp = temp >>> 6;
        return temp;
    }

    /**
     * version_number(バージョン番号):この5 ビットのフィールドはサブテーブルのバージョン番号である。
     * バージョン番号はサブテーブル内の情報に変化があった場合に1 加算される。 その値が31 になった場合は、その次は0 に戻る。
     * カレントネクスト指示が「1」の場合は、バージョン番号はテーブル識別とサービス識別で定義される現在有効なサブテーブルのバージョン番号になる。
     * カレントネクスト指示が「0」の場合は、バージョン番号はテーブル識別とサービス識別で定義される次に有効なサブテーブルのバージョン番号になる。
     */
    public final synchronized int getVersion_number() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[2]);
        temp = temp & 0x3E;
        temp = temp >>> 1;
        return temp;
    }

    /**
     * current_next_indicator(カレントネクスト指示):この1ビットの指示は、
     * それが「1」の場合はサブテーブルが現在のサブテーブルであることを示す。
     * 「0」の場合は、送られるサブテーブルはまだ適用されず、次のサブテーブルとして使用されることを示す。
     */
    public final synchronized int getCurrent_next_indicator() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[2]);
        temp = temp & 0x1;
        return temp;
    }

    /**
     * section_number(セクション番号):この8 ビットのフィールドはセクションの番号を表す。
     * サブテーブル中の最初のセクションのセクション番号は、0x00 である。
     * セクション番号は、同一のテーブル識別、サービス識別、トランスポートストリーム識別、オリジナルネットワーク識別を持つセクションの追加ごとに1
     * 加算される。 この場合、サブテーブルはいくつかのセグメントとして構築してもよい。 各セグメント内では、セクションの追加ごとにセクション番号を1
     * 加算させるが、セグメントの最終セクションと隣接するセグメントの最初のセクションとの間では、番号の隙間があってもよい。
     */
    public final synchronized int getSection_number() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[3]);
        return temp;
    }

    /**
     * last_section_number(最終セクション番号):この8ビットのフィールドは、 そのセクションが属するサブテーブルの最後のセクション
     * (すなわち、最大のセクション番号を持つセクション)の番号を規定する。
     */
    public final synchronized int getLast_section_number() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[4]);
        return temp;
    }

    /**
     * transport_stream_id(トランスポートストリーム識別): これは16 ビットのフィールドで、 EIT
     * が示すこのトランスポートストリームをその分配システム内の他の多重から識別するラベルの役割をする。
     */
    public final synchronized int getTransport_stream_id() {
        byte[] t = new byte[2];
        System.arraycopy(this.getData(), 5, t, 0, t.length);
        int temp = ByteConverter.bytesToInt(t);
        return temp;
    }

    /**
     * original_network_id(オリジナルネットワーク識別): この16
     * ビットのフィールドは、元の分配システムのネットワーク識別を規定するラベルの役割をする。
     */
    public final synchronized int getOriginal_network_id() {
        byte[] t = new byte[2];
        System.arraycopy(this.getData(), 7, t, 0, t.length);
        int temp = ByteConverter.bytesToInt(t);
        return temp;
    }

    /**
     * segment_last_section_number(セグメント最終セクション番号)この8 ビットのフィールドは、
     * サブテーブルのこのセグメントの最後のセクションの番号を規定する。
     * 分割されないサブテーブルでは、このフィールドは最終セクション番号(last_section_number)フィールドと同一の値に設定しなければならない。
     */
    public final synchronized int getSegment_last_section_number() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[9]);
        return temp;
    }

    /**
     * last_table_id(最終テーブル識別):この8 ビットのフィールドは、使用されている最終のテーブル識別を示す。 使用されるテーブルが1
     * 個のみの場合は、このフィールドにはこのテーブルのテーブル識別を設定する。 連続したテーブル識別値に渡って情報は時系列順でなければならない。。
     */
    public final synchronized int getLast_table_id() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[10]);
        return temp;
    }

    /**
     * 繰り返し項目のバイト列
     *
     * @return
     */
    public final synchronized byte[] getRepeatingPart() {
        byte[] t = new byte[this.getData().length - 11];
        System.arraycopy(this.getData(), 11, t, 0, t.length);
        return t;
    }

    /**
     * 分割済みの繰り返し項目。
     *
     * @return
     */
    public final synchronized List<EventInformationTableRepeatingPart> getEITRepeatingPartList() {
        List<byte[]> t = ByteArraySplitter.splitByLengthField(this.getRepeatingPart(), 11, 2, (x) -> x & 0x0FFF);
        List<EventInformationTableRepeatingPart> dest = new ArrayList<>();
        for (byte[] temp : t) {
            dest.add(new EventInformationTableRepeatingPart(temp));
        }
        return Collections.unmodifiableList(dest);
    }
    private static final MessageFormat TABLE_DESC = new MessageFormat(
            "{0}\n"
            + "サービス識別 = {1}\n"
            + "予約1 = {2}\n"
            + "バージョン番号 = {3}\n"
            + "カレントネクスト指示 = {4}\n"
            + "セクション番号 = {5}\n"
            + "最終セクション番号 = {6}\n"
            + "トランスポートストリーム識別 = {7}\n"
            + "オリジナルネットワーク識別 = {8}\n"
            + "セグメント最終セクション番号 = {9}\n"
            + "最終テーブル識別 = {10}\n"
            + "繰り返し項目のバイト列 = {11}\n"
            + "繰り返し項目={12}\n"
    );

    @Override
    /**
     * このクラスで取得できるフィールドの内容を表示する。
     */
    public String toString() {
        List<EventInformationTableRepeatingPart> l = this.getEITRepeatingPartList();
        StringBuilder s = new StringBuilder();
        for (EventInformationTableRepeatingPart eitrp : l) {
            s.append(eitrp);
        }
        Object[] parameters = {
            super.toString(),
            Integer.toHexString(this.getService_id()),
            Integer.toHexString(this.getReserved1()),
            this.getVersion_number(),
            this.getCurrent_next_indicator(),
            this.getSection_number(),
            this.getLast_section_number(),
            this.getTransport_stream_id(),
            this.getOriginal_network_id(),
            this.getSegment_last_section_number(),
            this.getLast_table_id(),
            Hex.encodeHexString(this.getRepeatingPart()),
            s};
        return TABLE_DESC.format(parameters);
    }

}
