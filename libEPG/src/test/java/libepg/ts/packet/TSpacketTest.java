/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.ts.packet;

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
import libepg.common.packet.TestPacket_SDT;
import libepg.common.packet.TestPacket_pid0x11_count_d_0_0;
import libepg.common.packet.TsPacket_error_indicator;
import libepg.util.LoggerFactory;
import testtool.EqualsChecker;

/**
 *
 * @author normal
 */
public class TSpacketTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, TsPacket.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    
    TestPacket_pid0x11_count_d_0_0 pd;
    private final TsPacket target00;
    private final TsPacket target01;
    private final TsPacket target02;

    public TSpacketTest() throws DecoderException {
        pd = new TestPacket_pid0x11_count_d_0_0();
        target00 = pd.target_p10;
        target01 = pd.target_p11;
        target02 = pd.target_p12;
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
     * Test of getSync_byte method, of class TsPacket.
     */
    @Test
    public void testcheckHeader() throws DecoderException {
        LOG.debug("checkHeader");
        TsPacket instance;
        boolean expResult;
        boolean result;

        instance = this.target00;
        expResult = true;
        result = instance.checkHeader();
        assertEquals(expResult, result);

        instance = new TsPacket_error_indicator().target_error01;
        expResult = true;
        result = instance.checkHeader();
        assertEquals(expResult, result);

    }

    /**
     * Test of getSync_byte method, of class TsPacket.
     */
    @Test
    public void testGetSync_byte() {
        LOG.debug("getSync_byte");
        TsPacket instance = this.target00;
        int expResult = 0x47;
        int result = instance.getSync_byte();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransport_error_indicator method, of class TsPacket.
     */
    @Test
    public void testGetTransport_error_indicator() {
        LOG.debug("getTransport_error_indicator");
        TsPacket instance = this.target00;
        int expResult = 0;
        int result = instance.getTransport_error_indicator();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPayload_unit_start_indicator method, of class TsPacket.
     */
    @Test
    public void testGetPayload_unit_start_indicator() {
        LOG.debug("getPayload_unit_start_indicator");
        TsPacket instance = this.target00;
        TsPacket.PAYLOAD_UNIT_START_INDICATOR expResult = TsPacket.PAYLOAD_UNIT_START_INDICATOR.START_PES_OR_START_SECTION;
        TsPacket.PAYLOAD_UNIT_START_INDICATOR result = instance.getPayload_unit_start_indicator();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransport_priority method, of class TsPacket.
     */
    @Test
    public void testGetTransport_priority() {
        LOG.debug("getTransport_priority");
        TsPacket instance = this.target00;
        int expResult = 0;
        int result = instance.getTransport_priority();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPid method, of class TsPacket.
     */
    @Test
    public void testGetPid() {
        LOG.debug("getPid");
        TsPacket instance = this.target00;
        int expResult = 0x11;
        int result = instance.getPid();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransport_scrambling_control method, of class TsPacket.
     */
    @Test
    public void testGetTransport_scrambling_control() {
        LOG.debug("getTransport_scrambling_control");
        TsPacket instance = this.target00;
        int expResult = 0;
        int result = instance.getTransport_scrambling_control().getValue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdaptation_field_control method, of class TsPacket.
     */
    @Test
    public void testGetAdaptation_field_control() {
        LOG.debug("getAdaptation_field_control");
        TsPacket instance = this.target00;
        TsPacket.ADAPTATION_FIELD_CONTROL expResult = TsPacket.ADAPTATION_FIELD_CONTROL.ONLY_PAYLOAD;
        TsPacket.ADAPTATION_FIELD_CONTROL result = instance.getAdaptation_field_control();
        assertEquals(expResult, result);
    }

    /**
     * Test of getContinuity_counter method, of class TsPacket.
     */
    @Test
    public void testGetContinuity_counter() {
        LOG.debug("getContinuity_counter");
        TsPacket instance = this.target00;
        int expResult = 0;
        int result = instance.getContinuity_counter();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdaptation_field method, of class TsPacket.
     */
    @Test
    public void testGetAdaptation_field() {
        LOG.debug("getAdaptation_field");
        TsPacket instance = this.target00;
        byte[] expResult = {0};
        byte[] result = instance.getAdaptation_field();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getPayload method, of class TsPacket.
     */
    @Test
    public void testGetPayload() throws DecoderException {
        LOG.debug("getPayload");
        TsPacket instance = this.target00;
        String paStr = "0042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
        byte[] expResult = Hex.decodeHex(paStr.toCharArray());
        byte[] result = instance.getPayload();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of equals method, of class TsPacket.
     */
    @Test
    public void testEquals_same() throws DecoderException {
        LOG.debug("equals_same");

        EqualsChecker<TsPacket> ec = new EqualsChecker<>();

        TsPacket obj = new TsPacket(Hex.decodeHex(this.pd.p10));
        TsPacket obj2 = new TsPacket(Hex.decodeHex(this.pd.p10));
        TsPacket instance = new TsPacket(Hex.decodeHex(this.pd.p10));
        boolean ret = ec.check(instance, obj, obj2);
//        System.out.println("****************************************************" + ret);
//        System.out.println("****************************************************" + obj.hashCode());
//        System.out.println("****************************************************" + obj2.hashCode());
//        System.out.println("****************************************************" + instance.hashCode());
        assertTrue(ec.check(instance, obj, obj2));
    }

    /**
     * Test of equals method, of class TsPacket.
     */
    @Test
    public void testEquals_Notsame() throws DecoderException {
        LOG.debug("equals_Notsame");

        EqualsChecker<TsPacket> ec = new EqualsChecker<>();

        TsPacket obj = new TsPacket(Hex.decodeHex(this.pd.p10));
        TsPacket obj2 = new TsPacket(Hex.decodeHex(this.pd.p11));
        TsPacket instance = this.target00;
        assertFalse(ec.check(instance, obj, obj2));
    }

    @Test
    public void testHashCode() throws DecoderException {
        LOG.debug("HashCode_same");
        TsPacket obj = new TsPacket(Hex.decodeHex(this.pd.p10));
        TsPacket obj2 = new TsPacket(Hex.decodeHex(this.pd.p10));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    public void testHashCode_NotSame() throws DecoderException {
        LOG.debug("HashCode_Notsame");
        TsPacket obj = new TsPacket(Hex.decodeHex(this.pd.p10));
        TsPacket obj2 = new TsPacket(Hex.decodeHex(this.pd.p11));
        assertNotEquals(obj.hashCode(), obj2.hashCode());

    }

    @Test
    public void testToString() throws DecoderException {
        LOG.debug("toString");
        TsPacket instance = new TestPacket_SDT().getTarget100();
        LOG.debug(instance);
    }

}
