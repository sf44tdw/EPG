/*
 * Copyright (C) 2016 normal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package libepg.epg.section.eit.descriptor.extendedeventdescriptor;

import java.lang.invoke.MethodHandles;
import libepg.epg.section.descriptor.Descriptor;
import libepg.epg.section.descriptor.extendedeventdescriptor.ExtendedEventDescriptorItem;
import libepg.util.LoggerFactory;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca<br>
 * item_description_length=08=08<br>
 * item_description_char=4856414846624d46<br>
 * item_length=dc=220<br>
 * item_char=3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca<br>
 */
/**
 *
 * @author normal
 */
public class ExtendedEventDescriptorItemTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Descriptor.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final char[] data = "084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca".toCharArray();
    private final ExtendedEventDescriptorItem target;

    public ExtendedEventDescriptorItemTest() throws DecoderException {
        target = new ExtendedEventDescriptorItem(Hex.decodeHex(data));
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
     * Test of getData method, of class ExtendedEventDescriptorItem.
     */
    @Test
    public void testGetData() throws DecoderException {
        LOG.debug("getData");
        ExtendedEventDescriptorItem instance = target;
        byte[] expResult = Hex.decodeHex("084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca".toCharArray());
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getItem_description_length method, of class
     * ExtendedEventDescriptorItem.
     */
    @Test
    public void testGetItem_description_length() {
        LOG.debug("getItem_description_length");
        ExtendedEventDescriptorItem instance = target;
        int expResult = 8;
        int result = instance.getItem_description_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getiItem_description method, of class
     * ExtendedEventDescriptorItem.
     */
    @Test
    public void testGetiItem_description() throws DecoderException {
        LOG.debug("getiItem_description");
        ExtendedEventDescriptorItem instance = target;
        byte[] expResult = Hex.decodeHex("4856414846624d46".toCharArray());
        byte[] result = instance.getiItem_description();
        assertArrayEquals(expResult, result);
    }

//    /**
//     * Test of getiItem_description_String method, of class
//     * ExtendedEventDescriptorItem.
//     */
//    @Test
//    public void testGetiItem_description_String() {
//        LOG.debug("getiItem_description_String");
//        ExtendedEventDescriptorItem instance = null;
//        String expResult = "";
//        String result = instance.getiItem_description_String();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getItem_length method, of class ExtendedEventDescriptorItem.
     */
    @Test
    public void testGetItem_length() {
        LOG.debug("getItem_length");
        ExtendedEventDescriptorItem instance = target;
        int expResult = 220;
        int result = instance.getItem_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getItem method, of class ExtendedEventDescriptorItem.
     */
    @Test
    public void testGetItem() throws DecoderException {
        LOG.debug("getItem");
        ExtendedEventDescriptorItem instance = target;
        byte[] expResult = Hex.decodeHex("3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca".toCharArray());
        byte[] result = instance.getItem();
        assertArrayEquals(expResult, result);
    }

//    /**
//     * Test of getItem_String method, of class ExtendedEventDescriptorItem.
//     */
//    @Test
//    public void testGetItem_String() {
//        LOG.debug("getItem_String");
//        ExtendedEventDescriptorItem instance = null;
//        String expResult = "";
//        String result = instance.getItem_String();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    /**
//     * Test of hashCode method, of class ExtendedEventDescriptorItem.
//     */
//    @Test
//    public void testHashCode() {
//        LOG.debug("hashCode");
//        ExtendedEventDescriptorItem instance = null;
//        int expResult = 0;
//        int result = instance.hashCode();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class ExtendedEventDescriptorItem.
//     */
//    @Test
//    public void testEquals() {
//        LOG.debug("equals");
//        Object obj = null;
//        ExtendedEventDescriptorItem instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(obj);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of toString method, of class ExtendedEventDescriptorItem.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        ExtendedEventDescriptorItem instance = target;
        LOG.debug(instance.toString());
    }
}
