/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Range;
import java.util.function.Function;
import libepg.epg.section.descriptor.contentdescriptor.ContentDescriptor;
import libepg.epg.section.descriptor.seriesdescriptor.SeriesDescriptor;
import libepg.epg.section.descriptor.servicedescriptor.ServiceDescriptor;
import libepg.epg.section.descriptor.shorteventdescriptor.ShortEventDescriptor;
import libepg.epg.section.descriptor.extendedeventdescriptor.ExtendedEventDescriptor;
import util.ReverseLookUpMapFactory;

/**
 * 記述子タグリスト：記述子タグは8 ビットのフィールドで、各記述子を識別する。<br>
 * MPEG-2規格に従った意味でのこれらの値は、ISO/IEC 13818-1(22)に記載されている。 <br>
 * 記述子タグの値は2-STD-B10 表6－1 に定義される。<br>
 *
 * @author normal
 */
public enum DESCRIPTOR_TAG {
    /**
     * サービス記述子（Service descriptor） 編成チャンネル名とその事業者名をサービス形式種別とともに文字符号で表す。
     *//**
     * サービス記述子（Service descriptor） 編成チャンネル名とその事業者名をサービス形式種別とともに文字符号で表す。
     */
    SERVICE_DESCRIPTOR("サービス記述子",ServiceDescriptor.class, 0x48),
    /**
     * 短形式イベント記述子（Short event descriptor）
     * 短形式イベント記述子はイベント名およびそのイベントの短い記述をテキスト形式で表す。
     */
    SHORT_EVENT_DESCRIPTOR("短形式イベント記述子",ShortEventDescriptor.class, 0x4d),
    /**
     * 拡張形式イベント記述子(Extended event descriptor)
     * 拡張形式イベント記述子は、短形式イベント記述子に付け加えて使用され、イベントの詳細記述を提供する。 1 イベントに関して256
     * バイト長以上の情報の伝送を可能とする為に、１つ以上の拡張形式イベント記述子を使用することができる。
     * 文書情報は項目名フィールドと項目記述フィールドの2 つの欄から構成される。
     * この構造の典型的な適用法は配役のリストの提供である。たとえば、項目名フィールドには「プロデューサー」、項目記述フィールドにはプロデューサーの名前が記載される。
     */
    EXTENDED_EVENT_DESCRIPTOR("拡張形式イベント記述子",ExtendedEventDescriptor.class, 0X4E),
    /**
     * コンテント記述子(Content descriptor) イベントのジャンルを示す。
     */
    CONTENT_DESCRIPTOR("コンテント記述子",ContentDescriptor.class, 0x54),
    /**
     * シリーズ記述子 シリーズ記述子は、シリーズ番組を識別するために用いる。
     */
    SERIES_DESCRIPTOR("シリーズ記述子",SeriesDescriptor.class, 0xd5),
    /**
     * デジタルコピー制御記述子(未実装。多分使わない)
     */
    DIGITAL_COPY_CONTROL_DESCRIPTOR("デジタルコピー制御記述子",Descriptor.class, 0xc1),
    /**
     * ロゴ伝送記述子（Logo transmission descriptor） ロゴ伝送記述子は、簡易ロゴ用文字列、
     * CDT形式のロゴへのポインティングなどを記述するために使用する。(未実装。多分使わない)
     */
    LOGO_TRANSMISSION_DESCRIPTOR("ロゴ伝送記述子",Descriptor.class, 0xcf),
    /**
     * その他の記述子。
     */
    OTHER_DESCRIPTOR_TAGS("その他の記述子",Descriptor.class, 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f, 0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x49, 0x4a, 0x4b, 0x4c, 0x4f, 0x50, 0x51, 0x52, 0x53, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x5b, 0x5c, 0x5d, 0x5e, 0x5f, 0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f, 0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x7b, 0x7c, 0x7d, 0x7e, 0x7f, 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c, 0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff);

    /**
     * 逆引きリスト
     */
    private static final Map<Integer, DESCRIPTOR_TAG> rev;

    private static final Function<DESCRIPTOR_TAG, Set<Integer>> func1 = (DESCRIPTOR_TAG t) -> t.getTags();
    //逆引きリスト生成
    private static final ReverseLookUpMapFactory<Integer, Set<Integer>, DESCRIPTOR_TAG> revmapf = new ReverseLookUpMapFactory<>(func1);

    static {
        for (DESCRIPTOR_TAG tag : DESCRIPTOR_TAG.values()) {
            revmapf.put(tag);
        }
        rev = revmapf.getDict();
    }

    ;
           
    /**
     * タグの値から定義済みの定数を逆引きする。
     *
     * @param tag タグの値
     * @return タグの値を含む定数。なければnull。
     */
    public static synchronized DESCRIPTOR_TAG reverseLookUp(int tag) {
        return rev.get(tag);
    }

    private String tagName;
    private final Class<? extends Descriptor> dataType;
    private final Set<Integer> tags;

    private DESCRIPTOR_TAG(String tagName, Class<? extends Descriptor> dataType, Integer tag, Integer... tags) {

        this.tagName = tagName;
        if ((this.tagName == null) || ("".equals(this.tagName))) {
            throw new IllegalArgumentException("タグ名が指定されていないか空文字です。");
        }

        List<Integer> t = new ArrayList<>();
        if (tag != null) {
            t.add(tag);
        } else {
            throw new NullPointerException("タグが指定されていません。");
        }
        if (tags != null) {
            t.addAll(Arrays.asList(tags));
        }
        Range<Integer> r = Range.between(0x0, 0xFF);
        for (Integer i : t) {
            if (!r.contains(i)) {
                MessageFormat msg = new MessageFormat("タグが範囲外の値です。タグ={0}");
                Object[] parameters = {Integer.toHexString(i)};
                throw new IllegalArgumentException(msg.format(parameters));
            }
        }
        Set<Integer> temp = Collections.synchronizedSet(new HashSet<Integer>());
        temp.addAll(t);
        this.tags = Collections.unmodifiableSet(temp);
        this.dataType = dataType;
    }

    public String getTagName() {
        return tagName;
    }

    /**
     * この定数に引数で渡されたタグが含まれているか?
     *
     * @param tag タグ
     * @return 含まれていればtrue
     */
    public synchronized boolean contains(int tag) {
        return this.tags.contains(tag);
    }

    private Set<Integer> getTags() {
        return tags;
    }

    public synchronized Class<? extends Descriptor> getDataType() {
        return dataType;
    }

    @Override
    public synchronized String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : this.tags) {
            s.append("[");
            s.append(Integer.toHexString(i));
            s.append("]");
        }
        String set = s.toString();
        MessageFormat msg = new MessageFormat("{0}(tagName={1},TAGs={2},dataType={3})");
        Object[] parameters = {super.toString(),this.getTagName(), set, dataType};
        return msg.format(parameters);
    }

}
