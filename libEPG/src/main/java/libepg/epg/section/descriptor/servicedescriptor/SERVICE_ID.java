/*
 * Copyright (C) 2016 normal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package libepg.epg.section.descriptor.servicedescriptor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.Range;

/**
 * サービスID
 * @author normal
 */
public enum SERVICE_ID {
    /**
     * 0x00:未定義<br>
     * 0x03_-_0x7F:未定義<br>
     * 0xC2_-_0xFF:未定義<br>
     */
    UNDEFINED("未定義",
            0x00, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
            0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f,
            0x20, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2a, 0x2b, 0x2c, 0x2d, 0x2e, 0x2f,
            0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x3a, 0x3b, 0x3c, 0x3d, 0x3e, 0x3f,
            0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d, 0x4e, 0x4f,
            0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x5b, 0x5c, 0x5d, 0x5e, 0x5f,
            0x60, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7a, 0x7b, 0x7c, 0x7d, 0x7e, 0x7f,
            0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf,
            0xd0, 0xd1, 0xd2, 0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf,
            0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef,
            0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff),
    /**
     * 0x01:デジタルＴＶサービス
     */
    DIGITAL_TV_SERVICE("デジタルＴＶサービス", 0x01),
    /**
     * 0x02:デジタル音声サービス
     */
    DIGITAL_VOICE_SERVICE("デジタル音声サービス", 0x02),
    /**
     * 0x80_-_0xA0:事業者定義
     */
    BUSINESS_DEFINITION("事業者定義",
            0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c, 0x8d, 0x8e, 0x8f,
            0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e, 0x9f,
            0xa0),
    /**
     * 0xA1:臨時映像サービス
     */
    EXTRAORDINARY_VIDEO_SERVICE("臨時映像サービス", 0xA1),
    /**
     * 0xA2:臨時音声サービス
     */
    EXTRAORDINARY_VOICE_SERVICE("臨時音声サービス", 0xA2),
    /**
     * 0xA3:臨時データサービス
     */
    EXTRAORDINARY_DATA_SERVICE("臨時データサービス", 0xA3),
    /**
     * 0xA4:エンジニアリングサービス
     */
    ENGINEERING_SERVICES("エンジニアリングサービス", 0xA4),
    /**
     * 0xA5:プロモーション映像サービス
     */
    PROMOTIONAL_VIDEO_SERVICES("プロモーション映像サービス", 0xA5),
    /**
     * 0xA6:プロモーション音声サービス
     */
    PROMOTION_VOICE_SERVICE("プロモーション音声サービス", 0xA6),
    /**
     * 0xA7:プロモーションデータサービス
     */
    PROMOTION_DATA_SERVICE("プロモーションデータサービス", 0xA7),
    /**
     * 0xA8:事前蓄積用データサービス
     */
    PRE_ACCUMULATION_DATA_SERVICE("事前蓄積用データサービス", 0xA8),
    /**
     * 0xA9:蓄積専用データサービス
     */
    ACCUMULATION_ONLY_DATA_SERVICE("蓄積専用データサービス", 0xA9),
    /**
     * 0xAA:ブックマーク一覧データサービス
     */
    BOOKMARK_LIST_DATA_SERVICE("ブックマーク一覧データサービス", 0xAA),
    /**
     * 0xAB:サーバー型サイマルサービス
     */
    SERVER_TYPE_SIMULCAST_SERVICE("サーバー型サイマルサービス", 0xAB),
    /**
     * 0xAC:独立ファイルサービス
     */
    INDEPENDENT_FILE_SERVICE("独立ファイルサービス", 0xAC),
    /**
     * 0xAD_-_0xBF:未定義（標準化機関定義領域)
     */
    ORGANIZATION_FOR_STANDARDIZATION("標準化機関定義領域", 0xad, 0xae, 0xaf, 0xb0, 0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf),
    /**
     * 0xC0 :データサービス
     */
    DATA_SERVICES("データサービス", 0xC0),
    /**
     * 0xC1:TLVを用いた蓄積型サービス
     */
    STORAGE_TYPE_SERVICES_USING_TLV("TLVを用いた蓄積型サービス", 0xC1);

    //逆引きリスト
    private static final Map<Integer, SERVICE_ID> rev = new ConcurrentHashMap<>();

    static {
        {
            for (SERVICE_ID sid : SERVICE_ID.values()) {
                for (int i : sid.serviceIds) {
                    if (!rev.containsKey(i)) {
                        rev.put(i, sid);
                    } else {
                        MessageFormat msg = new MessageFormat("サービスIDが重複して登録されています。重複したサービスID={0} 重複したときの定数名={1}");
                        Object[] parameters = {Integer.toHexString(i), sid};
                        throw new IllegalArgumentException(msg.format(parameters));
                    }
                }
            }
        }
    }

    ;
           
    /**
     * サービスIDから定義済みの定数を逆引きする。
     *
     * @param serviceId
     * @return サービスIDを含む定数。なければnull。
     */
    public static synchronized SERVICE_ID reverseLookUp(int serviceId) {
        return rev.get(serviceId);
    }

    private final String serviceType;
    private final Set<Integer> serviceIds;

    private SERVICE_ID(String serviceType, Integer serviceId, Integer... serviceIds) {
        this.serviceType = serviceType;
        if ((this.serviceType == null) || (this.serviceType.equals(""))) {
            throw new IllegalArgumentException("サービス種が指定されていません。");
        }

        List<Integer> t = new ArrayList<>();
        if (serviceId != null) {
            t.add(serviceId);
        } else {
            throw new NullPointerException("サービスIDが指定されていません。");
        }
        if (serviceIds != null) {
            t.addAll(Arrays.asList(serviceIds));
        }
        Range<Integer> r = Range.between(0x0, 0xFF);
        for (Integer i : t) {
            if (!r.contains(i)) {
                MessageFormat msg = new MessageFormat("サービスIDが範囲外の値です。サービスID={0}");
                Object[] parameters = {Integer.toHexString(i)};
                throw new IllegalArgumentException(msg.format(parameters));
            }
        }

        Set<Integer> temp = Collections.synchronizedSet(new HashSet<Integer>());
        temp.addAll(t);
        this.serviceIds = Collections.unmodifiableSet(temp);
    }

    public String getServiceType() {
        return serviceType;
    }

    /**
     * この定数に引数で渡されたサービスIDが含まれているか?
     *
     * @param serviceId サービスID
     * @return 含まれていればtrue
     */
    public synchronized boolean contains(int serviceId) {
        return this.serviceIds.contains(serviceId);
    }

    @Override
    public synchronized String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : this.serviceIds) {
            s.append("[");
            s.append(Integer.toHexString(i));
            s.append("]");
        }
        String set = s.toString();
        MessageFormat msg = new MessageFormat("{0}({1},serviceIds={2})");
        Object[] parameters = {super.toString(), this.getServiceType(), set};
        return msg.format(parameters);
    }
}
