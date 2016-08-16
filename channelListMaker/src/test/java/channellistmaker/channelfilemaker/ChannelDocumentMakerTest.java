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
package channellistmaker.channelfilemaker;

import channellistmaker.dataextractor.channel.Channel;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author normal
 */
public class ChannelDocumentMakerTest {

    public ChannelDocumentMakerTest() {
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

//    /**
//     * Test of getChannels method, of class ChannelDocumentMaker.
//     */
//    @Test
//    public void testGetChannels() {
//        System.out.println("getChannels");
//        ChannelDocumentMaker instance = null;
//        Map<MultiKey<Integer>, Channel> expResult = null;
//        Map<MultiKey<Integer>, Channel> result = instance.getChannels();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    /**
     * Test of getChannelList method, of class ChannelDocumentMaker.
     */
    @Test
    public void testGetChannelList() {
        System.out.println("getChannelList");
        Channel exp1 = new Channel(1, "ja_JP", "a", "111", 111, 555, 4444);
        Channel exp2 = new Channel(2, "ja_JP", "b", "222", 222, 666, 5555);
        Channel exp3 = new Channel(2, "ja_JP", "c", "333", 222, 666, 6666);
        Channel exp4 = new Channel(4, "ja_JP", "d", "333", 444, 888, 7777);
        Map<MultiKey<Integer>, Channel> x = new HashMap<>();

        x.put(exp1.getKeyfields().getMuiltiKey(), exp1);
        x.put(exp2.getKeyfields().getMuiltiKey(), exp2);
        x.put(exp3.getKeyfields().getMuiltiKey(), exp3);
        x.put(exp4.getKeyfields().getMuiltiKey(), exp4);

        System.out.println(x.size());

        ChannelDocumentMaker instance = new ChannelDocumentMaker(x);
        instance.getChannelList();
    }

}
