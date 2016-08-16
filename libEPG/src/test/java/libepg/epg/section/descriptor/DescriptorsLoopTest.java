/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor;


import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import libepg.common.descriptor.Descriptors;
import libepg.epg.section.descriptor.servicedescriptor.ServiceDescriptor;
import epgtools.loggerfactory.LoggerFactory;
import testtool.EqualsChecker;

/**
 *
 * @author normal
 */
public class DescriptorsLoopTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, DescriptorsLoop.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final DescriptorsLoop target;
    private final DescriptorsLoop target2;
    private final Descriptors descs;
    private final List<Descriptor> descsList;

    public DescriptorsLoopTest() throws DecoderException {
        target = new DescriptorsLoop(Hex.decodeHex("481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408".toCharArray()));
        target2 = new DescriptorsLoop(Hex.decodeHex("481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08".toCharArray()));
        descs = new Descriptors();
        descsList = new ArrayList<>();
        descsList.add(new ServiceDescriptor(descs.getSERVICE_DESCRIPTOR()));
        descsList.add(descs.getDIGITAL_COPY_CONTROL_DESCRIPTOR());
        descsList.add(descs.getLOGO_TRANSMISSION_DESCRIPTOR());
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
     * Test of getData method, of class DescriptorsLoop.
     */
    @Test
    public void testGetData() throws DecoderException {
        LOG.debug("getData");
        DescriptorsLoop instance = target;
        byte[] expResult = Hex.decodeHex("481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408".toCharArray());
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getDescriptors_loopList method, of class DescriptorsLoop.
     */
    @Test
    public void testGetDescriptors_loopList() {
        LOG.debug("getDescriptors_loopList");
        DescriptorsLoop instance = target;

        List<Descriptor> expResult = descsList;
        List<Descriptor> result = instance.getDescriptors_loopList();

        LOG.debug(instance);

        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class DescriptorsLoop.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        DescriptorsLoop instance = target;
        LOG.debug(instance);
    }

    /**
     * Test of hashCode method, of class ServiceDescriptionTableRepeatingPart.
     */
    @Test
    public void testHashCode() {
        LOG.debug("HashCode_same");
        DescriptorsLoop instance1 = target;
        DescriptorsLoop instance2 = target;
        assertEquals(instance1.hashCode(), instance2.hashCode());
    }

    @Test
    public void testHashCode_NotSame() throws DecoderException {
        LOG.debug("HashCode_Notsame");
        DescriptorsLoop instance1 = target;
        DescriptorsLoop instance2 = target2;
        boolean xx = (instance1.hashCode() == instance2.hashCode());
        assertEquals(xx, false);
    }

    /**
     * Test of equals method, of class TsPacket.
     */
    @Test
    public void testEquals_same() {
        LOG.debug("equals_same");
        EqualsChecker<DescriptorsLoop> ec = new EqualsChecker<>();
        DescriptorsLoop obj = target;
        DescriptorsLoop obj2 = target;
        DescriptorsLoop instance = target;
        assertTrue(ec.check_same(instance, obj, obj2));
    }

    /**
     * Test of equals method, of class TsPacket.
     */
    @Test
    public void testEquals_Notsame() throws DecoderException {
        LOG.debug("equals_Notsame");
        EqualsChecker<DescriptorsLoop> ec = new EqualsChecker<>();
        DescriptorsLoop obj = target;
        DescriptorsLoop obj2 = target;
        DescriptorsLoop instance = target2;
        assertTrue(ec.check_not_same(instance, obj, obj2));
    }

//    /**
//     * Test of equals method, of class DescriptorsLoop.
//     */
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        Object obj = null;
//        DescriptorsLoop instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(obj);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
