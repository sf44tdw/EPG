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
package libepg.epg.section.descriptor.contentdescriptor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import libepg.common.descriptor.Descriptors;
import libepg.epg.section.descriptor.DESCRIPTOR_TAG;
import libepg.util.LoggerFactory;

/**
 * 540682ff81ffa0ff<br>
 * 記述子タグ=54<br>
 * 記述子長=06<br>
 * content_nibble_level_1（ジャンル1）=8<br>
 * content_nibble_level_2（ジャンル2）=2<br>
 * user_nibble=f<br>
 * user_nibble=f<br>
 * content_nibble_level_1（ジャンル1）=8<br>
 * content_nibble_level_2（ジャンル2）=1<br>
 * user_nibble=f<br>
 * user_nibble=f<br>
 * content_nibble_level_1（ジャンル1）=a<br>
 * content_nibble_level_2（ジャンル2）=0<br>
 * user_nibble=f<br>
 * user_nibble=f<br>
 */
/**
 *
 * @author normal
 */
public class ContentDescriptorTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ContentDescriptorTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    private final Descriptors d;

    public ContentDescriptorTest() throws DecoderException {
        d = new Descriptors();
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
     * Test of getNibbles method, of class ContentDescriptor.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testGetNibbles() throws DecoderException, Throwable {
        LOG.debug("getNibbles");
        ContentDescriptor instance = new ContentDescriptor(d.getCONTENT_DESCRIPTOR());
        List<Nibble> expResult = new ArrayList<>();
        expResult.add(NIbbleMaker.init(Hex.decodeHex("82ff".toCharArray())));
        expResult.add(NIbbleMaker.init(Hex.decodeHex("81ff".toCharArray())));
        expResult.add(NIbbleMaker.init(Hex.decodeHex("a0ff".toCharArray())));
        List<Nibble> result = instance.getNibbles();
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class ContentDescriptor.
     */
    @Test
    public void testToString() {
        LOG.debug("toString1");
        ContentDescriptor instance = new ContentDescriptor(d.getCONTENT_DESCRIPTOR());
        LOG.debug(instance);

    }

    /**
     * Test of toString method, of class ContentDescriptor.
     *
     * @throws java.lang.reflect.InvocationTargetException
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testToString2() throws InvocationTargetException, DecoderException {
        LOG.debug("toString2");
        ContentDescriptor instance = new ContentDescriptor(Descriptors.init(Hex.decodeHex("540260ff".toCharArray())));
        LOG.debug(instance);

    }

}
