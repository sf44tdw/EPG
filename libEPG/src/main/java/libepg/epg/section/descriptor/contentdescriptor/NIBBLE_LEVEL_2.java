/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor.contentdescriptor;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.ANIME;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.CINEMA;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.DOCUMENTARY;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.DRAMA;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.HOBBY;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.INFORMATION;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.MUSIC;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.NEWS;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.STAGE;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.USER_NIBBLE;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.VARIETY;
import static libepg.epg.section.descriptor.contentdescriptor.NIBBLE_LEVEL_1.WELFARE;
import org.apache.commons.collections4.keyvalue.MultiKey;


/**
 * 第二段階分類符号 (ジャンル中分類)
 */
public enum NIBBLE_LEVEL_2 {
    /**
     * 定時・総合
     */
    REGULAR_GENERAL(NEWS, 0x0, "定時・総合", "Regular/General"),
    /**
     * 天気
     */
    WEATHER(NEWS, 0x1, "天気", "Weather"),
    /**
     * 特集・ドキュメント
     */
    SPECIAL_TOPIC_DOCUMENTARY(NEWS, 0x2, "特集・ドキュメント", "Special topic/documentary"),
    /**
     * 政治・国会
     */
    POLITICS_DIET(NEWS, 0x3, "政治・国会", "Politics/diet"),
    /**
     * 経済・市況
     */
    ECONOMY_MARKET(NEWS, 0x4, "経済・市況", "Economy/market"),
    /**
     * 海外・国際
     */
    OVERSEAS(NEWS, 0x5, "海外・国際", "Overseas"),
    /**
     * 解説
     */
    COMMENTARY(NEWS, 0x6, "解説", "Commentary"),
    /**
     * 討論・会談
     */
    DISCUSSION_MEETING(NEWS, 0x7, "討論・会談", "Discussion/meeting"),
    /**
     * 報道特番
     */
    SPECIAL_NEWS_REPORT(NEWS, 0x8, "報道特番", "Special news report"),
    /**
     * ローカル・地域
     */
    LOCAL(NEWS, 0x9, "ローカル・地域", "Local"),
    /**
     * 交通
     */
    TRAFFIC(NEWS, 0xa, "交通", "Traffic"),
    /**
     * その他
     */
    OTHERS_NEWS(NEWS, 0xf, "その他", "Others"),
    /**
     * スポーツニュース
     */
    SPORTS_NEWS(NIBBLE_LEVEL_1.SPORTS, 0x0, "スポーツニュース", "Sports news"),
    /**
     * 野球
     */
    BASEBALL(NIBBLE_LEVEL_1.SPORTS, 0x1, "野球", "Baseball"),
    /**
     * サッカー
     */
    SOCCER(NIBBLE_LEVEL_1.SPORTS, 0x2, "サッカー", "soccer"),
    /**
     * ゴルフ
     */
    GOLF(NIBBLE_LEVEL_1.SPORTS, 0x3, "ゴルフ", "Golf"),
    /**
     * その他の球技
     */
    OTHER_BALL_GAMES(NIBBLE_LEVEL_1.SPORTS, 0x4, "その他の球技", "Other ball games"),
    /**
     * 相撲・格闘技
     */
    SUMO_FIGHTING_SPORTS(NIBBLE_LEVEL_1.SPORTS, 0x5, "相撲・格闘技", "Sumo/fighting sports"),
    /**
     * オリンピック・国際大会
     */
    OLYMPIC_INTERNATIONAL_GAMES(NIBBLE_LEVEL_1.SPORTS, 0x6, "オリンピック・国際大会", "Olympic/international games"),
    /**
     * マラソン・陸上・水泳
     */
    MARATHON_FIELD_AND_TRACK_SWIMMING(NIBBLE_LEVEL_1.SPORTS, 0x7, "マラソン・陸上・水泳", "Marathon/field and track/swimming"),
    /**
     * モータースポーツ
     */
    MOTOR_SPORTS(NIBBLE_LEVEL_1.SPORTS, 0x8, "モータースポーツ", "Motor sports"),
    /**
     * マリン・ウインタースポーツ
     */
    MARINE_WINTER_SPORTS(NIBBLE_LEVEL_1.SPORTS, 0x9, "マリン・ウインタースポーツ", "Marine/winter sports"),
    /**
     * 競馬・公営競技
     */
    HORSE_RACE_PUBLIC_SPORTS(NIBBLE_LEVEL_1.SPORTS, 0xa, "競馬・公営競技", "Horse race/public sports"),
    /**
     * その他
     */
    OTHERS_SPORTS(NIBBLE_LEVEL_1.SPORTS, 0xf, "その他", "Others"),
    /**
     * 芸能・ワイドショー
     */
    SHOW_BUSINESS_TABLOID_SHOW(INFORMATION, 0x0, "芸能・ワイドショー", "Show business/tabloid show"),
    /**
     * ファッション
     */
    FASHION(INFORMATION, 0x1, "ファッション", "Fashion"),
    /**
     * 暮らし・住まい
     */
    LIFE_RESIDENCE(INFORMATION, 0x2, "暮らし・住まい", "Life/residence"),
    /**
     * 健康・医療
     */
    HEALTH_MEDICAL_CARE(INFORMATION, 0x3, "健康・医療", "Health/medical care"),
    /**
     * ショッピング・通販
     */
    SHOPPING_CATALOG_SHOPPING(INFORMATION, 0x4, "ショッピング・通販", "Shopping/catalog shopping"),
    /**
     * グルメ・料理
     */
    GOURMET_COOKING_SHOW(INFORMATION, 0x5, "グルメ・料理", "Gourmet/cooking show"),
    /**
     * イベント
     */
    EVENTS(INFORMATION, 0x6, "イベント", "Events"),
    /**
     * 番組紹介・お知らせ
     */
    PROMOTION_INFORMATION(INFORMATION, 0x7, "番組紹介・お知らせ", "Promotion/information"),
    /**
     * その他
     */
    OTHERS_INFORMATION(INFORMATION, 0xf, "その他", "Others"),
    /**
     * 国内ドラマ
     */
    JAPANESE_DRAMA(DRAMA, 0x0, "国内ドラマ", "Japanese drama"),
    /**
     * 海外ドラマ
     */
    OVERSEAS_DRAMA(DRAMA, 0x1, "海外ドラマ", "Overseas drama"),
    /**
     * 時代劇
     */
    HISTORICAL_DRAMA(DRAMA, 0x2, "時代劇", "Historical drama"),
    /**
     * その他
     */
    OTHERS_DRAMA(DRAMA, 0xf, "その他", "Others"),
    /**
     * 国内ロック・ポップス
     */
    JAPANESE_ROCK_POPS(MUSIC, 0x0, "国内ロック・ポップス", "Japanese rock/pops"),
    /**
     * 海外ロック・ポップス
     */
    OVERSEAS_ROCK_POPS(MUSIC, 0x1, "海外ロック・ポップス", "Overseas rock/pops"),
    /**
     * クラシック・オペラ
     */
    CLASSICAL_OPERA(MUSIC, 0x2, "クラシック・オペラ", "Classical/opera"),
    /**
     * ジャズ・フュージョン
     */
    JAZZ_FUSION(MUSIC, 0x3, "ジャズ・フュージョン", "Jazz/fusion"),
    /**
     * 歌謡曲・演歌
     */
    JAPANESE_POPULAR_SONG_ENKA(MUSIC, 0x4, "歌謡曲・演歌", "Japanese popular song/ballad /***/(Enka)"),
    /**
     * ライブ・コンサート
     */
    LIVE_SHOW_CONCERT(MUSIC, 0x5, "ライブ・コンサート", "Live show/concert"),
    /**
     * ランキング・リクエスト
     */
    RANKING_REQUEST_SHOW(MUSIC, 0x6, "ランキング・リクエスト", "Ranking/Request show"),
    /**
     * カラオケ・のど自慢
     */
    KARAOKE_SINGING_CONTEST(MUSIC, 0x7, "カラオケ・のど自慢", "Karaoke/singing contest"),
    /**
     * 民謡・邦楽
     */
    FOLK_JAPANESE_MUSIC(MUSIC, 0x8, "民謡・邦楽", "Folk/Japanese music"),
    /**
     * 童謡・キッズ
     */
    CHILDREN_S_SONG(MUSIC, 0x9, "童謡・キッズ", "Children's song"),
    /**
     * 民族音楽・ワールドミュージック
     */
    ETHNIC_WORLD_MUSIC(MUSIC, 0xa, "民族音楽・ワールドミュージック", "Ethnic/world music"),
    /**
     * その他
     */
    OTHERS_MUSIC(MUSIC, 0xf, "その他", "Other"),
    /**
     * クイズ
     */
    QUIZ(VARIETY, 0x0, "クイズ", "Quiz"),
    /**
     * ゲーム
     */
    GAME(VARIETY, 0x1, "ゲーム", "Game"),
    /**
     * トークバラエティ
     */
    TALK_SHOW(VARIETY, 0x2, "トークバラエティ", "Talk show"),
    /**
     * お笑い・コメディ
     */
    COMEDY(VARIETY, 0x3, "お笑い・コメディ", "Comedy"),
    /**
     * 音楽バラエティ
     */
    MUSICAL_VARIETY_SHOW(VARIETY, 0x4, "音楽バラエティ", "Musical variety show"),
    /**
     * 旅バラエティ
     */
    TRAVEL_VARIETY_SHOW(VARIETY, 0x5, "旅バラエティ", "Travel variety show"),
    /**
     * 料理バラエティ
     */
    COOKING_VARIETY_SHOW(VARIETY, 0x6, "料理バラエティ", "Cooking variety show"),
    /**
     * その他
     */
    OTHERS_VARIETY(VARIETY, 0xf, "その他", "Other"),
    /**
     * 洋画
     */
    OVERSEAS_MOVIE(CINEMA, 0x0, "洋画", "Overseas movie"),
    /**
     * 邦画
     */
    JAPANESE_MOVIE(CINEMA, 0x1, "邦画", "Japanese movie"),
    /**
     * アニメ
     */
    ANIMATION_MOVIE(CINEMA, 0x2, "アニメ", "Animation movie"),
    /**
     * その他
     */
    OTHERS_CINEMA(CINEMA, 0xf, "その他", "Other"),
    /**
     * 国内アニメ
     */
    JAPANESE_ANIMATION(ANIME, 0x0, "国内アニメ", "Japanese animation"),
    /**
     * 海外アニメ
     */
    OVERSEAS_ANIMATION(ANIME, 0x1, "海外アニメ", "Overseas animation"),
    /**
     * 特撮
     */
    SPECIAL_EFFECTS_PROGRAM(ANIME, 0x2, "特撮", "Special effects program"),
    /**
     * その他
     */
    OTHERS_ANIME(ANIME, 0xf, "その他", "Other"),
    /**
     * 社会・時事
     */
    SOCIAL_TOPICAL_NEWS(DOCUMENTARY, 0x0, "社会・時事", "Social/topical news"),
    /**
     * 歴史・紀行
     */
    HISTORY_TRAVEL(DOCUMENTARY, 0x1, "歴史・紀行", "History/travel"),
    /**
     * 自然・動物・環境
     */
    NATURE_ANIMAL_ENVIRONMENT(DOCUMENTARY, 0x2, "自然・動物・環境", "Nature/animal/environment"),
    /**
     * 宇宙・科学・医学
     */
    UNIVERSE_SCIENCE_SCIENCE_OF_MEDICINE(DOCUMENTARY, 0x3, "宇宙・科学・医学", "Universe/science/science of medicine"),
    /**
     * カルチャー・伝統文化
     */
    CULTURE_TRADITIONAL_CULTURE(DOCUMENTARY, 0x4, "カルチャー・伝統文化", "Culture/traditional culture"),
    /**
     * 文学・文芸
     */
    LITERATURE_LIBERAL_ARTS(DOCUMENTARY, 0x5, "文学・文芸", "Literature/liberal arts"),
    /**
     * スポーツ
     */
    SPORTS(DOCUMENTARY, 0x6, "スポーツ", "Sports"),
    /**
     * ドキュメンタリー全般
     */
    GENERAL_DOCUMENTARY(DOCUMENTARY, 0x7, "ドキュメンタリー全般", "General documentary"),
    /**
     * インタビュー・討論
     */
    INTERVIEW_DISCUSSION(DOCUMENTARY, 0x8, "インタビュー・討論", "Interview/discussion"),
    /**
     * その他
     */
    OTHERS_DOCUMENTARY(DOCUMENTARY, 0xf, "その他", "Other"),
    /**
     * 現代劇・新劇
     */
    MODERN_PLAY(STAGE, 0x0, "現代劇・新劇", "Modern play"),
    /**
     * ミュージカル
     */
    MUSICAL(STAGE, 0x1, "ミュージカル", "Musical"),
    /**
     * ダンス・バレエ
     */
    DANCE_BALLET(STAGE, 0x2, "ダンス・バレエ", "Dance/ballet"),
    /**
     * 落語・演芸
     */
    RAKUGO_ENTERTAINMENT(STAGE, 0x3, "落語・演芸", "Comic monologue /***/(Rakugo)/ entertainment"),
    /**
     * 歌舞伎・古典
     */
    KABUKI_CLASSICAL_PLAY(STAGE, 0x4, "歌舞伎・古典", "Kabuki/classical play"),
    /**
     * その他
     */
    OTHERS_STAGE(STAGE, 0xf, "その他", "Other"),
    /**
     * 旅・釣り・アウトドア
     */
    TRAVEL_FISHING_OUTDOORS(HOBBY, 0x0, "旅・釣り・アウトドア", "Travel/fishing/outdoors"),
    /**
     * 園芸・ペット・手芸
     */
    GARDENING_PET_HANDICRAFT(HOBBY, 0x1, "園芸・ペット・手芸", "Gardening/pet/ handicraft"),
    /**
     * 音楽・美術・工芸
     */
    MUSIC_ART_ARTIFICE(HOBBY, 0x2, "音楽・美術・工芸", "Music/art/ artifice"),
    /**
     * 囲碁・将棋
     */
    JAPANESE_GO_SHOGI(HOBBY, 0x3, "囲碁・将棋", "Japanese go/chess /***/(Shogi)"),
    /**
     * 麻雀・パチンコ
     */
    MAHJONG_PACHINKO(HOBBY, 0x4, "麻雀・パチンコ", "Mah-jong/Pachinko"),
    /**
     * 車・オートバイ
     */
    AUTOMOBILE_MOTORCYCLE(HOBBY, 0x5, "車・オートバイ", "Automobile/motorcycle"),
    /**
     * コンピュータ・ＴＶゲーム
     */
    COMPUTER_TV_GAME(HOBBY, 0x6, "コンピュータ・ＴＶゲーム", "Computer/TV game"),
    /**
     * 会話・語学
     */
    CONVERSATION_LANGUAGE(HOBBY, 0x7, "会話・語学", "Conversation/language"),
    /**
     * 幼児・小学生
     */
    INFANTS_PRIMARY_SCHOOL_STUDENTS(HOBBY, 0x8, "幼児・小学生", "Infants/primary school students"),
    /**
     * 中学生・高校生
     */
    JUNIOR_SENIOR_HIGH_SCHOOL_STUDENTS(HOBBY, 0x9, "中学生・高校生", "Junior/senior high school students"),
    /**
     * 大学生・受験
     */
    COLLEGE_PREPARATORY_STUDENTS(HOBBY, 0xa, "大学生・受験", "College/preparatory students"),
    /**
     * 生涯教育・資格
     */
    LIFELONG_EDUCATION_QULIFICATION(HOBBY, 0xb, "生涯教育・資格", "Lifelong education/qulification"),
    /**
     * 教育問題
     */
    EDUCATIONAL_PROBLEM(HOBBY, 0xc, "教育問題", "Educational problem"),
    /**
     * その他
     */
    OTHERS_HOBBY(HOBBY, 0xf, "その他", "Other"),
    /**
     * 高齢者
     */
    AGED_PERSONS(WELFARE, 0x0, "高齢者", "Aged persons"),
    /**
     * 障害者
     */
    HANDICAPPED_PERSON(WELFARE, 0x1, "障害者", "Handicapped person"),
    /**
     * 社会福祉
     */
    SOCIAL_WELFARE(WELFARE, 0x2, "社会福祉", "Social welfare"),
    /**
     * ボランティア
     */
    VOLUNTEERS(WELFARE, 0x3, "ボランティア", "Volunteers"),
    /**
     * 手話
     */
    SIGN_LANGUAGE(WELFARE, 0x4, "手話", "Sign language"),
    /**
     * 文字（字幕）
     */
    CAPTION(WELFARE, 0x5, "文字（字幕）", "Texts /***/(caption)"),
    /**
     * 音声解説
     */
    AUDIO_COMMENTARY(WELFARE, 0x6, "音声解説", "Audio commentary"),
    /**
     * その他
     */
    OTHERS_WELFARE(WELFARE, 0xf, "その他", "Other"),
    /**
     * BS/地上デジタル放送用番組付属情報
     */
    BS_OR_TERRESTRIAL_DIGITAL_BROADCASTING_FOR_THE_PROGRAM_INCLUDED_INFORMATION(USER_NIBBLE, 0x0, "BS/地上デジタル放送用番組付属情報", "BS / terrestrial digital broadcasting for the program included information"),
    /**
     * 広帯域CS デジタル放送用拡張
     */
    BROADBAND_CS_DIGITAL_BROADCASTING_FOR_THE_EXTENSION(USER_NIBBLE, 0x1, "広帯域CS デジタル放送用拡張", "Broadband CS digital broadcasting for the extension"),
    /**
     * 衛星デジタル音声放送用拡張
     */
    SATELLITE_DIGITAL_AUDIO_BROADCASTING_FOR_EXPANSION(USER_NIBBLE, 0x2, "衛星デジタル音声放送用拡張", "Satellite digital audio broadcasting for expansion"),
    /**
     * サーバー型番組付属情報
     */
    SERVER_TYPE_PROGRAM_SUPPLIED_INFORMATION(USER_NIBBLE, 0x3, "サーバー型番組付属情報", "Server type program supplied information"),
    /**
     * IP 放送用番組付属情報
     */
    IP_BROADCAST_PROGRAM_SUPPLIED_INFORMATION(USER_NIBBLE, 0x4, "IP 放送用番組付属情報", "IP broadcast program supplied information");

    //逆引きリスト
    private static final Map<MultiKey<Integer>, NIBBLE_LEVEL_2> rev = new HashMap<>();

    static {
        {
            for (NIBBLE_LEVEL_2 nibble2 : NIBBLE_LEVEL_2.values()) {
                MultiKey<Integer> key = new MultiKey(nibble2.getParentNibble().getCode(), nibble2.getCode());
                if (!rev.containsKey(key)) {
                    rev.put(key, nibble2);
                } else {
                    MessageFormat msg = new MessageFormat("ジャンルコードが重複して登録されています。重複したジャンルコード={0} 重複したときの定数名={1}");
                    Object[] parameters = {Integer.toHexString(nibble2.getCode()), nibble2};
                    throw new IllegalArgumentException(msg.format(parameters));
                }

            }
        }
    }

    ;
           
    /**
     * ジャンルコードから定義済みの定数を逆引きする。
     *
     * @param parentCode ジャンル大分類コード
     * @param code ジャンル中分類コード
     * @return ジャンル中分類コードを含む定数。なければnull。
     */
        public static synchronized NIBBLE_LEVEL_2 reverseLookUp(int parentCode, int code) {
        MultiKey<Integer> key = new MultiKey(parentCode, code);
        return rev.get(key);
    }

    private final NIBBLE_LEVEL_1 parentNibble;
    private final int code;
    private final String nibble_en;
    private final String nibble_jp;

    private NIBBLE_LEVEL_2(NIBBLE_LEVEL_1 parentNibble, int code, String nibble_jp, String nibble_en) {
        this.parentNibble = parentNibble;
        if (this.parentNibble == null) {
            throw new NullPointerException("第一段階分類符号 (ジャンル大分類)がnullです。");
        }
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

    public NIBBLE_LEVEL_1 getParentNibble() {
        return parentNibble;
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
        return "NIBBLE_LEVEL_2{" + "parentNibble=" + parentNibble + ", code=" + code + ", nibble_jp=" + nibble_jp + ", nibble_en=" + nibble_en + '}';
    }

}
