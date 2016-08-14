/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section;

import java.lang.invoke.MethodHandles;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import libepg.epg.section.SectionBody;
import libepg.epg.section.TABLE_ID;
import static libepg.epg.section.TABLE_ID.EIT_OTHER_STREAM_8_DAYS;
import static libepg.epg.section.TABLE_ID.EIT_OTHER_STREAM_NOW_AND_NEXT;
import static libepg.epg.section.TABLE_ID.EIT_THIS_STREAM_8_DAYS;
import static libepg.epg.section.TABLE_ID.EIT_THIS_STREAM_NOW_AND_NEXT;
import static libepg.epg.section.TABLE_ID.OTHER_TABLE_IDS;
import static libepg.epg.section.TABLE_ID.SDT;
import libepg.epg.section.body.eventinformationtable.EventInformationTableBody;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
public class TABLE_IDTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, TABLE_IDTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    public TABLE_IDTest() {
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
     * Test of values method, of class TABLE_ID.
     */
    @Test
    public void testValues() {
        LOG.debug("values");
        TABLE_ID[] expResult = {SDT, EIT_THIS_STREAM_8_DAYS, EIT_THIS_STREAM_NOW_AND_NEXT, EIT_OTHER_STREAM_8_DAYS, EIT_OTHER_STREAM_NOW_AND_NEXT, OTHER_TABLE_IDS};
        TABLE_ID[] result = TABLE_ID.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class TABLE_ID.
     */
    @Test
    public void testValueOf() {
        LOG.debug("valueOf");
        String name = "SDT";
        TABLE_ID expResult = TABLE_ID.SDT;
        TABLE_ID result = TABLE_ID.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of reverseLookUp method, of class TABLE_ID.
     */
    @Test
    public void testReverseLookUp() {
        LOG.debug("reverseLookUp");
        Integer tableID = 0x67;
        TABLE_ID expResult = TABLE_ID.EIT_OTHER_STREAM_8_DAYS;
        TABLE_ID result = TABLE_ID.reverseLookUp(tableID);
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class TABLE_ID.
     */
    @Test
    public void testContains() {
        LOG.debug("contains");
        Integer tableID = 0x42;
        TABLE_ID instance = TABLE_ID.SDT;
        boolean expResult = true;
        boolean result = instance.contains(tableID);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMaxSectionLength method, of class TABLE_ID.
     */
    @Test
    public void testGetMaxSectionLength() {
        LOG.debug("getMaxSectionLength");
        TABLE_ID instance = TABLE_ID.EIT_OTHER_STREAM_8_DAYS;
        TABLE_ID.MAX_SECTION_LENGTH expResult = TABLE_ID.MAX_SECTION_LENGTH.BYTE_4093;
        TABLE_ID.MAX_SECTION_LENGTH result = instance.getMaxSectionLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class TABLE_ID.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        LOG.debug(EIT_OTHER_STREAM_8_DAYS);
    }

    /**
     * Test of getDataType method, of class TABLE_ID.
     */
    @Test
    public void testGetDataType() {
        LOG.debug("getDataType");
        TABLE_ID instance = EIT_THIS_STREAM_NOW_AND_NEXT;
        Class<? extends SectionBody> expResult = EventInformationTableBody.class;
        Class<? extends SectionBody> result = instance.getDataType();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTableName method, of class TABLE_ID.
     */
    @Test
    public void testGetTableName() {
        LOG.debug("getTableName");
        TABLE_ID instance = SDT;
        String expResult = "サービス記述テーブル";
        String result = instance.getTableName();
        assertEquals(expResult, result);
    }

}
