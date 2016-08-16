/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.sdt.descriptor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import libepg.common.descriptor.Descriptors;
import libepg.epg.section.descriptor.DESCRIPTOR_TAG;
import libepg.epg.section.descriptor.Descriptor;
import libepg.epg.section.descriptor.servicedescriptor.ServiceDescriptor;
import epgtools.loggerfactory.LoggerFactory;
import org.junit.BeforeClass;

/**
 *
 * @author normal
 */
public class ServiceDescriptorTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ServiceDescriptorTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    //サービス記述子
    private final byte[] x1;
    private final Descriptor sdesc;

    public ServiceDescriptorTest() throws DecoderException, Throwable {
        try {
            x1 = new Descriptors().getSERVICE_DESCRIPTOR_BYTE();
            sdesc = Descriptors.init(x1);
        } catch (InvocationTargetException ex) {
            throw ex.getCause();
        }
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
     * Test of getService_type method, of class ServiceDescriptor.
     */
    @Test
    public void testGetService_type() {
        LOG.debug("getService_type");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);
        int expResult = 0x01;
        int result = instance.getService_type();
        assertEquals(expResult, result);
    }

    /**
     * Test of getService_provider_name_length method, of class
     * ServiceDescriptor.
     */
    @Test
    public void testGetService_provider_name_length() {
        LOG.debug("getService_provider_name_length");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);
        int expResult = 0;
        int result = instance.getService_provider_name_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getService_provider_name method, of class ServiceDescriptor.
     */
    @Test
    public void testGetService_provider_name() {
        LOG.debug("getService_provider_name");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);
        byte[] expResult = {};
        byte[] result = instance.getService_provider_name();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getService_name_length method, of class ServiceDescriptor.
     */
    @Test
    public void testGetSservice_name_length() {
        LOG.debug("getSservice_name_length");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);
        int expResult = 0x0f;
        int result = instance.getService_name_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getService_name method, of class ServiceDescriptor.
     */
    @Test
    public void testGetService_name() throws DecoderException {
        LOG.debug("getService_name");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);
        byte[] expResult = Hex.decodeHex("0e4e484b451d461d6c310f456c357e".toCharArray());
        byte[] result = instance.getService_name();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of toString method, of class ServiceDescriptor.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);
        LOG.debug(instance.toString());
    }

    /**
     * Test of getService_provider_name_String method, of class
     * ServiceDescriptor.
     */
    @Test
    public void testGetService_provider_name_String() {
        LOG.debug("getService_provider_name_String");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);;
        String expResult = "";
        String result = instance.getService_provider_name_String();
        assertEquals(expResult, result);

    }

    /**
     * Test of getService_name_String method, of class ServiceDescriptor.
     */
    @Test
    public void testGetService_name_String() {
        LOG.debug("getService_name_String");
        ServiceDescriptor instance = new ServiceDescriptor(sdesc);;
        String expResult = "ＮＨＫＥテレ１東京";
        String result = instance.getService_name_String();
        assertEquals(expResult, result);

    }

}
