/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor.shorteventdescriptor;

import libepg.util.bytearray.ByteConverter;
import java.text.MessageFormat;
import libepg.epg.section.descriptor.Descriptor;
import libepg.epg.util.Aribstr;
import org.apache.commons.codec.binary.Hex;



/**
 * 短形式イベント記述子（Short event descriptor） 短形式イベント記述子はイベント名およびそのイベントの短い記述をテキスト形式で表す。
 *
 * @author normal
 */
public final class ShortEventDescriptor extends Descriptor {

    public ShortEventDescriptor(Descriptor discriptor) {
        super(discriptor);
    }

    /**
     * ISO_639_language_code(言語コード):この24 ビットのフィールドは、後続の文字情報フィールドの言語をISO
     * 639-2(22)に規定されるアルファベット3 文字コードで表す。 各文字はISO 8859-1(24)に従って8
     * ビットで符号化され、その順で24 ビットフィールドに挿入される。 例: 日本語はアルファベット3
     * 文字コードで「jpn」であり、次のように符号化される。 「0110 1010 0111 0000 0110 1110」
     *
     * @return 言語コード
     */
    public synchronized String getISO_639_language_code() {
        byte[] t = new byte[3];
        System.arraycopy(this.getData(), 2, t, 0, t.length);
        return new String(t);
    }

    /**
     * event_name_length(番組名長):この8 ビットのフィールドは、後続の番組名のバイト長を表す。
     *
     * @return 番組名長
     */
    public synchronized int getEvent_name_length() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[5]);
        return temp;
    }

    /**
     * event_name_char(番組名):これは8 ビットのフィールドである。一連の文字情報フィールドは、番組名を表す。
     * 文字情報の符号化に関しては、付録A を参照。
     *
     * @return 番組名のバイト列
     */
    public synchronized byte[] getEvent_name_char() {
        byte[] t = new byte[this.getEvent_name_length()];
        if (t.length > 0) {
            System.arraycopy(this.getData(), 6, t, 0, t.length);
        }
        return t;
    }

    /**
     * 番組名の文字列を取得する
     *
     * @return 変換した文字列
     */
    public synchronized String getEvent_name_String() {
        byte[] t = this.getEvent_name_char();
        return Aribstr.AribToString(t);
    }

    /**
     * text_length(番組記述長):この8 ビットのフィールドは、後続の番組記述のバイト長を表す。
     *
     * @return 番組記述長
     */
    public synchronized int getText_length() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[6 + this.getEvent_name_length()]);
        return temp;
    }

    /**
     * text_char(番組記述):これは8 ビットのフィールドである。一連の文字情報フィールド
     * は番組の説明を記述する。文字情報の符号化に関しては、付録A を参照。
     *
     * @return 番組記述のバイト列
     */
    public synchronized byte[] getText_char() {
        byte[] t = new byte[this.getText_length()];
        if (t.length > 0) {
            System.arraycopy(this.getData(), 6 + this.getEvent_name_length() + 1, t, 0, t.length);
        }
        return t;
    }

    /**
     * 番組記述の文字列を取得する
     *
     * @return 変換した文字列
     */
    public synchronized String getText_String() {
        byte[] t = this.getText_char();
        return Aribstr.AribToString(t);
    }

    private static final MessageFormat DESC = new MessageFormat(
            "{0}\n"
            + "言語コード = {1}\n"
            + "番組名長 = {2}\n"
            + "番組名 = {3}\n"
            + "番組名テキスト = {4}\n"
            + "番組記述長 = {5}\n"
            + "番組記述 = {6}\n"
            + "番組記述テキスト = {7}\n"
    );

    @Override
    public String toString() {
        Object[] parameters = {super.toString(),
            this.getISO_639_language_code(),
            this.getEvent_name_length(),
            Hex.encodeHexString(this.getEvent_name_char()),
            this.getEvent_name_String(),
            this.getText_length(),
            Hex.encodeHexString(this.getText_char()),
            this.getText_String()};
        return DESC.format(parameters);
    }

}
