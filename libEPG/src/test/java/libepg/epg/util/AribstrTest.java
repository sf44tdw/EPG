/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.util;

import java.lang.invoke.MethodHandles;
import epgtools.loggerfactory.LoggerFactory;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author normal
 */
public class AribstrTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, AribstrTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    public AribstrTest() {
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

    /**
     * Test of AribToString method, of class Aribstr.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAribToString() throws Exception {
        LOG.debug("AribToString");
        byte[] pSrcData = Hex.decodeHex("0e4e484b451d461d6c310f456c357e".toCharArray());
        String expResult = "ＮＨＫＥテレ１東京";
        String result = Aribstr.AribToString(pSrcData);
        LOG.debug(result);
        assertEquals(expResult, result);
    }


    /**
     * Test of AribToString method, of class Aribstr.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAribToString_null() throws Exception {
        LOG.debug("AribToString_null");
        byte[] pSrcData = null;
        String expResult = "";
        String result = Aribstr.AribToString(pSrcData);
        LOG.debug(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of AribToString method, of class Aribstr.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAribToString_emptyArray() throws Exception {
        LOG.debug("AribToString_emptyArray");
        byte[] pSrcData = new byte[0];
        String expResult = "";
        String result = Aribstr.AribToString(pSrcData);
        LOG.debug(result);
        assertEquals(expResult, result);
    }
}
