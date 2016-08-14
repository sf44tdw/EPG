package libepg.epg.section.descriptor.seriesdescriptor;

import libepg.util.bytearray.ByteConverter;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import libepg.epg.section.descriptor.Descriptor;
import libepg.epg.util.Aribstr;
import libepg.epg.util.datetime.DateTimeFieldConverter;


/**
 * シリーズ記述子 シリーズ記述子は、シリーズ番組を識別するために用いる。
 */
public class SeriesDescriptor extends Descriptor {

    public SeriesDescriptor(Descriptor discriptor) {
        super(discriptor);
    }

    /**
     * series_id(シリーズ識別):これは16 ビットのフィールドで、シリーズをユニークに識別するための識別子である。
     *
     * @return
     */
    public synchronized int getSeries_id() {
        byte[] t = new byte[2];
        System.arraycopy(this.getDescriptor_Body(), 0, t, 0, t.length);
        return ByteConverter.bytesToInt(t);
    }

    /**
     * repeat_label(再放送ラベル)：この4 ビットのフィールドは、
     * シリーズの放送期間とシリーズの再放送の放送期間が重なる場合に、編成を区別するラベルとして用いる。
     * オリジナルのシリーズの放送には「0x0」を与える。
     */
    public synchronized int getRepeat_label() {
        int temp = ByteConverter.byteToInt(this.getDescriptor_Body()[2]);
        temp = temp >>> 4;
        return temp;
    }

    /**
     * 編成パターン<br>
     */
    public static enum PROGRAM_PATTERN {
        /**
         * 0x0 不定期(0x1 – 0x7で定義されるもの以外)<br>
         */
        IRREGULAR(0x0, "不定期(0x1 – 0x7で定義されるもの以外)"),
        /**
         * 0x1 帯番組(毎日、平日のみ毎日、土・日のみなど)、週に複数回の編成<br>
         */
        ACROSS_THE_BOARD(0x1, "帯番組(毎日、平日のみ毎日、土・日のみなど)、週に複数回の編成"),
        /**
         * 0x2 週に1回程度の編成<br>
         */
        ONCE_A_WEEK(0x2, "週に1回程度の編成"),
        /**
         * 0x3 月に1回程度の編成<br>
         */
        MONTHLY(0x3, "月に1回程度の編成"),
        /**
         * 0x4 同日内に複数話数の編成<br>
         */
        EVERY_DAY_MORE_THAN_ONCE(0x4, "同日内に複数話数の編成"),
        /**
         * 0x5 長時間番組の分割 <br>
         */
        DIVISION_OF_THE_LONG_PROGRAM(0x5, "長時間番組の分割 "),
        /**
         * 0x6 定期または不定期の蓄積用の編成<br>
         */
        FOR_THE_ACCUMULATION(0x6, "定期または不定期の蓄積用の編成"),
        /**
         * 0x7 未定義
         */
        UNDEFINED(0x7, "未定義");
        //逆引きリスト
        private static final Map<Integer, PROGRAM_PATTERN> rev = new ConcurrentHashMap<>();

        static {
            {
                for (PROGRAM_PATTERN pp : PROGRAM_PATTERN.values()) {
                    if (!rev.containsKey(pp.getCode())) {
                        rev.put(pp.getCode(), pp);
                    } else {
                        MessageFormat msg = new MessageFormat("編成パターンが重複して登録されています。重複した編成パターン={0} 重複したときの定数名={1}");
                        Object[] parameters = {Integer.toHexString(pp.getCode()), pp};
                        throw new IllegalArgumentException(msg.format(parameters));
                    }

                }
            }
        }

        ;
           
    /**
     * ジャンルコードから定義済みの定数を逆引きする。
     *
     * @param value 編成パターンコード
     * @return コードを含む定数。なければnull。
     */
        public static synchronized PROGRAM_PATTERN reverseLookUp(int value) {
            return rev.get(value);
        }
        private final int code;
        private final String label;

        private PROGRAM_PATTERN(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public int getCode() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return super.toString() + "{" + "code=" + code + ", label=" + label + '}';
        }

    }

    /**
     * program_pattern(編成パターン):この3 ビットのフィールドは、シリーズ番組の編成のパターンを表す。<br>
     * これによりシリーズに属するイベントが次にいつ現れるかの目安がわかる。<br>
     *
     * @return 編成パターンの定数。
     */
    public synchronized PROGRAM_PATTERN getProgram_pattern() {
        int temp = ByteConverter.byteToInt(this.getDescriptor_Body()[2]);
        temp = temp & 0xe;
        temp = temp >>> 1;
        PROGRAM_PATTERN pp = PROGRAM_PATTERN.reverseLookUp(temp);
        if (pp == null) {
            throw new IllegalStateException("編成パターンの数値が登録されているものと一致しません。");
        }
        return pp;
    }

    /**
     * expire_date_valid_flag(有効期限フラグ)：この1
     * ビットのフラグは、次に続くexpire_dateの値が有効であることを示す。 シリーズの終了予定日の値が有効な場合、この値を「1」とする。
     */
    public synchronized int getExpire_date_valid_flag() {
        int temp = ByteConverter.byteToInt(this.getDescriptor_Body()[2]);
        temp = temp & 0x1;
        return temp;
    }

    /**
     * expire_date(有効期限):この16 ビットのフィールドは、シリーズが有効な期限の年月日をMJD の下位16 ビットで表す。
     * 何らかの原因で最終回のイベントを認識できなかった場合も、この日付を過ぎると受信機はシリーズが終了したと認識する。
     *
     * @return バイト列。
     */
    public synchronized byte[] getExpire_date() {
        byte[] t = new byte[2];
        System.arraycopy(this.getDescriptor_Body(), 3, t, 0, t.length);
        return t;
    }

    /**
     * expire_date(有効期限)
     *
     * @return タイムスタンプに変換された有効期限。l
     * @throws java.text.ParseException
     */
    public synchronized Timestamp getExpire_date_Object() throws ParseException {
        if (this.getExpire_date_valid_flag() != 1) {
            throw new IllegalStateException("有効期限が無効になっています。");
        }
        byte[] t = {0, 0, 0, 0, 0};
        System.arraycopy(this.getExpire_date(), 0, t, 0, t.length);
        try {
            return (DateTimeFieldConverter.BytesToSqlDateTime(t));
        } catch (ParseException ex) {
            LOG.warn("有効期限を示すタイムスタンプの生成に失敗しました。", ex);
            throw ex;
        }
    }

    /**
     * episode_number(話数):この12 ビットのフィールドは、この記述子が示す番組の、シリーズ内の話数を示す。第1 回から第4095
     * 回まで記載できる。 話数がこれを超える場合はシリーズを別に定義する。 連続番組の場合で番組回数が定義できない場合は、「0x000」とする。
     *
     * @return
     */
    public synchronized int getEpisode_number() {
        byte[] t = new byte[3];
        System.arraycopy(this.getDescriptor_Body(), 4, t, 0, t.length);
        return ByteConverter.bytesToInt(t);
    }

    /**
     * last_episode_number(番組総数):この12 ビットのフィールドは、当該シリーズ番組の番組総数を示す。 第1 回から第4095
     * 回まで記載できる。番組総数がこれを超える場合は、シリーズを別に定義する。 最終回が未定の場合、「0x000」とする。
     *
     * @return
     */
    public synchronized int getLast_episode_number() {
        byte[] t = new byte[3];
        System.arraycopy(this.getDescriptor_Body(), 7, t, 0, t.length);
        return ByteConverter.bytesToInt(t);
    }

    /**
     * series_name_char(シリーズ名):この文字符号フィールドでは、シリーズ名が伝送される。文字情報の符号化については、付録A 参照。
     *
     * @return
     */
    public synchronized byte[] getSeries_name_char() {
        byte[] t = new byte[this.getDescriptor_Body().length - 8];
        System.arraycopy(this.getDescriptor_Body(), 8, t, 0, t.length);
        return t;
    }

    /**
     * series_name_char(シリーズ名):この文字符号フィールドでは、シリーズ名が伝送される。文字情報の符号化については、付録A 参照。
     *
     * @return
     */
    public synchronized String getSeries_name_String() {
        return Aribstr.AribToString(this.getSeries_name_char());

    }

}
