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
package channellistmaker.dataextractor.channel;

import channellistmaker.listmaker.XMLLoader;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.collections4.keyvalue.MultiKey;
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
public class ChannelDataExtractorTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    private final File F = new File("./test/25.xml");

    private final XMLLoader loader = new XMLLoader(Charset.forName("UTF-8"));

    private final Document doc;

    public ChannelDataExtractorTest() {
        doc = loader.Load(F);
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

    @Test
    public void testSomeMethod() {
        LOG.info("");

        Channel exp = new Channel(25, "ja_JP", "日テレ１", "GR_1040", 32738, 32738, 1040);

        ChannelDataExtractor ex = new ChannelDataExtractor(this.doc);
        Map<MultiKey<Integer>, Channel> dest = ex.makeMap();
        Channel c = dest.get(new MultiKey<Integer>(32738, 32738, 1040));
        if (c != null) {
            LOG.info(c);
            assertEquals(c, exp);
        } else {
            fail("オブジェクトが無い。");
        }
    }

}
