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

import libepg.epg.section.descriptor.extendedeventdescriptor.ExtendedEventDescriptorItem;
import libepg.epg.section.descriptor.extendedeventdescriptor.ExtendedEventDescriptor;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import libepg.common.descriptor.Descriptors;
import libepg.epg.section.descriptor.DESCRIPTOR_TAG;
import libepg.epg.section.descriptor.Descriptor;
import libepg.util.LoggerFactory;
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

/**
 *
 * @author normal
 */
/**
 * descriptor_tag=4e<br>
 * descriptor_length=ec=236<br>
 * descriptor_number(0),last_descriptor_number(2)=02<br>
 * ISO_639_language_code=6a706e<br>
 * length_of_items=e6=230<br>
 * items=084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca<br>
 * text_length=00<br>
 */
public class ExtendedEventDescriptorTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Descriptor.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    private final Descriptors descs;
    private final ExtendedEventDescriptor target1;

    public ExtendedEventDescriptorTest() throws DecoderException {
        descs = new Descriptors();
        Descriptor src = this.descs.getEXTENDED_EVENT_DESCRIPTOR();
        LOG.debug(src);
        target1 = new ExtendedEventDescriptor(src);

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
     * Test of getDescriptor_number method, of class ExtendedEventDescriptor.
     */
    @Test
    public void testGetDescriptor_number() {
        LOG.debug("getDescriptor_number");
        ExtendedEventDescriptor instance = target1;
        int expResult = 0;
        int result = instance.getDescriptor_number();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLast_descriptor_number method, of class
     * ExtendedEventDescriptor.
     */
    @Test
    public void testGetLast_descriptor_number() {
        LOG.debug("getLast_descriptor_number");
        ExtendedEventDescriptor instance = target1;
        int expResult = 2;
        int result = instance.getLast_descriptor_number();
        assertEquals(expResult, result);
    }

    /**
     * Test of getISO_639_language_code method, of class
     * ExtendedEventDescriptor.
     */
    @Test
    public void testGetISO_639_language_code() {
        LOG.debug("getISO_639_language_code");
        ExtendedEventDescriptor instance = target1;
        String expResult = "jpn";
        String result = instance.getISO_639_language_code();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLength_of_items method, of class ExtendedEventDescriptor.
     */
    @Test
    public void testGetLength_of_items() {
        LOG.debug("getLength_of_items");
        ExtendedEventDescriptor instance = target1;
        int expResult = 230;
        int result = instance.getLength_of_items();
        assertEquals(expResult, result);
    }

    /**
     * Test of getItems method, of class ExtendedEventDescriptor.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testGetItems() throws DecoderException {
        LOG.debug("getItems");
        ExtendedEventDescriptor instance = target1;
        byte[] expResult = Hex.decodeHex("084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca".toCharArray());
        byte[] result = instance.getItems();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getText_length method, of class ExtendedEventDescriptor.
     */
    @Test
    public void testGetText_length() {
        LOG.debug("getText_length");
        ExtendedEventDescriptor instance = target1;
        int expResult = 0;
        int result = instance.getText_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText_char method, of class ExtendedEventDescriptor.
     */
    @Test
    public void testGetText_char1() {
        LOG.debug("getText_char1");
        ExtendedEventDescriptor instance = target1;
        byte[] expResult = new byte[0];
        byte[] result = instance.getText_char();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getText_char method, of class ExtendedEventDescriptor.
     * テキストありのデータを作成してテストする。
     *
     * @throws org.apache.commons.codec.DecoderException
     * @throws java.lang.reflect.InvocationTargetException
     */
    @Test
    public void testGetText_char2() throws DecoderException, InvocationTargetException {
        LOG.debug("getText_char2");

        byte[] dat = this.descs.getEXTENDED_EVENT_DESCRIPTOR_BYTE();
        byte[] dat2 = Hex.decodeHex("0e4e484b451d461d6c310f456c357e".toCharArray());
        //テキストフィールドを追加
        byte[] dat3 = ArrayUtils.addAll(dat, dat2);

        LOG.debug(Hex.encodeHexString(dat));
        LOG.debug(Hex.encodeHexString(dat2));
        LOG.debug(Hex.encodeHexString(dat3));

        //記述子長を改ざん
        dat3[1] = (byte) (dat.length - 1 + dat2.length - 1);
        //テキストフィールド長を改ざん
        dat3[dat.length - 1] = (byte) dat2.length;
        LOG.debug(Hex.encodeHexString(dat3));

        Descriptor dummy = Descriptors.init(dat3);
        ExtendedEventDescriptor instance = new ExtendedEventDescriptor(dummy);

        byte[] expResult = dat2;
        byte[] result = instance.getText_char();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of toString method, of class ExtendedEventDescriptor.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        ExtendedEventDescriptor instance = target1;
        LOG.debug(instance.toString());
    }

    /**
     * Test of getItemsList method, of class ExtendedEventDescriptor.
     */
    @Test
    public void testGetItemsList() throws DecoderException {
        System.out.println("getItemsList");
        ExtendedEventDescriptor instance = target1;
        List<ExtendedEventDescriptorItem> expResult = new ArrayList<>();
        expResult.add(new ExtendedEventDescriptorItem(Hex.decodeHex("084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca".toCharArray())));
        List<ExtendedEventDescriptorItem> result = instance.getItemsList();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText_char method, of class ExtendedEventDescriptor.
     */
    @Test
    public void testGetText_char() {
        System.out.println("getText_char");
        ExtendedEventDescriptor instance = target1;
        byte[] expResult = {};
        byte[] result = instance.getText_char();
        assertArrayEquals(expResult, result);
    }

}
