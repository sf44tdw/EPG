/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor.contentdescriptor;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author normal
 */
/**
 * 第一段階分類符号 (ジャンル大分類)
 */
public enum NIBBLE_LEVEL_1 {
    /**
     * ニュース／報道
     */
    NEWS(0x0, "ニュース／報道", "news"),
    /**
     * スポーツ
     */
    SPORTS(0x1, "スポーツ", "sports"),
    /**
     * 情報／ワイドショー
     */
    INFORMATION(0x2, "情報／ワイドショー", "information"),
    /**
     * ドラマ
     */
    DRAMA(0x3, "ドラマ", "drama"),
    /**
     * 音楽
     */
    MUSIC(0x4, "音楽", "music"),
    /**
     * バラエティ
     */
    VARIETY(0x5, "バラエティ", "variety"),
    /**
     * 映画
     */
    CINEMA(0x6, "映画", "cinema"),
    /**
     * アニメ／特撮
     */
    ANIME(0x7, "アニメ／特撮", "anime"),
    /**
     * ドキュメンタリー／教養
     */
    DOCUMENTARY(0x8, "ドキュメンタリー／教養", "documentary"),
    /**
     * 劇場／公演
     */
    STAGE(0x9, "劇場／公演", "stage"),
    /**
     * 趣味／教育
     */
    HOBBY(0xa, "趣味／教育", "hobby"),
    /**
     * 福祉
     */
    WELFARE(0xb, "福祉", "welfare"),
    /**
     * 予約
     */
    RESERVE_1(0xc, "予備", "etc"),
    /**
     * 予約
     */
    RESERVE_2(0xd, "予備", "etc"),
    /**
     * 拡張
     */
    USER_NIBBLE(0xe, "拡張", "user_nibble"),
    /**
     * その他
     */
    OTHERS(0xf, "その他", "Others");
    //逆引きリスト
    private static final Map<Integer, NIBBLE_LEVEL_1> rev = new ConcurrentHashMap<>();

    static {
        {
            for (NIBBLE_LEVEL_1 nibble1 : NIBBLE_LEVEL_1.values()) {
                if (!rev.containsKey(nibble1.getCode())) {
                    rev.put(nibble1.getCode(), nibble1);
                } else {
                    MessageFormat msg = new MessageFormat("ジャンルコードが重複して登録されています。重複したジャンルコード={0} 重複したときの定数名={1}");
                    Object[] parameters = {Integer.toHexString(nibble1.getCode()), nibble1};
                    throw new IllegalArgumentException(msg.format(parameters));
                }

            }
        }
    }

    ;
           
    /**
     * ジャンルコードから定義済みの定数を逆引きする。
     *
     * @param code ジャンル大分類コード
     * @return ジャンルコードを含む定数。なければnull。
     */
        public static synchronized NIBBLE_LEVEL_1 reverseLookUp(int code) {
        return rev.get(code);
    }

    private final int code;
    private final String nibble_en;
    private final String nibble_jp;

    private NIBBLE_LEVEL_1(int code, String nibble_jp, String nibble_en) {
        this.code = code;
        if ((this.code < 0) || (this.code > 0xf)) {
            MessageFormat msg = new MessageFormat("ジャンルコードは0x0以上0xF以下です。設定値={0}");
            Object[] parameters = {Integer.toHexString(this.code)};
            throw new IllegalArgumentException(msg.format(parameters));
        }
        this.nibble_en = nibble_en;
        if ((this.nibble_en == null) || (this.nibble_en.equals(""))) {
            MessageFormat msg = new MessageFormat("ジャンル(英語)がnullか、空文字列です。");
            Object[] parameters = {Integer.toHexString(this.code)};
            throw new IllegalArgumentException(msg.format(parameters));
        }
        this.nibble_jp = nibble_jp;
        if ((this.nibble_jp == null) || (this.nibble_jp.equals(""))) {
            MessageFormat msg = new MessageFormat("ジャンル(日本語)がnullか、空文字列です。");
            Object[] parameters = {Integer.toHexString(this.code)};
            throw new IllegalArgumentException(msg.format(parameters));
        }
    }

    public int getCode() {
        return code;
    }

    public String getNibble_en() {
        return nibble_en;
    }

    public String getNibble_jp() {
        return nibble_jp;
    }

    @Override
    public String toString() {
        return "NIBBLE_LEVEL_1{" + "code=" + code + ", nibble_jp=" + nibble_jp + ", nibble_en=" + nibble_en + '}';
    }

}
