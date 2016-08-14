/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor;

import java.lang.invoke.MethodHandles;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static libepg.epg.section.descriptor.DESCRIPTOR_TAG.CONTENT_DESCRIPTOR;
import libepg.epg.section.descriptor.contentdescriptor.ContentDescriptor;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
public class DESCRIPTOR_TAGTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, DESCRIPTOR_TAGTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    public DESCRIPTOR_TAGTest() {
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
     * Test of values method, of class DESCRIPTOR_TAG.
     */
    @Test
    public void testValues() {
        LOG.debug("values");
        DESCRIPTOR_TAG[] expResult = {
            DESCRIPTOR_TAG.SERVICE_DESCRIPTOR,
            DESCRIPTOR_TAG.SHORT_EVENT_DESCRIPTOR,
            DESCRIPTOR_TAG.EXTENDED_EVENT_DESCRIPTOR,
            DESCRIPTOR_TAG.CONTENT_DESCRIPTOR,
            DESCRIPTOR_TAG.SERIES_DESCRIPTOR,
            DESCRIPTOR_TAG.DIGITAL_COPY_CONTROL_DESCRIPTOR,
            DESCRIPTOR_TAG.LOGO_TRANSMISSION_DESCRIPTOR,
            DESCRIPTOR_TAG.OTHER_DESCRIPTOR_TAGS};
        DESCRIPTOR_TAG[] result = DESCRIPTOR_TAG.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class DESCRIPTOR_TAG.
     */
    @Test
    public void testValueOf() {
        LOG.debug("valueOf");
        String name = "SERVICE_DESCRIPTOR";
        DESCRIPTOR_TAG expResult = DESCRIPTOR_TAG.SERVICE_DESCRIPTOR;
        DESCRIPTOR_TAG result = DESCRIPTOR_TAG.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of reverseLookUp method, of class DESCRIPTOR_TAG.
     */
    @Test
    public void testReverseLookUp() {
        LOG.debug("reverseLookUp");
        int tag1 = 0x48;
        DESCRIPTOR_TAG expResult1 = DESCRIPTOR_TAG.SERVICE_DESCRIPTOR;
        DESCRIPTOR_TAG result1 = DESCRIPTOR_TAG.reverseLookUp(tag1);
        assertEquals(expResult1, result1);
        int tag2 = 0xc1;
        DESCRIPTOR_TAG expResult2 = DESCRIPTOR_TAG.DIGITAL_COPY_CONTROL_DESCRIPTOR;
        DESCRIPTOR_TAG result2 = DESCRIPTOR_TAG.reverseLookUp(tag2);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of contains method, of class DESCRIPTOR_TAG.
     */
    @Test
    public void testContains() {
        LOG.debug("contains");
        int tag1 = 0x48;
        DESCRIPTOR_TAG instance1 = DESCRIPTOR_TAG.SERVICE_DESCRIPTOR;
        boolean expResult1 = true;
        boolean result1 = instance1.contains(tag1);
        assertEquals(expResult1, result1);

        int tag2 = 0xc5;
        DESCRIPTOR_TAG instance2 = DESCRIPTOR_TAG.OTHER_DESCRIPTOR_TAGS;
        boolean expResult2 = true;
        boolean result2 = instance2.contains(tag2);
        assertEquals(expResult2, result2);

    }

    /**
     * Test of toString method, of class DESCRIPTOR_TAG.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        DESCRIPTOR_TAG instance = DESCRIPTOR_TAG.OTHER_DESCRIPTOR_TAGS;
        LOG.debug(instance.toString());
    }

    /**
     * Test of getDataType method, of class DESCRIPTOR_TAG.
     */
    @Test
    public void testGetDataType() {
        System.out.println("getDataType");
        DESCRIPTOR_TAG instance = CONTENT_DESCRIPTOR;
        Class<?> expResult = ContentDescriptor.class;
        Class<?> result = instance.getDataType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTagName method, of class DESCRIPTOR_TAG.
     */
    @Test
    public void testGetTagName() {
        System.out.println("getTagName");
        DESCRIPTOR_TAG instance = CONTENT_DESCRIPTOR;
        String expResult = "コンテント記述子";
        String result = instance.getTagName();
        assertEquals(expResult, result);

    }

}
