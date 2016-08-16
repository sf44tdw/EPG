/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section;

import epgtools.reverselookupmapfactory.ReverseLookUpMapFactory;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import libepg.epg.section.body.eventinformationtable.EventInformationTableBody;
import libepg.epg.section.body.servicedescriptiontable.ServiceDescriptionTableBody;
import org.apache.commons.lang3.Range;



/**
 * セクションのテーブル識別値(テーブルID)とそのテーブルの最大長の組み合わせ。 テーブルIDは1つ以上必要。
 * 2箇所以上で同じテーブルIDを指定することは禁止。
 */
public enum TABLE_ID {
    /**
     * サービス記述テーブルSDT（Service Description Table）<br>
     * 編成チャンネルの名称、放送事業者の名称など、編成チャンネルに関する情報を伝送する。<br>
     * 最大セクション長は1021バイト。<br>
     * 自トランスポートストリーム（つまりそのSDTを含むトランスポートストリーム）の内容を記述するSDTのセクションは全てテーブル識別値は0x42とし、<br>
     * また、自トランスポートストリーム以外のトランスポートストリームのSDTセクションのテーブル識別値は0x46とする。<br>
     */
    /**
     * サービス記述テーブルSDT（Service Description Table）<br>
     * 編成チャンネルの名称、放送事業者の名称など、編成チャンネルに関する情報を伝送する。<br>
     * 最大セクション長は1021バイト。<br>
     * 自トランスポートストリーム（つまりそのSDTを含むトランスポートストリーム）の内容を記述するSDTのセクションは全てテーブル識別値は0x42とし、<br>
     * また、自トランスポートストリーム以外のトランスポートストリームのSDTセクションのテーブル識別値は0x46とする。<br>
     */
    SDT("サービス記述テーブル", MAX_SECTION_LENGTH.BYTE_1021, ServiceDescriptionTableBody.class, 0x42, 0x46),
    /**
     * EIT(自局8日以内)
     */
    EIT_THIS_STREAM_8_DAYS("EIT(自局8日以内)", MAX_SECTION_LENGTH.BYTE_4093, EventInformationTableBody.class, 0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x5b, 0x5c, 0x5d, 0x5e, 0x5f),
    /**
     * EIT（自ストリームの現在と次の番組） 今現在放送中の番組とその次の番組のこと
     */
    EIT_THIS_STREAM_NOW_AND_NEXT("EIT（自ストリームの現在と次の番組）", MAX_SECTION_LENGTH.BYTE_4093, EventInformationTableBody.class, 0x4E),
    /**
     * EIT(他局8日以内)
     */
    EIT_OTHER_STREAM_8_DAYS("EIT(他局8日以内)", MAX_SECTION_LENGTH.BYTE_4093, EventInformationTableBody.class, 0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f),
    /**
     * EIT（他ストリームの現在と次の番組）今現在放送中の番組とその次の番組のこと
     */
    EIT_OTHER_STREAM_NOW_AND_NEXT("EIT（他ストリームの現在と次の番組）今現在放送中の番組とその次の番組のこと", MAX_SECTION_LENGTH.BYTE_4093, EventInformationTableBody.class, 0x4F),
    /**
     * その他(基本的に使わない)
     */
    OTHER_TABLE_IDS("その他(基本的に使わない)", MAX_SECTION_LENGTH.BYTE_4093, SectionBody.class, 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f, 0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f, 0x40, 0x41, 0x43, 0x44, 0x45, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x7b, 0x7c, 0x7d, 0x7e, 0x7f, 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c, 0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff);

    /**
     * セクション長(セクション長フィールド直後からCRC3バイトを含めて最大で1021バイトもしくは4093バイト。最大長がどちらの長さになるかはテーブルの種類しだい。)
     * 全セクションの長さが1024バイトもしくは4096バイトを超えないようにするため、セクション長は1021バイトもしくは4093バイトを超えてはならない。
     */
    public static enum MAX_SECTION_LENGTH {
        BYTE_1021(1021), BYTE_4093(4093);
        private final int maxSectionLength;

        private MAX_SECTION_LENGTH(int length) {
            this.maxSectionLength = length;
        }

        /**
         * セクション長フィールド直後からCRC3バイトまでの最大長。1021バイトもしくは4093バイトを超えてはならない。
         *
         * @return セクション長の最大値
         */
        public int getMaxSectionBodyLength() {
            return maxSectionLength;
        }

        /**
         * セクションを表すバイト列の最大長。1024バイトもしくは4096バイトを超えてはならない。
         *
         * @return 全セクション長の最大値
         */
        public int getMaxSectionLength() {
            return maxSectionLength + 3;
        }

        @Override
        public synchronized String toString() {
            MessageFormat msg = new MessageFormat("{0}(maxSectionLength={1})");
            Object[] parameters = {super.toString(), maxSectionLength};
            return msg.format(parameters);
        }

    }

    //逆引きリスト
    private static final Map<Integer, TABLE_ID> rev;

    private static final Function<TABLE_ID, Set<Integer>> func1 = (TABLE_ID t) -> t.getTableIDs();
    //逆引きリスト生成
    private static final ReverseLookUpMapFactory<Integer, Set<Integer>, TABLE_ID> revmapf = new ReverseLookUpMapFactory<>(func1);

    static {
        for (TABLE_ID tid : TABLE_ID.values()) {
            revmapf.put(tid);
        }
        rev = revmapf.getDict();
    }

    ;
           
    /**
     * テーブルIDから定義済みの定数を逆引きする。
     *
     * @param tableID テーブルID
     * @return テーブルIDを含む定数。なければnull。
     */
    public static synchronized TABLE_ID reverseLookUp(int tableID) {
        return rev.get(tableID);
    }

    private final String tableName;
    private final MAX_SECTION_LENGTH maxSectionLength;
    private final Class<? extends SectionBody> dataType;
    private final Set<Integer> tableIDs;

    private TABLE_ID(String tableName, MAX_SECTION_LENGTH maxSectionLength, Class<? extends SectionBody> dataType, Integer tableID, Integer... tableIDs) {

        this.tableName = tableName;
        if ((this.tableName == null) || ("".equals(this.tableName))) {
            throw new IllegalArgumentException("テーブル名が指定されていないか空文字です。");
        }

        List<Integer> t = new ArrayList<>();
        if (tableID != null) {
            t.add(tableID);
        } else {
            throw new NullPointerException("テーブルIDが指定されていません。");
        }

        if (tableIDs != null) {
            t.addAll(Arrays.asList(tableIDs));
        }
        Range<Integer> r = Range.between(0x0, 0xFF);
        for (Integer i : t) {
            if (!r.contains(i)) {
                MessageFormat msg = new MessageFormat("テーブルIDが範囲外の値です。テーブルID={0}");
                Object[] parameters = {Integer.toHexString(i)};
                throw new IllegalArgumentException(msg.format(parameters));
            }
        }

        Set<Integer> temp = Collections.synchronizedSet(new HashSet<Integer>());
        temp.addAll(t);
        this.tableIDs = Collections.unmodifiableSet(temp);
        this.dataType = dataType;
        this.maxSectionLength = maxSectionLength;
    }

    public synchronized String getTableName() {
        return tableName;
    }

    /**
     * この定数に引数で渡されたテーブルIDが含まれているか?
     *
     * @param tableID テーブルID
     * @return 含まれていればtrue
     */
    public synchronized boolean contains(int tableID) {
        return this.tableIDs.contains(tableID);
    }

    public synchronized MAX_SECTION_LENGTH getMaxSectionLength() {
        return maxSectionLength;
    }

    public synchronized Class<? extends SectionBody> getDataType() {
        return dataType;
    }

    private Set<Integer> getTableIDs() {
        return tableIDs;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : this.tableIDs) {
            s.append("[");
            s.append(Integer.toHexString(i));
            s.append("]");
        }
        String set = s.toString();
        MessageFormat msg = new MessageFormat("{0}(tableName={1}, tableIDs={2}, maxSectionLength={3},dataType={4})");
        Object[] parameters = {super.toString(), this.getTableName(), set, maxSectionLength, dataType};
        return msg.format(parameters);
    }

}
