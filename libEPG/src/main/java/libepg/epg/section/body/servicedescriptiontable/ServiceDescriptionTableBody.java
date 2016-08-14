/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.body.servicedescriptiontable;

import libepg.util.bytearray.ByteConverter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import libepg.epg.section.SectionBody;
import libepg.util.bytearray.ByteArraySplitter;
import org.apache.commons.codec.binary.Hex;

/**
 * サービス記述テーブルSDT（Service Description Table）のセクション本体<br>
 * 編成チャンネルの名称、放送事業者の名称など、編成チャンネルに関する情報を伝送する。<br>
 */
public final class ServiceDescriptionTableBody extends SectionBody {

    public ServiceDescriptionTableBody(SectionBody sectionBody) {
        super(sectionBody);
    }

    /**
     * トランスポートストリーム識別<br>
     * 16bit。<br>
     * SDTが示すトランスポートストリームをその分配システム内の他の多重から識別するラベルの役割をする<br>
     *
     * @return
     */
    public final synchronized int getTransport_stream_id() {
        byte[] t = new byte[2];
        System.arraycopy(this.getData(), 0, t, 0, t.length);
        return ByteConverter.bytesToInt(t);
    }

    /**
     * 符号化ビットストリームを定義する項の中で使用する場合、その値が将来ISO で定義される拡張子として使用されるかもしれないことを表す。 2bit。
     *
     * @return
     */
    public final synchronized int getReserved2() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[2]);
        temp = temp >>> 6;
        return temp;
    }

    /**
     * version_number(バージョン番号):この5ビットのフィールドはサブテーブルのバージョン番号である。
     * バージョン番号はサブテーブル内の情報に変化があった場合に1加算される。
     * その値が31になった場合は、その次は0に戻る。カレントネクスト指示が「1」の場合は、バージョン番号はテーブル識別とネットワーク識別で定義される現在のサブテーブルのバージョン番号になる。
     * カレントネクスト指示が「0」の場合は、バージョン番号はテーブル識別とネットワーク識別で定義される次のサブテーブルのバージョン番号になる。
     * @return 
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
     * @return 
     */
    public final synchronized int getCurrent_next_indicator() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[2]);
        temp = temp & 0x1;
        return temp;
    }

    /**
     * section_number(セクション番号):この8ビットのフィールドはセクションの番号を表す。
     * サブテーブル中の最初のセクションのセクション番号は、0x00である。
     * セクション番号は、同一のテーブル識別、トランスポートストリーム識別、オリジナルネットワーク識別を持つセクションの追加ごとに1加算される。
     * @return 
     */
//    int section_number;
    public final synchronized int getSection_number() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[3]);
        return temp;
    }

    /**
     * last_section_number(最終セクション番号):この8ビットのフィールドは、 そのセクションが属するサブテーブルの最後のセクション
     * (すなわち、最大のセクション番号を持つセクション)の番号を規定する。
     * @return 
     */
//    int last_section_number;
    public final synchronized int getLast_section_number() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[4]);
        return temp;
    }

    /**
     * original_network_id(オリジナルネットワーク識別):この16ビットのフィールドは、
     * 元の分配システムのネットワーク識別を規定するラベルの役割をする。
     * @return 
     */
    public final synchronized int getOriginal_network_id() {
        byte[] t = new byte[2];
        System.arraycopy(this.getData(), 5, t, 0, t.length);
        int temp = ByteConverter.bytesToInt(t);
        return temp;
    }

    /**
     * 符号化ビットストリームを定義する項の中で使用する場合、 その値が将来、ARIB
     * STD-B10が定義する拡張子として使用されるかもしれないことを表す。 8bit
     * @return 
     */
    public final synchronized int getReserved_future_use2() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[7]);
        return temp;
    }

    /**
     * 繰り返し項目のバイト列
     * @return 
     */
    public final synchronized byte[] getRepeatingPart() {
        byte[] t = new byte[this.getData().length - 8];
        System.arraycopy(this.getData(), 8, t, 0, t.length);
        return t;
    }

    /**
     * 分割済みの繰り返し項目。
     *
     * @return
     */
    public final synchronized List<ServiceDescriptionTableRepeatingPart> getSDTRepeatingPartList() {
        List<byte[]> t = ByteArraySplitter.splitByLengthField(this.getRepeatingPart(), 4, 2,(x) -> x & 0x0FFF);
        List<ServiceDescriptionTableRepeatingPart> dest = new ArrayList<>();
        for (byte[] temp : t) {
            dest.add(new ServiceDescriptionTableRepeatingPart(temp));
        }
        return Collections.unmodifiableList(dest);
    }

    private static final MessageFormat TABLE_DESC = new MessageFormat( "{0}\n"
            + "トランスポートストリーム識別 = {1}\n"
            + "Reserved2 = {2}\n"
            + "バージョン番号 = {3}\n"
            + "カレントネクスト指示 = {4}\n"
            + "セクション番号 = {5}\n"
            + "最終セクション番号 = {6}\n"
            + "オリジナルネットワーク識別 = {7}\n"
            + "Reserved_future_use2 = {8}\n"
            + "繰り返し項目 = {9}\n"
            + "繰り返し項目の解析結果={10}\n"
    );

    @Override
    /**
     * このクラスで取得できるフィールドの内容を表示する。
     */
    public String toString() {
        List<ServiceDescriptionTableRepeatingPart> l = this.getSDTRepeatingPartList();
        StringBuilder s = new StringBuilder();
        for (ServiceDescriptionTableRepeatingPart sdtrp : l) {
            s.append(sdtrp);
        }

        Object[] parameters = {super.toString(),
            Integer.toHexString(this.getTransport_stream_id()),
            Integer.toHexString(this.getReserved2()),
            this.getVersion_number(),
            this.getCurrent_next_indicator(),
            Integer.toHexString(this.getSection_number()),
            Integer.toHexString(this.getLast_section_number()),
            Integer.toHexString(this.getOriginal_network_id()),
            Integer.toHexString(this.getReserved_future_use2()),
            Hex.encodeHexString(this.getRepeatingPart()),
            s.toString()};
        return TABLE_DESC.format(parameters);
    }

}
