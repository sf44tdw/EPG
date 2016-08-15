/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import libepg.common.descriptor.Descriptors;
import static libepg.epg.section.descriptor.DESCRIPTOR_TAG.LOGO_TRANSMISSION_DESCRIPTOR;
import libepg.util.LoggerFactory;
import testtool.EqualsChecker;
import testtool.testrule.regexmessagerule.ExpectedExceptionMessage;
import testtool.testrule.regexmessagerule.ExpectedExceptionRule;

/**
 *
 * @author normal
 */
public class DescriptorTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Descriptors.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final Descriptors descs;

    public DescriptorTest() throws DecoderException {
        descs = new Descriptors();
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
    @ExpectedExceptionMessage("^渡されたデータの長さが想定と異なりま.*$")
    public void testConstructor_1() throws Throwable {
        try {
            LOG.debug("異常系_データ長相違");
            byte[] tail = {0x00, 0x00};
            byte[] wrongX1 = ArrayUtils.addAll(this.descs.getSERVICE_DESCRIPTOR_BYTE(), tail);
            Descriptor instance = Descriptors.init(wrongX1);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
    }

    //記述子タグ定数に、すべての値が登録されていない場合を除きありえなくなったので削除。
//    @Test
//    @ExpectedExceptionMessage("^記述子タグが異なります.*$")
//    public void testConstructor_2() throws Throwable {
//        try {
//            LOG.debug("異常系_記述子タグ相違");
//            Descriptor instance = Descriptors.init(this.descs.getSERVICE_DESCRIPTOR_BYTE());
//        } catch (InvocationTargetException ex) {
//            throw ex.getCause();
//        }
//    }

    /**
     * Test of getData method, of class Descriptor.
     */
    @Test
    public void testGetData() {
        LOG.debug("getData");
        Descriptor instance = this.descs.getSERVICE_DESCRIPTOR();
        byte[] expResult = this.descs.getSERVICE_DESCRIPTOR_BYTE();
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getDescriptor_tag method, of class Descriptor.
     */
    @Test
    public void testGetDescriptor_tag() {
        LOG.debug("getDescriptor_tag");
        Descriptor instance = this.descs.getSERVICE_DESCRIPTOR();
        int expResult = 0x48;
        int result = instance.getDescriptor_tag();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescriptor_length method, of class Descriptor.
     */
    @Test
    public void testGetDescriptor_length() {
        LOG.debug("getDescriptor_length");
        Descriptor instance = this.descs.getSERVICE_DESCRIPTOR();
        int expResult = 0x12;
        int result = instance.getDescriptor_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescriptor_Body method, of class Descriptor.
     */
    @Test
    public void testGetDescriptor_Body() throws DecoderException {
        LOG.debug("getDescriptor_Body");
        Descriptor instance = this.descs.getSERVICE_DESCRIPTOR();
        byte[] expResult = Hex.decodeHex("01000f0e4e484b451d461d6c310f456c357e".toCharArray());
        byte[] result = instance.getDescriptor_Body();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Descriptor.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        Descriptor instance = this.descs.getSERVICE_DESCRIPTOR();
        String result = instance.toString();
        LOG.debug(result);
    }

    /**
     * Test of hashCode method, of class Descriptor.
     */
    @Test
    public void testHashCode() {
        LOG.debug("hashCode");
        Descriptor instance1 = this.descs.getSERVICE_DESCRIPTOR();
        Descriptor instance2 = this.descs.getSERVICE_DESCRIPTOR();
        int expResult1 = instance2.hashCode();
        int result = instance1.hashCode();
        assertEquals(expResult1, result);
        int expResult2 = this.descs.getDIGITAL_COPY_CONTROL_DESCRIPTOR().hashCode();
        assertFalse(expResult2 == result);
    }

    /**
     * Test of equals method, of class Descriptor.
     */
    @Test
    public void testEquals() {
        LOG.debug("equals_ok");
        EqualsChecker<Descriptor> ec = new EqualsChecker<>();
        Descriptor instance1 = this.descs.getSERVICE_DESCRIPTOR();
        Descriptor instance2 = this.descs.getSERVICE_DESCRIPTOR();
        Descriptor instance3 = this.descs.getSERVICE_DESCRIPTOR();
        boolean result = ec.check_same(instance1, instance2, instance3);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Descriptor.
     */
    @Test
    public void testEquals_ng() {
        LOG.debug("equals_ng");
        Descriptor instance1 = this.descs.getSERVICE_DESCRIPTOR();
        Descriptor instance2 = this.descs.getDIGITAL_COPY_CONTROL_DESCRIPTOR();
        boolean result = instance1.equals(instance2);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of getpreferedDescriptorTag method, of class Descriptor.
     */
    @Test
    public void testGetDescriptor_tag_const() {
        LOG.debug("getDescriptor_tag_const");
        Descriptor instance = this.descs.getLOGO_TRANSMISSION_DESCRIPTOR();
        DESCRIPTOR_TAG expResult = LOGO_TRANSMISSION_DESCRIPTOR;
        DESCRIPTOR_TAG result = instance.getDescriptor_tag_const();
        assertEquals(expResult, result);
    }

}
