/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.sdt;

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
import libepg.epg.section.descriptor.DescriptorsLoop;
import libepg.epg.section.body.servicedescriptiontable.ServiceDescriptionTableRepeatingPart;
import libepg.util.LoggerFactory;
import testtool.EqualsChecker;

/**
 *
 * @author normal
 */
public class ServiceDescriptionTableRepeatingPartTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ServiceDescriptionTableRepeatingPart.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    
    private final String testData = "0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408";
    private final byte[] x;

    public ServiceDescriptionTableRepeatingPartTest() throws DecoderException {
        x = Hex.decodeHex(testData.toCharArray());

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
     * Test of getService_id method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetService_id() throws Throwable {
        LOG.debug("getService_id");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 0x0408;
        int result = instance.getService_id();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReserved_future_use1 method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetReserved_future_use1() throws Throwable {
        LOG.debug("getReserved_future_use1");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 7;
        int result = instance.getReserved_future_use1();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEIT_user_defined_flags method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetEIT_user_defined_flags() throws Throwable {
        LOG.debug("getEIT_user_defined_flags");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 4;
        int result = instance.getEIT_user_defined_flags();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEIT_schedule_flag method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetEIT_schedule_flag() throws Throwable {
        LOG.debug("getEIT_schedule_flag");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 1;
        int result = instance.getEIT_schedule_flag();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEIT_present_following_flag method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetEIT_present_following_flag() throws Throwable {
        LOG.debug("getEIT_present_following_flag");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 1;
        int result = instance.getEIT_present_following_flag();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRunning_status method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetRunning_status() throws Throwable {
        LOG.debug("getRunning_status");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 0;
        int result = instance.getRunning_status();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFree_CA_mode method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetFree_CA_mode() throws Throwable {
        LOG.debug("getFree_CA_mode");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 0;
        int result = instance.getFree_CA_mode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescriptors_loop_length method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetDescriptors_loop_length() throws Throwable {
        LOG.debug("getDescriptors_loop_length");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        int expResult = 32;
        int result = instance.getDescriptors_loop_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescriptors_loop method, of class
     * ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetDescriptors_loop() throws DecoderException, Throwable {
        LOG.debug("getDescriptors_loop");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        DescriptorsLoop expResult = new DescriptorsLoop(Hex.decodeHex("481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408".toCharArray()));
        DescriptorsLoop result = instance.getDescriptors_loop();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testToString() throws Throwable {
        LOG.debug("toString");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        LOG.debug(instance);
    }

    /**
     * Test of hashCode method, of class ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testHashCode() throws Throwable {
        LOG.debug("HashCode_same");
        ServiceDescriptionTableRepeatingPart instance1 = ClassGetter.init(this.x);
        ServiceDescriptionTableRepeatingPart instance2 = ClassGetter.init(this.x);
        assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testHashCode_NotSame() throws DecoderException, Throwable {
        LOG.debug("HashCode_Notsame");
        ServiceDescriptionTableRepeatingPart instance1 = ClassGetter.init(this.x);
        ServiceDescriptionTableRepeatingPart instance2 = ClassGetter.init(Hex.decodeHex("0409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08".toCharArray()));
        boolean xx = (instance1.hashCode() == instance2.hashCode());
        assertEquals(xx, false);
    }

    /**
     * Test of equals method, of class TsPacket.
     */
    @Test
    public void testEquals_same() throws Throwable {
        LOG.debug("equals_same");

        EqualsChecker<ServiceDescriptionTableRepeatingPart> ec = new EqualsChecker<>();
        ServiceDescriptionTableRepeatingPart obj = ClassGetter.init(this.x);
        ServiceDescriptionTableRepeatingPart obj2 = ClassGetter.init(this.x);
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        assertTrue(ec.check_same(instance, obj, obj2));
    }

    /**
     * Test of equals method, of class TsPacket.
     */
    @Test
    public void testEquals_Notsame() throws DecoderException, Throwable {
        LOG.debug("equals_Notsame");
        EqualsChecker<ServiceDescriptionTableRepeatingPart> ec = new EqualsChecker<>();
        ServiceDescriptionTableRepeatingPart obj = ClassGetter.init(this.x);
        ServiceDescriptionTableRepeatingPart obj2 = ClassGetter.init(this.x);
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(Hex.decodeHex("0409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08".toCharArray()));
        assertTrue(ec.check_not_same(instance, obj, obj2));
    }

    /**
     * Test of getData method, of class ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testGetData() throws Throwable {
        LOG.debug("getData");
        ServiceDescriptionTableRepeatingPart instance = ClassGetter.init(this.x);
        byte[] expResult = this.x;
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

}
