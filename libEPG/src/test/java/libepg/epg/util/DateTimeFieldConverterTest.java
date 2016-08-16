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
package libepg.epg.util;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.text.ParseException;
import libepg.epg.util.datetime.DateTimeFieldConverter;
import epgtools.loggerfactory.LoggerFactory;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import testtool.testrule.regexmessagerule.ExpectedExceptionMessage;
import testtool.testrule.regexmessagerule.ExpectedExceptionRule;

/**
 *
 * @author normal
 */
public class DateTimeFieldConverterTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, DateTimeFieldConverter.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    @Rule
    public ExpectedExceptionRule rule = new ExpectedExceptionRule();

    public DateTimeFieldConverterTest() {
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
     * Test of BcdTimeToSecond method, of class DateTimeFieldConverter.
     */
    @Test
    public void testBcdTimeToSecond() throws ParseException {
        LOG.debug("BcdTimeToSecond");
        byte[] hms = {0x01, 0x01, 0x01};
        long expResult = 3661L;
        long result = DateTimeFieldConverter.BcdTimeToSecond(hms);
        assertEquals(expResult, result);
    }

    @Test
    @ExpectedExceptionMessage("^配列が想定外のサイズです.*$")
    public void testBcdTimeToSecond2() throws ParseException {
        LOG.debug("異常系_配列サイズ相違");
        byte[] hms = {0x01, 0x01, 0x01, 0x01};
        long result = DateTimeFieldConverter.BcdTimeToSecond(hms);
    }

    @Test
    @ExpectedExceptionMessage("^時刻が定義なしです.*$")
    public void testBcdTimeToSecond3() throws ParseException {
        LOG.debug("異常系_時刻定義なし");
        byte b = (byte) 0xff;
        byte[] hms = {b, b, b};
        long result = DateTimeFieldConverter.BcdTimeToSecond(hms);
    }

    @Test
    @ExpectedExceptionMessage("^時刻表示の範囲外になっている部分があります.*$")
    public void testBcdTimeToSecond4() throws ParseException {
        LOG.debug("異常系_時");
        byte[] hms = {0x24, 0x01, 0x01};
        long result = DateTimeFieldConverter.BcdTimeToSecond(hms);
    }

    @Test
    @ExpectedExceptionMessage("^時刻表示の範囲外になっている部分があります.*$")
    public void testBcdTimeToSecond5() throws ParseException {
        LOG.debug("異常系_分");
        byte[] hms = {0x00, 0x60, 0x01};
        long result = DateTimeFieldConverter.BcdTimeToSecond(hms);
    }

    @Test
    @ExpectedExceptionMessage("^時刻表示の範囲外になっている部分があります.*$")
    public void testBcdTimeToSecond6() throws ParseException {
        LOG.debug("異常系_秒");
        byte[] hms = {0x01, 0x01, 0x60};
        long result = DateTimeFieldConverter.BcdTimeToSecond(hms);
    }

    /**
     * Test of BytesToSqlDateTime method, of class DateTimeFieldConverter.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testBytesTOSqlDateTime1() throws Exception {
        LOG.debug("BytesTOSqlDateTime1");
        byte[] source = Hex.decodeHex("C079124500".toCharArray());
        Timestamp expResult = new Timestamp(new java.text.SimpleDateFormat("yyyyMMddHHmmss").parse("19931013124500").getTime());
        Timestamp result = DateTimeFieldConverter.BytesToSqlDateTime(source);
        assertEquals(expResult, result);
    }

    /**
     * Test of BytesToSqlDateTime method, of class DateTimeFieldConverter.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testBytesTOSqlDateTime2() throws Exception {
        LOG.debug("BytesTOSqlDateTime2");
        byte[] source = Hex.decodeHex("e07c180000".toCharArray());
        Timestamp expResult = new Timestamp(new java.text.SimpleDateFormat("yyyyMMddHHmmss").parse("20160321180000").getTime());
        Timestamp result = DateTimeFieldConverter.BytesToSqlDateTime(source);
        assertEquals(expResult, result);
    }

    @Test
    @ExpectedExceptionMessage("^配列が想定外のサイズです.*$")
    public void testBytesTOSqlDateTime3() throws Exception {
        LOG.debug("BytesTOSqlDateTime_配列サイズ相違");
        byte[] source = Hex.decodeHex("C07912450000".toCharArray());
        Timestamp result = DateTimeFieldConverter.BytesToSqlDateTime(source);
    }

    @Test
    @ExpectedExceptionMessage("^与えられたフィールドは未定義になっています.*$")
    public void testBytesTOSqlDateTime4() throws Exception {
        LOG.debug("BytesTOSqlDateTime_未定義");
        byte[] source = Hex.decodeHex("ffffffffff".toCharArray());
        Timestamp result = DateTimeFieldConverter.BytesToSqlDateTime(source);
    }
}
