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
     * @return target1～target3が全て同じオブジェクトで、下記を満たすならtrue。
     * @see java.lang.Object.equals
     */
    public boolean check(final T target1, final T target2, final T target3) {
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

    private synchronized void checkNull(T target1) {
        if (target1 == null) {
            throw new NullPointerException("nullのセットは禁止です。");
        }
    }

    private boolean singleParm(T target) {
        return this.null_testEquals(target) && this.reflexive_testEquals(target);
    }

    private boolean doubleParms(T target1, T target2) {
        return this.symmetric_testEquals(target1, target2) && this.consistent_testEquals(target1, target2);
    }

    /**
     * @return target1がnullを評価したときの値がfalseならばtrue。<br>
     */
    private boolean null_testEquals(T target1) {
        checkNull(target1);
        boolean ret;
        ret = !target1.equals(null);
        LOG.info("戻り値 = " + ret);
        return ret;
    }

    /**
     * @return target1がtarget1を評価したときの値がtrueならばtrue。<br>
     */
    private boolean reflexive_testEquals(T target1) {
        checkNull(target1);
        boolean ret;
        ret = target1.equals(target1);
        LOG.info("戻り値 = " + ret);
        return ret;
    }

    /**
     * @return 下の2つが等しい値ならばtrue。<br>
     * 1.target1がtarget2を評価したときの値<br>
     * 2.target2がtarget1を評価したときの値<br>
     */
    private boolean symmetric_testEquals(T target1, T target2) {
        checkNull(target1);
        checkNull(target2);
        boolean result1 = target1.equals(target2);
        boolean result2 = target2.equals(target1);
        boolean ret;
        ret = (result1 == result2);
        LOG.info("戻り値 = " + ret);
        return ret;
    }

    /**
     * @return <br>
     * 1:引数として渡されたオブジェクトがすべて同じ物である場合、無条件にtrue<br>
     * 2:参照先のメソッドの全ての引数に同じオブジェクトをセットした場合、どのオブジェクトの場合でもその戻り値がtrue。<br>
     * かつ、参照先のメソッドの引数のうち、少なくとも1つに違うオブジェクトをセットした場合、どのオブジェクトの場合でもその戻り値がfalse<br>
     * @see this._transitive_testEquals
     */
    private boolean transitive_testEquals(T target1, T target2, T target3) {
        if (_transitive_testEquals(target1, target2, target3)) {
            LOG.trace("渡されたオブジェクトが全部同じ。無条件にtrueになる。");
            return true;
        }

        //全部trueになる。
        final boolean result_1 = this._transitive_testEquals(target1, target1, target1);
        final boolean result_2 = this._transitive_testEquals(target2, target2, target2);
        final boolean result_3 = this._transitive_testEquals(target3, target3, target3);
        final boolean all_same;
        if (result_1 == true && result_2 == true && result_3 == true) {
            all_same = true;
        } else {
            all_same = false;
        }
        LOG.info("trueになる = " + all_same);

        //引数として渡されたオブジェクトの少なくとも1つが他と違う場合、falseになる。
        final boolean result_1_2 = this._transitive_testEquals(target1, target1, target2);
        final boolean result_1_3 = this._transitive_testEquals(target1, target1, target3);
        final boolean result_2_3 = this._transitive_testEquals(target2, target2, target3);
        final boolean not_same;
        if (result_1_2 == true && result_1_3 == true && result_2_3 == true) {
            not_same = true;
        } else {
            not_same = false;
        }
        LOG.info("falseになる = " + not_same);

        final boolean ret;
        if (all_same == true && not_same == false) {
            ret = true;
        } else {
            ret = false;
        }

        LOG.info("戻り値 = " + ret);
        return ret;
    }

    /**
     * @return <br>
     * target1.equals(target2) = true<br>
     * かつ <br>
     * target2.equals(target3) = true<br>
     * のとき、 <br>
     * target3.equals(target1) = true<br>
     * ならば、true<br>
     */
    private boolean _transitive_testEquals(T target1, T target2, T target3) {

        checkNull(target1);
        checkNull(target2);
        checkNull(target3);

        boolean result1 = target1.equals(target2);
        boolean result2 = target2.equals(target3);
        boolean result3 = target3.equals(target1);

        boolean ret;
        if (result1 == true && result2 == true && result3 == true) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    /**
     * @return 下の2つが等しい値ならばtrue。<br>
     * 1.target1がtarget2を評価したときの値(1回め)<br>
     * 2.target1がtarget2を評価したときの値(2回め)<br>
     */
    private boolean consistent_testEquals(T target1, T target2) {
        checkNull(target1);
        checkNull(target2);
        boolean result1 = target1.equals(target2);
        boolean result2 = target1.equals(target2);
        boolean ret;
        ret = (result1 == result2);
        return ret;
    }

}
