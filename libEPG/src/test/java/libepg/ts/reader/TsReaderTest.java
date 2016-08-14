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
package libepg.ts.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import libepg.common.tsfile.TsFile;
import libepg.ts.packet.PROGRAM_ID;
import libepg.ts.packet.TsPacketParcel;
import libepg.util.LoggerFactory;
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
public class TsReaderTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, TsReader.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final Set<Integer> pids;

    public TsReaderTest() {
        pids = new HashSet();
        pids.addAll(PROGRAM_ID.SDT_OR_BAT.getPids());
        pids.addAll(PROGRAM_ID.EIT_GR_ST.getPids());

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

    @Test(expected = FileNotFoundException.class)
    public void noFile() throws FileNotFoundException {
        LOG.info("noFile");
        TsReader instance = new TsReader(new File("aaa.ts"), this.pids);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nopid_null() throws FileNotFoundException {
        LOG.info("nopid_null");
        TsReader instance = new TsReader(TsFile.getTsFile(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nopid_notNull() throws FileNotFoundException {
        LOG.info("nopid_notNull");
        TsReader instance = new TsReader(TsFile.getTsFile(), new HashSet<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void limit_less_than_0() throws FileNotFoundException {
        LOG.info("limit_less_than_0");
        TsReader instance = new TsReader(TsFile.getTsFile(), this.pids, -1L);
    }

    /**
     * Test of isReadEOF method, of class TsReader.
     *
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testIsReadEOF_true() throws FileNotFoundException {
        LOG.info("isReadEOF_true");
        TsReader instance = new TsReader(TsFile.getTsFile(), this.pids);
        boolean expResult = true;
        boolean result = instance.isReadEOF();
        assertEquals(expResult, result);
    }

    /**
     * Test of isReadEOF method, of class TsReader.
     *
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testIsReadEOF_false() throws FileNotFoundException {
        LOG.info("isReadEOF_false");
        TsReader instance = new TsReader(TsFile.getTsFile(), this.pids, 2000L);
        boolean expResult = false;
        boolean result = instance.isReadEOF();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReadLimit method, of class TsReader.
     *
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testGetReadLimit() throws FileNotFoundException {
        LOG.info("getReadLimit");
        TsReader instance = new TsReader(TsFile.getTsFile(), this.pids, 2000L);
        long expResult = 2000L;
        long result = instance.getReadLimit();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReadLimit method, of class TsReader.
     *
     * @throws java.io.FileNotFoundException
     */
    @Test(expected = NullPointerException.class)
    public void testGetReadLimit_readEOF() throws FileNotFoundException {
        LOG.info("getReadLimit_readEOF");
        TsReader instance = new TsReader(TsFile.getTsFile(), this.pids);
        long l = instance.getReadLimit();
    }

    /**
     * Test of getPackets method, of class TsReader.
     */
    @Test
    public void testGetPackets_NoLimit() throws FileNotFoundException {
        LOG.debug("getPackets_noLimit");
        TsReader instance = new TsReader(TsFile.getTsFile(), this.pids);
        this.read(instance);
    }

    /**
     * Test of getPackets method, of class TsReader.
     */
    @Test
    public void testGetPackets_Limit() throws FileNotFoundException {
        LOG.debug("getPackets_Limit");
        TsReader instance = new TsReader(TsFile.getTsFile(), this.pids, 500L);
        this.read(instance);
    }

    private void read(TsReader instance) {
        LOG.debug("getPackets_正常系");
        Map<Integer, List<TsPacketParcel>> ss = instance.getPackets();
        for (Integer key : ss.keySet()) {
            List<TsPacketParcel> l = ss.get(key);
            int x = 0;
            Iterator<TsPacketParcel> it = l.iterator();
            while (it.hasNext()) {
                TsPacketParcel pk = it.next();

                LOG.debug("***********************************************************************************************************************************");
                LOG.debug(pk.toString());
                LOG.debug("***********************************************************************************************************************************");

                x++;
            }
        }
    }

}
