/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.ts.packet;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.apache.commons.lang3.Range;
import util.ReverseLookUpMapFactory;

/**
 * TSパケットの分類に使うPIDのうち、動的に定義されていないものの一部。 0x0000=PAT 0x0001=CAT 0x0002―0x000F=未定義
 * 0x0010=NIT 0x0011―0x1FFE=PAT、CAT、NIT及びヌルパケット以外に割り当てることができる領域 0x1FFF=ヌルパケット
 *
 * (大体このくらいあるが、大抵はPATを見ない限り何を意味するかはわからない)<br>
 * 0x0,0x1,0x10,0x11,0x12,0x14,0x23,0x24,0x27,0x28,0x29,0x100,0x110,0x111,0x130,0x137,0x138,0x140,0x160,0x161,0x162,0x163,0x164,0x165,0x1f0,0x1ff,0x238,<br>
 * 0x3f0,0x480,0x481,0x4f0,0x580,0x581,0x583,0x587,0x589,0x58a,0x58b,0x5a6,0x5f4,0x5ff,0x781,0x900,0x901,0x902,0xaef,0xb93,0xe52,0x1380,0x161a,0x1c61,<br>
 * 0x1c63,0x1d76,0x1df0,0x1f22,0x1f86,0x1fc8,0x1ffd)<br>
 */
public enum PROGRAM_ID {

    /**
     * PAT(Program Association Table) MPEG-2
     * TSストリーム内にどのような映像が含まれているのかを記述したMPEG-2 TSパケット。 <br>
     * PMTのPIDが列挙されている。<br>
     * PATのPIDは0と定められている。<br>
     */
    PAT("PAT", 0x0),
    UNDEFINED("未定義", 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xa, 0xb, 0xc, 0xd, 0xe, 0xf),
    CAT("CAT", 0x1),
    /**
     * ネットワーク情報テーブル(たぶん使わない)
     */
    NIT("ネットワーク情報テーブル", 0x10),
    /**
     * サービス記述テーブルSDT（Service Description Table）<br>
     * 編成チャンネルの名称、放送事業者の名称など、編成チャンネルに関する情報を伝送する。テーブルid=0x42<br>
     *
     * ブーケアソシエーションテーブルBAT(SDTとはテーブルIDが違うので識別はできる。)<br>
     * ブーケの名称、含まれる編成チャンネルなど、ブーケ（編成チャンネルの集合）に関する情報の指示(たぶん使わない)テーブルid=不明<br>
     */
    SDT_OR_BAT("サービス記述テーブルもしくはブーケアソシエーションテーブル", 0x11),
    /**
     * イベント情報テーブル。番組の名称、放送日時、内容の説明など、番組に関する情報の指示 (地上波、衛星共用=0x12 地上波専用=0x26,0x27)
     */
    EIT_GR_ST("イベント情報テーブル", 0x12, 0x26, 0x27),
    /**
     * nullパケット
     */
    NULL_PACKET("nullパケット", 0x1FFF);

    /**
     * 逆引きリスト
     */
    private static final Map<Integer, PROGRAM_ID> rev;

    private static final Function<PROGRAM_ID, Set<Integer>> func1 = (PROGRAM_ID t) -> t.getPids();
    //逆引きリスト生成
    private static final ReverseLookUpMapFactory<Integer, Set<Integer>, PROGRAM_ID> revmapf = new ReverseLookUpMapFactory<>(func1);

    static {
        for (PROGRAM_ID pid : PROGRAM_ID.values()) {
            revmapf.put(pid);
        }
        rev = revmapf.getDict();
    }

    ;

           
    /**
     * テーブルIDから定義済みの定数を逆引きする。
     *
     * @param pid テーブルID
     * @return テーブルIDを含む定数。なければnull。
     */
    public static synchronized PROGRAM_ID reverseLookUp(int pid) {
        return rev.get(pid);
    }

    private final String pidName;
    private final Set<Integer> pids;

    private PROGRAM_ID(String pidName, Integer pid, Integer... pids) {

        this.pidName = pidName;
        if ((this.pidName == null) || ("".equals(this.pidName))) {
            throw new IllegalArgumentException("タグ名が指定されていないか空文字です。");
        }

        List<Integer> t = new ArrayList<>();
        if (pid != null) {
            t.add(pid);
        } else {
            throw new NullPointerException("PIDが指定されていません。");
        }
        if (pids != null) {
            t.addAll(Arrays.asList(pids));
        }
        Range<Integer> r = Range.between(0x0000, 0x1FFF);
        for (Integer i : t) {
            if (!r.contains(i)) {
                MessageFormat msg = new MessageFormat("PIDが範囲外の値です。PID={0}");
                Object[] parameters = {Integer.toHexString(i)};
                throw new IllegalArgumentException(msg.format(parameters));
            }
        }
        Set<Integer> temp = Collections.synchronizedSet(new HashSet<Integer>());
        temp.addAll(t);
        this.pids = Collections.unmodifiableSet(temp);
    }

    /**
     * この定数に引数で渡されたPIDが含まれているか?
     *
     * @param pid PID
     * @return 含まれていればtrue
     */
    public synchronized boolean contains(int pid) {
        return this.pids.contains(pid);
    }

    public String getPidName() {
        return pidName;
    }

    /**
     * @return この定数にセットされているpidの一覧
     */
    public Set<Integer> getPids() {
        return pids;
    }

    @Override
    public synchronized String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : this.pids) {
            s.append("[");
            s.append(Integer.toHexString(i));
            s.append("]");
        }
        String set = s.toString();
        MessageFormat msg = new MessageFormat("{0}(pidName={1},PIDs={2})");
        Object[] parameters = {super.toString(), this.getPidName(), set};
        return msg.format(parameters);
    }
}
