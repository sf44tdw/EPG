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
package libepg.epg.section.descriptor.shorteventdescriptor;

import java.lang.invoke.MethodHandles;
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
import libepg.common.descriptor.Descriptors;
import libepg.epg.section.descriptor.DESCRIPTOR_TAG;
import libepg.epg.section.descriptor.Descriptor;
import libepg.util.LoggerFactory;

/**
 * 4dbb6a706e3d1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea791b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea1<br>
 * 記述子タグ=SHORT_EVENT_DESCRIPTOR(TAGs=[4d])<br>
 * 記述子長=187<br>
 * ISO_639_language_code(言語コード)(24bit)=6a706e(jpn)<br>
 * event_name_length(番組名長)(8bit)=3d(61byte)<br>
 * event_name_char(番組名)=1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea<br>
 * text_length(番組記述長)(8bit)=79(121byte)<br>
 * text_char(番組記述)=1b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea1<br>
 * 以下その他のタグ<br>
 */
/**
 *
 * @author normal
 */
public class ShortEventDescriptorTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Descriptor.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final ShortEventDescriptor target;

    public ShortEventDescriptorTest() throws DecoderException, InvocationTargetException {
        byte[] x1 = new Descriptors().getSHORT_EVENT_DESCRIPTOR_BYTE();
        Descriptor desc = Descriptors.init(x1);
        LOG.debug(desc);
        target = new ShortEventDescriptor(desc);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws DecoderException {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getISO_639_language_code method, of class ShortEventDescriptor.
     */
    @Test
    public void testGetISO_639_language_code() {
        LOG.debug("getISO_639_language_code");
        ShortEventDescriptor instance = target;
        String expResult = "jpn";
        String result = instance.getISO_639_language_code();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEvent_name_length method, of class ShortEventDescriptor.
     */
    @Test
    public void testGetEvent_name_length() {
        LOG.debug("getEvent_name_length");
        ShortEventDescriptor instance = target;
        int expResult = 61;
        int result = instance.getEvent_name_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEvent_name_char method, of class ShortEventDescriptor.
     */
    @Test
    public void testGetEvent_name_char() throws DecoderException {
        LOG.debug("getEvent_name_char");
        ShortEventDescriptor instance = target;
        byte[] expResult = Hex.decodeHex("1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea".toCharArray());
        byte[] result = instance.getEvent_name_char();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getEvent_name_String method, of class ShortEventDescriptor.
     */
    @Test
    public void testGetEvent_name_String() {
        LOG.debug("getEvent_name_String");
        ShortEventDescriptor instance = target;
        String expResult = "ワンワンパッコロ！キャラともワールド　飛び出せ！人気ソング【字】【再】";
        String result = instance.getEvent_name_String();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText_length method, of class ShortEventDescriptor.
     */
    @Test
    public void testGetText_length() {
        LOG.debug("getText_length");
        ShortEventDescriptor instance = target;
        int expResult = 121;
        int result = instance.getText_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getText_char method, of class ShortEventDescriptor.
     */
    @Test
    public void testGetText_char() throws DecoderException {
        ShortEventDescriptor instance = target;
        byte[] expResult = Hex.decodeHex("1b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea1".toCharArray());
        byte[] result = instance.getText_char();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getText_String method, of class ShortEventDescriptor.
     */
    @Test
    public void testGetText_String() {
        LOG.debug("getText_String");
        ShortEventDescriptor instance = target;
        String expResult = "ワンワンたちが、こども番組で人気の歌を楽しく紹介。曲は「ゆきだるまのユキちゃんと」「いっしょならへいきだよ」など。「ドレミファ・どーなっつ！」のれっしーが登場！";
        String result = instance.getText_String();
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class ShortEventDescriptor.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        ShortEventDescriptor instance = target;
        LOG.debug(instance);

    }
}
