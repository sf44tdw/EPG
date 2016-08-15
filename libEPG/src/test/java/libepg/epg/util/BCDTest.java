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
package libepg.epg.util;

import java.lang.invoke.MethodHandles;
import libepg.epg.util.datetime.BCD;
import libepg.util.LoggerFactory;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import testtool.EqualsChecker;
import testtool.testrule.regexmessagerule.ExpectedExceptionMessage;
import testtool.testrule.regexmessagerule.ExpectedExceptionRule;

/**
 *
 * @author normal
 */
public class BCDTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, BCDTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    
    private final byte bcds1 = 0x12;
    private final BCD target1 = new BCD(bcds1);

    private final byte bcds2 = 0x01;
    private final BCD target2 = new BCD(bcds2);

    public BCDTest() {
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

    @Test
    @ExpectedExceptionMessage("^このオブジェクトで取り扱える範囲外の数値です.*$")
    public void testConstructor_1() {
        LOG.debug("異常系_BCD範囲外1");
        byte wrong = 0x0a;
        BCD instance = new BCD(wrong);
    }

    @Test
    @ExpectedExceptionMessage("^このオブジェクトで取り扱える範囲外の数値です.*$")
    public void testConstructor_2() {
        LOG.debug("異常系_BCD範囲外2");
        byte wrong = (byte) 0xa0;
        BCD instance = new BCD(wrong);
    }

    /**
     * Test of getHigh method, of class BCD.
     */
    @Test
    public void testGetHigh() {
        LOG.debug("getHigh");
        BCD instance = target1;
        int expResult = 1;
        int result = instance.getHigh();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLow method, of class BCD.
     */
    @Test
    public void testGetLow() {
        LOG.debug("getLow");
        BCD instance = target1;
        int expResult = 2;
        int result = instance.getLow();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDecimal method, of class BCD.
     */
    @Test
    public void testGetDecimal() {
        LOG.debug("getDecimal");
        BCD instance = target1;
        int expResult = 12;
        int result = instance.getDecimal();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class BCD.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        BCD instance = target1;
        LOG.debug(instance);
    }

    /**
     * Test of hashCode method, of class BCD.
     */
    @Test
    public void testHashCode() {
        LOG.debug("hashCode");
        BCD instance1 = this.target1;
        BCD instance2 = this.target2;
        assertTrue(instance1.hashCode() == new BCD(this.bcds1).hashCode());
        assertFalse(instance1.hashCode() == instance2.hashCode());
    }

    /**
     * Test of equals method, of class BCD.
     */
    @Test
    public void testEquals_OK() {
        LOG.debug("equals_ok");
        EqualsChecker<BCD> ec = new EqualsChecker<>();
        BCD instance1 = this.target1;
        BCD instance2 = this.target1;
        BCD instance3 = this.target1;
        assertEquals(true, ec.check_same(instance1, instance2, instance3));
    }

    /**
     * Test of equals method, of class BCD.
     */
    @Test
    public void testEquals_NG() {
        LOG.debug("equals_ng");
        EqualsChecker<BCD> ec = new EqualsChecker<>();
        BCD instance1 = this.target1;
        BCD instance2 = this.target2;
        BCD instance3 = this.target1;
        assertEquals(true, ec.check_not_same(instance1, instance2, instance3));

    }
}
