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
package channellistmaker.dataextractor;

import java.lang.invoke.MethodHandles;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import testtool.EqualsChecker;

/**
 *
 * @author normal
 */
public class KeyFieldsTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }
    private final KeyFields target1 = new KeyFields("CID1", 0, 1, 2);
    private final KeyFields target1_2 = new KeyFields("CID1", 0, 1, 2);
    private final KeyFields target2 = new KeyFields("CID2", 1, 2, 3);
    private final KeyFields target3 = new KeyFields("CID3", 2, 3, 4);

    public KeyFieldsTest() {
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

    @Test(expected = IllegalArgumentException.class)
    public void testSetChannelId1() {
        LOG.info("");
        final KeyFields target = new KeyFields(null, 0, 1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetChannelId2() {
        LOG.info("");
        final KeyFields target = new KeyFields("", 0, 1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTransportStreamId1() {
        LOG.info("");
        final KeyFields target = new KeyFields("CID1", -1, 1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOriginalNetworkId1() {
        LOG.info("");
        final KeyFields target = new KeyFields("CID1", 0, -1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetServiceI2() {
        LOG.info("");
        final KeyFields target = new KeyFields("CID1", 0, 1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTransportStreamId2() {
        LOG.info("");
        final KeyFields target = new KeyFields("CID1", 0x010000, 1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetOriginalNetworkId2() {
        LOG.info("");
        final KeyFields target = new KeyFields("CID1", 0, 0x010000, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetServiceId2() {
        LOG.info("");
        final KeyFields target = new KeyFields("CID1", 0, 1, 0x010000);
    }

    /**
     * Test of getId method, of class KeyFields.
     */
    @Test
    public void testGetChannelId() {
        System.out.println("getId");
        KeyFields instance = target1;
        String expResult = "CID1";
        String result = instance.getChannelId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTransportStreamId method, of class KeyFields.
     */
    @Test
    public void testGetTransportStreamId() {
        System.out.println("getTransportStreamId");
        KeyFields instance = target1;
        int expResult = 0;
        int result = instance.getTransportStreamId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getOriginalNetworkId method, of class KeyFields.
     */
    @Test
    public void testGetOriginalNetworkId() {
        System.out.println("getOriginalNetworkId");
        KeyFields instance = target1;
        int expResult = 1;
        int result = instance.getOriginalNetworkId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getServiceId method, of class KeyFields.
     */
    @Test
    public void testGetServiceId() {
        System.out.println("getServiceId");
        KeyFields instance = target1;
        int expResult = 2;
        int result = instance.getServiceId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMuiltiKey method, of class KeyFields.
     */
    @Test
    public void testGetMuiltiKey() {
        System.out.println("getMuiltiKey");
        KeyFields instance = target1;
        MultiKey<Integer> expResult = new MultiKey<>(0, 1, 2);
        MultiKey<Integer> result = instance.getMuiltiKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class KeyFields.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        KeyFields instance1 = this.target1;
        KeyFields instance2 = this.target1_2;
        KeyFields instance3 = this.target2;
        assertEquals(instance1.hashCode(), instance2.hashCode());
        assertTrue(!(instance1.hashCode() == instance3.hashCode()));
    }

    /**
     * Test of equals method, of class KeyFields.
     */
    @Test
    public void testEquals() {
        LOG.info("");
        EqualsChecker<KeyFields> eqc = new EqualsChecker<>();
        assertTrue(eqc.check_same(target1, target1, target1));
    }

    /**
     * Test of equals method, of class KeyFields.
     */
    @Test
    public void testNotEquals() {
        LOG.info("");
        EqualsChecker<KeyFields> eqc = new EqualsChecker<>();
        assertTrue(eqc.check_not_same(target2, target1, target1));
    }

    /**
     * Test of toString method, of class KeyFields.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        System.out.println(target1.toString());

    }

}
