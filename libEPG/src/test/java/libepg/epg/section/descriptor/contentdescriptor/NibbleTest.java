/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor.contentdescriptor;

import java.lang.invoke.MethodHandles;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import libepg.util.LoggerFactory;
import testtool.EqualsChecker;

/**
 *
 * @author normal
 */
public class NibbleTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, NibbleTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    private byte[] data = {0x11, 0x11};

    public NibbleTest() {

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
//強制呼び出し

    /**
     * Test of getData method, of class Nibble.
     *
     * @throws java.lang.Throwable
     */
    @Test
    public void testGetData() throws Throwable {
        LOG.debug("getData");
        Nibble instance = NIbbleMaker.init(this.data);
        byte[] expResult = this.data;
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getContent_nibble_level_1 method, of class Nibble.
     *
     * @throws java.lang.Throwable
     */
    @Test
    public void testGetContent_nibble_level_1() throws Throwable {
        LOG.debug("getContent_nibble_level_1");
        Nibble instance = NIbbleMaker.init(this.data);
        NIBBLE_LEVEL_1 expResult = NIBBLE_LEVEL_1.SPORTS;
        NIBBLE_LEVEL_1 result = instance.getContent_nibble_level_1();
        assertEquals(expResult, result);
    }

    /**
     * Test of getContent_nibble_level_2 method, of class Nibble.
     *
     * @throws java.lang.Throwable
     */
    @Test
    public void testGetContent_nibble_level_2() throws Throwable {
        LOG.debug("getContent_nibble_level_2");
        Nibble instance = NIbbleMaker.init(this.data);
        NIBBLE_LEVEL_2 expResult = NIBBLE_LEVEL_2.BASEBALL;
        NIBBLE_LEVEL_2 result = instance.getContent_nibble_level_2();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser_nibble1 method, of class Nibble.
     *
     * @throws java.lang.Throwable
     */
    @Test
    public void testGetUser_nibble1() throws Throwable {
        LOG.debug("getUser_nibble1");
        Nibble instance = NIbbleMaker.init(this.data);
        int expResult = 0x1;
        int result = instance.getUser_nibble1();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser_nibble2 method, of class Nibble.
     *
     * @throws java.lang.Throwable
     */
    @Test
    public void testGetUser_nibble2() throws Throwable {
        LOG.debug("getUser_nibble2");
        Nibble instance = NIbbleMaker.init(this.data);
        int expResult = 0x1;
        int result = instance.getUser_nibble1();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Nibble.
     */
    @Test
    public void testHashCode() throws Throwable {
        LOG.debug("hashCode");
        Nibble instance = NIbbleMaker.init(this.data);
        int expResult = 2019;
        int result = instance.hashCode();
        assertEquals(expResult, result);
    }
//
//    /**
//     * Test of equals method, of class Nibble.
//     */
//    @Test
//    public void testEquals() {
//        LOG.debug("equals");
//        Object obj = null;
//        Nibble instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(obj);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//

    /**
     * Test of equals method, of class Descriptor.
     */
    @Test
    public void testEquals() throws Throwable {
        LOG.debug("equals_ok");
        EqualsChecker<Nibble> ec = new EqualsChecker<>();

        Nibble instance1 = NIbbleMaker.init(Hex.decodeHex("82ff".toCharArray()));
        Nibble instance2 = NIbbleMaker.init(Hex.decodeHex("82ff".toCharArray()));
        Nibble instance3 = NIbbleMaker.init(Hex.decodeHex("82ff".toCharArray()));

        boolean result = ec.check(instance1, instance2, instance3);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Descriptor.
     */
    @Test
    public void testEquals_ng() throws DecoderException, Throwable {
        LOG.debug("equals_ng");
        Nibble instance1 = NIbbleMaker.init(Hex.decodeHex("82ff".toCharArray()));
        Nibble instance2 = NIbbleMaker.init(Hex.decodeHex("81ff".toCharArray()));
        boolean result = instance1.equals(instance2);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Nibble.
     *
     * @throws java.lang.Throwable
     */
    @Test
    public void testToString() throws Throwable {
        LOG.debug("toString");
        Nibble instance = NIbbleMaker.init(this.data);
        LOG.debug(instance);
    }
}
