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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import testtool.testrule.regexmessagerule.ExpectedExceptionMessage;
import testtool.testrule.regexmessagerule.ExpectedExceptionRule;

/**
 *
 * @author normal
 */
public class EqualsCheckerTest {

    private class Class1 {

        private final int param1;

        public Class1(int param1) {
            this.param1 = param1;
        }

        public int getParam1() {
            return param1;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 83 * hash + this.param1;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Class1 other = (Class1) obj;
            if (this.param1 != other.param1) {
                return false;
            }
            return true;
        }

    }

    private class Class2 extends Class1 {

        private final int param2;

        public Class2(int param2, int param1) {
            super(param1);
            this.param2 = param2;
        }

        public int getParam2() {
            return param2;
        }

    }

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    public EqualsCheckerTest() {
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

    @Rule
    public ExpectedExceptionRule rule = new ExpectedExceptionRule();

    /**
     * Test of check method, of class EqualsChecker.
     */
    @Test
    public void testCheck0() {
        LOG.info("");
        String target1 = "a";
        String target2 = "a";
        String target3 = "a";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean result1 = instance.check_same(target1, target2, target3);
        assertEquals(true, result1);
        boolean result2 = instance.check_not_same(target1, target2, target3);
        assertEquals(false, result2);
    }

    /**
     * Test of check method, of class EqualsChecker.
     */
    @Test
    public void testCheck1() {
        LOG.info("");
        String target1 = "a";
        String target2 = "b";
        String target3 = "c";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean result1 = instance.check_same(target1, target2, target3);
        assertEquals(false, result1);
        boolean result2 = instance.check_not_same(target1, target2, target3);
        assertEquals(true, result2);
    }

    /**
     * Test of check method, of class EqualsChecker.
     */
    @Test
    public void testCheck2() {
        LOG.info("");
        String target1 = "a";
        String target2 = "a";
        String target3 = "c";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean result1 = instance.check_same(target1, target2, target3);
        assertEquals(false, result1);
        boolean result2 = instance.check_not_same(target1, target2, target3);
        assertEquals(true, result2);

    }

    @Test
    @ExpectedExceptionMessage("^.*nullのセットは禁止.*$")
    public void testCheck3() {
        LOG.info("");
        String target1 = "a";
        String target2 = null;
        String target3 = "c";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean expResult = true;
        boolean result = instance.check_same(target1, target2, target3);
    }

    /**
     * Test of check method, of class EqualsChecker. 実装の正しくないequalsを渡している上に、サブクラスが混じっている。
     */
    @Test
    @ExpectedExceptionMessage("^.*xとyは同じクラスではありません.*$")
    public void testCheck4() {
        LOG.info("");
        Class1 target1 = new Class1(0);
        Class1 target2 = new Class2(1, 1);
        Class1 target3 = new Class1(0);
        assertEquals(target1, target3);
        assertTrue(!(target1.equals(target2)));
        EqualsChecker<Class1> instance = new EqualsChecker<>();
        boolean result1 = instance.check_same(target1, target2, target3);
        assertEquals(false, result1);
        boolean result2 = instance.check_not_same(target1, target2, target3);
        assertEquals(false, result2);
    }


}
