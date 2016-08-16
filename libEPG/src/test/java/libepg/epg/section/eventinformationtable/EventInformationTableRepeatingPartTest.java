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
package libepg.epg.section.eventinformationtable;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import libepg.epg.section.descriptor.DescriptorsLoop;
import libepg.epg.section.body.eventinformationtable.EventInformationTableRepeatingPart;
import epgtools.loggerfactory.LoggerFactory;

/**
 * 7361e07c18000000250000f64dbb6a706e3d1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea791b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea15006f1b3006a706ec409f203100f0f6f6a706ec70e0008300501136a706e006a706e005406a8ff54ff49ffc10184d609120067736100687361<br>
 * event_id(16bit)=7361<br>
 * start_time(40bit)=e07c180000<br>
 * duration(24bit)=002500<br>
 * いろいろ(running_status(3bit)=0,free_CA_mode(1bit)=0,descriptors_loop_length(12bit)=0f6=246)=00f6<br>
 * 記述子ループ(246byte)=4dbb6a706e3d1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea791b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea15006f1b3006a706ec409f203100f0f6f6a706ec70e0008300501136a706e006a706e005406a8ff54ff49ffc10184d609120067736100687361
 * <br>
 */
/**
 *
 * @author normal
 */
public class EventInformationTableRepeatingPartTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, EventInformationTableRepeatingPart.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    private final char[] data = "7361e07c18000000250000f64dbb6a706e3d1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea791b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea15006f1b3006a706ec409f203100f0f6f6a706ec70e0008300501136a706e006a706e005406a8ff54ff49ffc10184d609120067736100687361".toCharArray();
    private final EventInformationTableRepeatingPart target;

    //強制呼び出し
    private static final EventInformationTableRepeatingPart init(byte[] data) throws InvocationTargetException {
        try {
            Object[] args = {data};
            Class<?>[] params = {byte[].class};
            Constructor<EventInformationTableRepeatingPart> constructor = EventInformationTableRepeatingPart.class.getDeclaredConstructor(params);
            constructor.setAccessible(true);
            EventInformationTableRepeatingPart target = constructor.newInstance(args);
            return target;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
            LOG.fatal(ex);
        }
        return null;
    }

    public EventInformationTableRepeatingPartTest() throws DecoderException, InvocationTargetException {
        target = EventInformationTableRepeatingPartTest.init(Hex.decodeHex(data));
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
     * Test of getData method, of class EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetData() throws DecoderException {
        LOG.debug("getData");
        EventInformationTableRepeatingPart instance = target;
        byte[] expResult = Hex.decodeHex(data);
        byte[] result = instance.getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getEvent_id method, of class EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetEvent_id() {
        LOG.debug("getEvent_id");
        EventInformationTableRepeatingPart instance = target;
        int expResult = 0x7361;
        int result = instance.getEvent_id();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStart_time method, of class
     * EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetStart_time() throws DecoderException {
        LOG.debug("getStart_time");
        EventInformationTableRepeatingPart instance = target;
        byte[] expResult = Hex.decodeHex("e07c180000".toCharArray());
        byte[] result = instance.getStart_time();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getStart_time_Object method, of class
     * EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetStart_time_Object() throws Exception {
        LOG.debug("getStart_time_Object");
        EventInformationTableRepeatingPart instance = target;
        Timestamp expResult = Timestamp.valueOf("2016-03-21 18:00:00.0");
        Timestamp result = instance.getStart_time_Object();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDuration method, of class EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetDuration() throws DecoderException {
        LOG.debug("getDuration");
        EventInformationTableRepeatingPart instance = target;
        byte[] expResult = Hex.decodeHex("002500".toCharArray());
        byte[] result = instance.getDuration();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getStopTime_Object method, of class
     * EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetStopTime_Object() throws Exception {
        LOG.debug("getStopTime_Object");
        EventInformationTableRepeatingPart instance = target;
        Timestamp expResult = Timestamp.valueOf("2016-03-21 18:25:00.0");;
        Timestamp result = instance.getStopTime_Object();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRunning_status method, of class
     * EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetRunning_status() {
        LOG.debug("getRunning_status");
        EventInformationTableRepeatingPart instance = target;
        int expResult = 0;
        int result = instance.getRunning_status();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFree_CA_mode method, of class
     * EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetFree_CA_mode() {
        LOG.debug("getFree_CA_mode");
        EventInformationTableRepeatingPart instance = target;
        int expResult = 0;
        int result = instance.getFree_CA_mode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescriptors_loop_length method, of class
     * EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetDescriptors_loop_length() {
        LOG.debug("getDescriptors_loop_length");
        EventInformationTableRepeatingPart instance = target;
        int expResult = 0x0f6;
        int result = instance.getDescriptors_loop_length();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescriptors_loop method, of class
     * EventInformationTableRepeatingPart.
     */
    @Test
    public void testGetDescriptors_loop() throws DecoderException {
        LOG.debug("getDescriptors_loop");
        EventInformationTableRepeatingPart instance = target;
        DescriptorsLoop expResult = new DescriptorsLoop(Hex.decodeHex("4dbb6a706e3d1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea791b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea15006f1b3006a706ec409f203100f0f6f6a706ec70e0008300501136a706e006a706e005406a8ff54ff49ffc10184d609120067736100687361".toCharArray()));
        DescriptorsLoop result = instance.getDescriptors_loop();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class EventInformationTableRepeatingPart.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        EventInformationTableRepeatingPart instance = target;
        LOG.debug(target);
    }

//    /**
//     * Test of hashCode method, of class EventInformationTableRepeatingPart.
//     */
//    @Test
//    public void testHashCode() {
//        System.out.println("hashCode");
//        EventInformationTableRepeatingPart instance = target;
//        int expResult = 0;
//        int result = instance.hashCode();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class EventInformationTableRepeatingPart.
//     */
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        Object obj = null;
//        EventInformationTableRepeatingPart instance = target;
//        boolean expResult = false;
//        boolean result = instance.equals(obj);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
