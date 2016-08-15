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
package testtool;

import java.lang.invoke.MethodHandles;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * equals()の実装チェックを行う。(渡されたオブジェクトの内容を変更することはしない。)<br>
 * また、異なるクラスのオブジェクトを混ぜて渡した場合、例外を発生させる。
 *
 * @author normal
 * @param <T>
 */
public class EqualsChecker<T> {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    public EqualsChecker() {
    }

    /**
     * @param target1
     * @param target2
     * @param target3
     * @return
     * target1、target2、target3の全てがnullではない同一のクラスから作成されたオブジェクトで、反射性を守っている。<br>
     * かつ、3つのうち少なくとも1つが他と異なっている(3つの中から選んだ2つのうちどれか１つに対してa.equals(b)==falseが成立する)とき、<br>
     * 推移性チェックがfalseならばtrue<br>
     *
     * @see java.lang.Object.equals
     * @see EqualsChecker.transitive_testEquals
     */
    public boolean check_not_same(final T target1, final T target2, final T target3) {
        boolean ret;
        ret = true;
        LOG.info("target1をチェック");
        ret = ret && this.singleParm(target1);
        if (ret == false) {
            return ret;
        }

        LOG.info("target2をチェック");
        ret = ret && this.singleParm(target2);
        if (ret == false) {
            return ret;
        }

        LOG.info("target3をチェック");
        ret = ret && this.singleParm(target3);
        if (ret == false) {
            return ret;
        }

        LOG.info("target1とtarget2とtarget3をチェック");
        ret = ret && !this.transitive_testEquals(target1, target2, target3);
        LOG.info("戻り値 = " + ret);
        return ret;
    }

    /**
     * @param target1
     * @param target2
     * @param target3
     * @return
     * target1、target2、target3の全てがnullではない同一のクラスから作成されたオブジェクトで、、3つの中から選んだどの2つに対してもa.equals(b)==trueが成立するならtrue。<br>
     *
     * @see java.lang.Object.equals
     */
    public boolean check_same(final T target1, final T target2, final T target3) {
        boolean ret;
        ret = true;

        LOG.info("target1をチェック");
        ret = ret && this.singleParm(target1);
        if (ret == false) {
            return ret;
        }

        LOG.info("target2をチェック");
        ret = ret && this.singleParm(target2);
        if (ret == false) {
            return ret;
        }

        LOG.info("target3をチェック");
        ret = ret && this.singleParm(target3);
        if (ret == false) {
            return ret;
        }

        LOG.info("target1とtarget2をチェック");
        ret = ret && this.doubleParms(target1, target2);
        if (ret == false) {
            return ret;
        }

        LOG.info("target2とtarget3をチェック");
        ret = ret && this.doubleParms(target2, target3);
        if (ret == false) {
            return ret;
        }

        LOG.info("target3とtarget1をチェック");
        ret = ret && this.doubleParms(target3, target1);
        if (ret == false) {
            return ret;
        }

        LOG.info("target1とtarget2とtarget3をチェック");
        ret = ret && this.transitive_testEquals(target1, target2, target3);
        LOG.info("戻り値 = " + ret);
        return ret;
    }

    /**
     * @return target1がnullではないなら何もしない。nullの場合は例外を発生させる。<br>
     */
    private synchronized void checkNull(T target1) throws NullPointerException {
        if (target1 == null) {
            throw new NullPointerException("nullのセットは禁止です。");
        }
        LOG.info("引数の非nullチェックを通過。");
    }

    private boolean singleParm(T x) {
        return this.null_testEquals(x) && this.reflexive_testEquals(x);
    }

    private boolean doubleParms(T x, T y) {
        this.isSameClass(x, y);
        return this.symmetric_testEquals(x, y) && this.consistent_testEquals(x, y);
    }

    /**
     * @return target1がnullを評価したときの値がfalseならばtrue。<br>
     */
    private boolean null_testEquals(T x) {
        checkNull(x);
        boolean ret;
        ret = !x.equals(null);
        LOG.info("null評価チェック。 戻り値 = " + ret);
        return ret;
    }

    /**
     * @return target1がtarget1を評価したときの値がtrueならばtrue。<br>
     */
    private boolean reflexive_testEquals(T x) {
        checkNull(x);
        boolean ret;
        ret = x.equals(x);
        LOG.info("反射性チェック 戻り値 = " + ret);
        return ret;
    }

    /**
     * xとyが同じクラスから作成されたオブジェクトならば何もしない。そうでない場合は例外を発生させる<br>
     *
     * @param x
     * @param y
     */
    public void isSameClass(T x, T y) throws IllegalArgumentException {
        Class<?> xC = x.getClass();
        Class<?> yC = y.getClass();
        boolean ret = (xC == yC);
        if (ret == false) {
            throw new IllegalArgumentException("xとyは同じクラスではありません。 x = " + xC + " y = " + yC);
        }
        LOG.info("引数のクラスチェックを通過。");
    }

    /**
     * @return 下の2つが等しい値ならばtrue。<br>
     * 1.xがyを評価したときの値<br>
     * 2.yがxを評価したときの値<br>
     */
    private boolean symmetric_testEquals(T x, T y) {
        checkNull(x);
        checkNull(y);
        boolean result1 = x.equals(y);
        boolean result2 = y.equals(x);
        boolean ret;
        ret = (result1 == result2);
        if (ret == true) {
            LOG.info("対称性チェック 戻り値 = " + ret);
        }
        return ret;
    }

    /**
     *
     * @return <br>
     * x.equals(y) = true<br>
     * かつ <br>
     * y.equals(z) = true<br>
     * のとき、 <br>
     * z.equals(x) = true<br>
     * ならば、true<br>
     */
    private boolean transitive_testEquals(T x, T y, T z) {

        checkNull(x);
        checkNull(y);
        checkNull(z);

        boolean result1 = x.equals(y);
        boolean result2 = y.equals(z);
        boolean result3 = z.equals(x);

        boolean ret;
        if (result1 == true && result2 == true && result3 == true) {
            ret = true;
        } else {
            ret = false;
        }
        LOG.info("推移性チェック 戻り値 = " + ret);
        return ret;
    }

    /**
     * @return 下の2つが等しい値ならばtrue。<br>
     * 1.xがyを評価したときの値(1回め)<br>
     * 2.yがxを評価したときの値(2回め)<br>
     */
    private boolean consistent_testEquals(T x, T y) {
        checkNull(x);
        checkNull(y);
        boolean result1 = x.equals(y);
        boolean result2 = x.equals(y);
        boolean ret;
        ret = (result1 == result2);
        LOG.info("整合性チェック 1回め = " + result1 + " 2回目 = " + result2 + " 戻り値 = " + ret);
        return ret;
    }

}
