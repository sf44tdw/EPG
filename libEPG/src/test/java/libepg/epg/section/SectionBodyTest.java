/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
public class SectionBodyTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, SectionBodyTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    private final byte[] data1;
    private final byte[] data2;

    private final SectionBody testBody1;
    private final SectionBody testBody2;

    public SectionBodyTest() throws DecoderException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        data1 = Hex.decodeHex("7fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray());
        data2 = Hex.decodeHex("7fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47".toCharArray());

        //強制呼び出し
        this.testBody1 = SectionBodyTest.init(new Object[]{TABLE_ID.SDT, data1});
        this.testBody2 = SectionBodyTest.init(new Object[]{TABLE_ID.SDT, data2});
    }

    private static final SectionBody init(Object[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SectionBody target;
        Class<?>[] params = {TABLE_ID.class, byte[].class};
        Constructor<SectionBody> constructor = SectionBody.class.getDeclaredConstructor(params);
        constructor.setAccessible(true);
        target = constructor.newInstance(args);
        return target;
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

//    @Test
//    public void xx() {
//        System.out.println("****");
////        Constructor<?>[] x = SectionBody.class.getDeclaredConstructors();
//        System.out.println(this.testBody1);
//        System.out.println(this.testBody2);
//        System.out.println("****");
//    }
    /**
     * Test of getTableID method, of class SectionBody.
     */
    @Test
    public void testGetTableID() {
        LOG.debug("getTableID");
        SectionBody instance = this.testBody1;
        TABLE_ID expResult = TABLE_ID.SDT;
        TABLE_ID result = instance.getTableID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class SectionBody.
     */
    @Test
    public void testGetData() {
        LOG.debug("getSectionBody");
        SectionBody instance = this.testBody1;
        byte[] expResult = this.data1;
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class SectionBody.
     *
     * @throws java.lang.NoSuchMethodException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @Test
    public void testHashCode() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        LOG.debug("hashCode");
        SectionBody instance1 = this.testBody1;
        SectionBody instance2 = this.testBody2;
        assertTrue(instance1.hashCode() == SectionBodyTest.init(new Object[]{TABLE_ID.SDT, data1}).hashCode());
        assertFalse(instance1.hashCode() == instance2.hashCode());
    }

    /**
     * Test of equals method, of class SectionBody.
     */
    @Test
    public void testEquals_Ok() {
        LOG.debug("equals_ok");
        EqualsChecker<SectionBody> ec = new EqualsChecker<>();
        SectionBody instance1 = this.testBody1;
        SectionBody instance2 = this.testBody1;
        SectionBody instance3 = this.testBody1;
        boolean result = ec.check(instance1, instance1, instance1);
        boolean expResult = true;
        assertEquals(expResult, result);

    }

    /**
     * Test of equals method, of class SectionBody.
     */
    @Test
    public void testEquals_Ng() {
        LOG.debug("equals_ng");
        SectionBody instance1 = this.testBody1;
        SectionBody instance2 = this.testBody2;
        assertFalse(instance1.equals(instance2));
        assertFalse(instance2.equals(instance1));
    }

    /**
     * Test of toString method, of class SectionBody.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        SectionBody instance = this.testBody1;
        LOG.debug(instance);
    }
}
