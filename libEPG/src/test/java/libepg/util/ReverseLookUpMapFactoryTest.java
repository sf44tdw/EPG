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
package libepg.util;

import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import testtool.testrule.regexmessagerule.ExpectedExceptionMessage;
import testtool.testrule.regexmessagerule.ExpectedExceptionRule;
import util.ReverseLookUpMapFactory;

/**
 *
 * @author normal
 */
public class ReverseLookUpMapFactoryTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ReverseLookUpMapFactoryTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private enum SOME_ENUM_OK {
        A("a", 0), B("b", 1, 2), C("c", 3, 4, 5), D("d", 6, 7, 8, 9);

        private final Set<Integer> setMember;
        private final String stringMember;

        private SOME_ENUM_OK(String stringMember, Integer i, Integer... is) {
            this.stringMember = stringMember;
            if ((this.stringMember == null) || ("".equals(this.stringMember))) {
                throw new IllegalArgumentException("文字列が指定されていないか空文字です。");
            }

            List<Integer> t = new ArrayList<>();
            if (i != null) {
                t.add(i);
            } else {
                throw new NullPointerException("数字が1つも指定されていません。");
            }
            if (is != null) {
                t.addAll(Arrays.asList(is));
            }

            Set<Integer> temp = Collections.synchronizedSet(new HashSet<>());
            temp.addAll(t);

            this.setMember = Collections.unmodifiableSet(temp);
        }

        public Set<Integer> getSetMember() {
            return setMember;
        }

        public String getStringMember() {
            return stringMember;
        }

        @Override
        public synchronized String toString() {
            StringBuilder s = new StringBuilder();
            for (int i : this.setMember) {
                s.append("[");
                s.append(Integer.toHexString(i));
                s.append("]");
            }
            String set = s.toString();
            MessageFormat msg = new MessageFormat("{0}({1},serviceIds={2})");
            Object[] parameters = {super.toString(), this.stringMember, set};
            return msg.format(parameters);
        }

    }

    //数値が重複している。
    private enum SOME_ENUM_NG {
        A("a", 0), B("b", 1, 2), C("c", 0, 2, 3);

        private final Set<Integer> setMember;
        private final String stringMember;

        private SOME_ENUM_NG(String stringMember, Integer i, Integer... is) {
            this.stringMember = stringMember;
            if ((this.stringMember == null) || ("".equals(this.stringMember))) {
                throw new IllegalArgumentException("文字列が指定されていないか空文字です。");
            }

            List<Integer> t = new ArrayList<>();
            if (i != null) {
                t.add(i);
            } else {
                throw new NullPointerException("数字が1つも指定されていません。");
            }
            if (is != null) {
                t.addAll(Arrays.asList(is));
            }

            Set<Integer> temp = Collections.synchronizedSet(new HashSet<>());
            temp.addAll(t);

            this.setMember = Collections.unmodifiableSet(temp);
        }

        public Set<Integer> getSetMember() {
            return setMember;
        }

        public String getStringMember() {
            return stringMember;
        }

        @Override
        public synchronized String toString() {
            StringBuilder s = new StringBuilder();
            for (int i : this.setMember) {
                s.append("[");
                s.append(Integer.toHexString(i));
                s.append("]");
            }
            String set = s.toString();
            MessageFormat msg = new MessageFormat("{0}({1},serviceIds={2})");
            Object[] parameters = {super.toString(), this.stringMember, set};
            return msg.format(parameters);
        }

    }

    @Rule
    public ExpectedExceptionRule rule = new ExpectedExceptionRule();

    public ReverseLookUpMapFactoryTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> getDecf_OK() {
        final Function<SOME_ENUM_OK, Set<Integer>> func1;
        final ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> decf1;

        func1 = (SOME_ENUM_OK t) -> t.getSetMember();
        decf1 = new ReverseLookUpMapFactory<>(func1);
        return decf1;
    }

    private void putAllToDecf_OK(ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> decf) {
        decf.put(ReverseLookUpMapFactoryTest.SOME_ENUM_OK.A);
        decf.put(ReverseLookUpMapFactoryTest.SOME_ENUM_OK.B);
        decf.put(ReverseLookUpMapFactoryTest.SOME_ENUM_OK.C);
    }

    private ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_NG> getDecf_NG() {
        final Function<SOME_ENUM_NG, Set<Integer>> func1;
        final ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_NG> decf1;

        func1 = (SOME_ENUM_NG t) -> (Set<Integer>) t.getSetMember();
        decf1 = new ReverseLookUpMapFactory<>(func1);
        return decf1;
    }

    private void putAllToDecf_NG(ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_NG> decf) {
        decf.put(ReverseLookUpMapFactoryTest.SOME_ENUM_NG.A);
        decf.put(ReverseLookUpMapFactoryTest.SOME_ENUM_NG.B);
        decf.put(ReverseLookUpMapFactoryTest.SOME_ENUM_NG.C);
    }

    @Test
    @ExpectedExceptionMessage("^関数オブジェクトが指定されていません.*")
    public void testNG_NULL_FUNCTION() {
        final ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> decf1;
        decf1 = new ReverseLookUpMapFactory<>(null);

    }

    @Test
    @ExpectedExceptionMessage("^キーが重複しています.*")
    public void testNG_SYNONIM_KEYS() {
        LOG.debug("NG_SYNONIM_KEYS");
        this.putAllToDecf_NG(this.getDecf_NG());
    }

    /**
     * Test of size method, of class ReverseLookUpMapFactory.
     */
    @Test
    public void testSize() {
        LOG.debug("size");
        ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> instance = getDecf_OK();
        this.putAllToDecf_OK(instance);

        int expResult1 = 6;
        int result1 = instance.size();
        assertEquals(expResult1, result1);
    }

    /**
     * Test of isEmpty method, of class ReverseLookUpMapFactory.
     */
    @Test
    public void testIsEmpty() {
        LOG.debug("isEmpty");
        ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> instance = getDecf_OK();

        assertEquals(true, instance.isEmpty());

        this.putAllToDecf_OK(instance);

        assertEquals(false, instance.isEmpty());

    }

    /**
     * Test of containsKey method, of class ReverseLookUpMapFactory.
     */
    @Test
    public void testContainsKey() {
        LOG.debug("containsKey");
        int key;
        ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> instance = getDecf_OK();

        key = -1;
        assertEquals(false, instance.containsKey(key));

        this.putAllToDecf_OK(instance);

        key = 1;
        assertEquals(true, instance.containsKey(key));
    }

    /**
     * Test of containsValue method, of class ReverseLookUpMapFactory.
     */
    @Test
    public void testContainsValue() {
        LOG.debug("containsValue");

        ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> instance = getDecf_OK();

        SOME_ENUM_OK value;

        //ぬるぽ。
//        value = null;
        value = SOME_ENUM_OK.D;
        assertEquals(false, instance.containsValue(value));
        this.putAllToDecf_OK(instance);

        value = SOME_ENUM_OK.C;
        assertEquals(true, instance.containsValue(value));
    }


    /**
     * Test of getDict method, of class ReverseLookUpMapFactory.
     */
    @Test
    public void testGetDict() {
        LOG.debug("getDict");

        ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> instance = getDecf_OK();
        this.putAllToDecf_OK(instance);

        Map<Integer, SOME_ENUM_OK> expResult = new ConcurrentHashMap<>();
        expResult.put(0, SOME_ENUM_OK.A);
        expResult.put(1, SOME_ENUM_OK.B);
        expResult.put(2, SOME_ENUM_OK.B);
        expResult.put(3, SOME_ENUM_OK.C);
        expResult.put(4, SOME_ENUM_OK.C);
        expResult.put(5, SOME_ENUM_OK.C);

        Map result = instance.getDict();
        assertEquals(expResult, result);
    }

    /**
     * Test of put method, of class ReverseLookUpMapFactory.
     */
    @Test
    public void testPut() {
        LOG.debug("put");

        SOME_ENUM_OK e = SOME_ENUM_OK.A;
        ReverseLookUpMapFactory<Integer, Set<Integer>, SOME_ENUM_OK> instance = getDecf_OK();
        instance.put(e);

        Map<Integer, SOME_ENUM_OK> expResult = new ConcurrentHashMap<>();
        expResult.put(0, SOME_ENUM_OK.A);

        Map result = instance.getDict();
        assertEquals(expResult, result);
    }

}
