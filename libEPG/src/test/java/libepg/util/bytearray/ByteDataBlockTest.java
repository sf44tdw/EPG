/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.util.bytearray;

import util.bytearray.ByteDataBlock;
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
import libepg.common.packet.TestPacket_pid0x11_count_d_0_0;
import libepg.util.LoggerFactory;
import testtool.EqualsChecker;

/**
 *
 * @author normal
 */
public class ByteDataBlockTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ByteDataBlockTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final TestPacket_pid0x11_count_d_0_0 dat;

    public ByteDataBlockTest() throws DecoderException {
        dat = new TestPacket_pid0x11_count_d_0_0();
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
     * Test of toString method, of class ByteDataBlock.
     */
    @Test
    public void testToString() throws DecoderException {
        LOG.debug("toString");
        ByteDataBlock instance = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        String expResult = Hex.encodeHexString(instance.getData());
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class ByteDataBlock.
     */
    @Test
    public void testHashCode() throws DecoderException {
        LOG.debug("hashCode");
        ByteDataBlock instance1 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        ByteDataBlock instance2 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        assertTrue((instance1.hashCode() == instance2.hashCode()));

        ByteDataBlock instance5 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        ByteDataBlock instance6 = new ByteDataBlock(Hex.decodeHex(dat.p0e));
        assertTrue((instance5.hashCode() != instance6.hashCode()));

    }

    /**
     * Test of equals method, of class ByteDataBlock.
     */
    @Test
    public void testEquals() throws DecoderException {
        LOG.debug("equals");
        EqualsChecker<ByteDataBlock> ec = new EqualsChecker<>();

        ByteDataBlock instance1 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        ByteDataBlock instance2 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        ByteDataBlock instance3 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        boolean expResult = true;
        boolean result = ec.check(instance1, instance2, instance3);
        assertEquals(expResult, result);

        ByteDataBlock instance4 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        ByteDataBlock instance5 = new ByteDataBlock(Hex.decodeHex(dat.p0e));
        ByteDataBlock instance6 = new ByteDataBlock(Hex.decodeHex(dat.p0d));
        boolean expResult2 = false;
        boolean result2 = ec.check(instance4, instance5, instance6);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of getData method, of class ByteDataBlock.
     */
    @Test
    public void testGetData() {
        LOG.debug("getData");
        byte[] x = {0x01, 0x02, 0x03};
        ByteDataBlock instance = new ByteDataBlock(x);
        byte[] expResult = x;
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
        result[1] = 0x06;
        assertFalse(result.equals(instance.getData()));
    }

    /**
     * Test of length method, of class ByteDataBlock.
     */
    @Test
    public void testLength() {
        LOG.debug("length");
        byte[] x = {0x01, 0x02, 0x03};
        ByteDataBlock instance = new ByteDataBlock(x);
        int expResult = 3;
        int result = instance.length();
        assertEquals(expResult, result);
    }

}
