package libepg.epg.section.descriptor.extendedeventdescriptor;


import libepg.util.bytearray.ByteConverter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import libepg.epg.section.descriptor.Descriptor;
import libepg.util.bytearray.ByteArraySplitter;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 拡張形式イベント記述子(Extended event descriptor)
 * 拡張形式イベント記述子は、短形式イベント記述子に付け加えて使用され、イベントの詳細記述を提供する。 1 イベントに関して256
 * バイト長以上の情報の伝送を可能とする為に、１つ以上の拡張形式イベント記述子を使用することができる。 文書情報は項目名フィールドと項目記述フィールドの2
 * つの欄から構成される。
 * この構造の典型的な適用法は配役のリストの提供である。たとえば、項目名フィールドには「プロデューサー」、項目記述フィールドにはプロデューサーの名前が記載される。
 */
public class ExtendedEventDescriptor extends Descriptor {

    public ExtendedEventDescriptor(Descriptor discriptor) {
        super(discriptor);
    }

    /**
     * descriptor_number(記述子番号)：記述子の番号を表す4 ビットのフィールドである。
     * ひとつの記述子には入りきらない情報を関係づける為に使われる。関係づけられている拡張形式イベント記述子セットの一番目の記述子番号は、「0x0」とする。
     * このセクション内で拡張形式イベント記述子が加わるごとに記述子番号は1 加算される。
     *
     * @return
     */
    public final synchronized int getDescriptor_number() {
        int temp = ByteConverter.byteToInt(this.getDescriptor_Body()[0]);
        temp = temp & 0xf0;
        temp = temp >>> 4;
        return temp;
    }

    /**
     * last_descriptor_number(最終記述子番号)：この4 ビットのフィールドは、この記述子がその一部分となっている、
     * 関係づけられた記述子セットの最終拡張形式イベント記述子(最大の記述子番号を持つ記述子)の番号を示す。
     *
     * @return
     */
    public final synchronized int getLast_descriptor_number() {
        int temp = ByteConverter.byteToInt(this.getDescriptor_Body()[0]);
        temp = temp & 0x0f;
        return temp;
    }

    /**
     * ISO_639_language_code(言語コード)：この24
     * ビットのフィールドは、コンポーネント(音声、あるいはデータ)の言語、およびこの記述子に含まれる文字記述の言語を識別する。 言語コードは、ISO
     * 639-2(22)に規定されるアルファベット3 文字コードで表す。各文字はISO 8859-1(24)に従って8 ビットで符号化され、
     * その順で24 ビットフィールドに挿入される。 例： 日本語はアルファベット3 文字コードで「jpn」であり、次のように符号化される。 「0110
     * 1010 0111 0000 0110 1110」
     *
     * @return
     */
    public final synchronized String getISO_639_language_code() {
        byte[] t = new byte[3];
        System.arraycopy(this.getDescriptor_Body(), 1, t, 0, t.length);
        return new String(t);
    }

    /**
     * length_of_items(項目長)：これは8 ビットのフィールドで、後続の項目のバイト長を示す。
     *
     * @return
     */
    public final synchronized int getLength_of_items() {
        int temp;
        temp = ByteConverter.byteToInt(this.getDescriptor_Body()[4]);
        return temp;
    }

    /**
     * 項目を取得する。
     *
     * @return
     */
    public synchronized byte[] getItems() {
        byte[] t = new byte[this.getLength_of_items()];
        if (t.length > 0) {
            System.arraycopy(this.getDescriptor_Body(), 5, t, 0, t.length);
        }
        return t;
    }

    /**
     * 項目オブジェクトを取得する。
     *
     * @return
     */
    public synchronized List<ExtendedEventDescriptorItem> getItemsList() {
        List<ExtendedEventDescriptorItem> ret = new ArrayList<>();

        List<byte[]> temp = ByteArraySplitter.splitByLengthField(this.getItems(), 1, 1);

        Iterator<byte[]> iterator = temp.iterator();
        while (iterator.hasNext()) {
            byte[] item_description;
            byte[] item;
            try {
                item_description = iterator.next();
            } catch (NoSuchElementException e) {
                LOG.error("次の項目名がありません。", e);
                item_description = new byte[0];
            }
            try {
                item = iterator.next();
            } catch (NoSuchElementException e) {
                LOG.error("次の項目記述がありません。", e);
                item = new byte[0];
            }
            byte[] itemArray = ArrayUtils.addAll(item_description, item);
            ret.add(new ExtendedEventDescriptorItem(itemArray));
        }
        return ret;
    }

    /**
     * text_length（拡張記述長）：これは8 ビットのフィールドで、項目無しの拡張記述のバイト長を示す。
     *
     * @return
     */
    public final synchronized int getText_length() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[5 + this.getLength_of_items() + 2]);
        return temp;
    }

    /**
     * text_char(拡張記述(文字符号)):これは8 ビットのフィールドで、一連の拡張記述のフィールドは、項目無しの文字記述を規定する。
     * 文字情報の符号化に関しては、付録A を参照。
     */
    public final synchronized byte[] getText_char() {
        byte[] t = new byte[this.getText_length()];
        if (t.length > 0) {
            System.arraycopy(this.getDescriptor_Body(), 5 + this.getLength_of_items() + 1, t, 0, t.length);
        }
        return t;
    }

    private static final MessageFormat DESC = new MessageFormat("{0}"
            + "記述子番号 = {1}\n"
            + "最終記述子番号 = {2}\n"
            + "言語コード = {3}\n"
            + "項目長 = {4}\n"
            + "項目 = {5}\n"
            + "項目内容 = {6}"
            + "拡張記述長 = {7}\n"
            + "拡張記述 = {8}\n"
    );

    @Override
    public String toString() {
        List<ExtendedEventDescriptorItem> l = this.getItemsList();
        StringBuilder s = new StringBuilder();
        for (ExtendedEventDescriptorItem val : l) {
            s.append(val);
        }

        Object[] parameters = {super.toString(),
            this.getDescriptor_number(),
            this.getLast_descriptor_number(),
            this.getISO_639_language_code(),
            this.getLength_of_items(),
            s.toString(),
            Hex.encodeHexString(this.getItems()),
            this.getText_length(),
            Hex.encodeHexString(this.getText_char())};
        return DESC.format(parameters);
    }

}
