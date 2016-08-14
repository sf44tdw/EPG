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
package channellistmaker.listmaker;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author normal
 */
public class XMLLoaderTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    private final XMLLoader target = new XMLLoader(Charset.forName("UTF-8"));

    public XMLLoaderTest() {
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
     * Test of getCharset method, of class XMLLoader.
     */
    @Test
    public void testGetCharset() {
        LOG.info("");
        XMLLoader instance = target;
        Charset expResult = Charset.forName("UTF-8");
        Charset result = instance.getCharset();
        assertEquals(expResult, result);
    }

    /**
     * Test of Load method, of class XMLLoader.
     */
    @Test
    public void testLoad() {
        LOG.info("");
        File F = new File("./test/101.xml");
        XMLLoader instance = target;
        Document result = instance.Load(F);
        assertEquals("1.0", result.getXmlVersion());
    }

}
