/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.sectionreconstructor;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import libepg.common.packet.TestPacket_EIT;
import libepg.common.packet.TestPacket_SDT;
import libepg.epg.section.Section;
import libepg.epg.section.Section.CRC_STATUS;
import libepg.epg.section.sectionreconstructor.SectionReconstructor;
import libepg.ts.aligner.TsPacketAligner;
import libepg.ts.packet.PROGRAM_ID;
import libepg.ts.packet.TsPacket;
import libepg.ts.packet.TsPacketParcel;
import epgtools.loggerfactory.LoggerFactory;
import static org.junit.Assert.assertEquals;

/**
 * EITを含むパケットを1000個送り込んでセクションを再構築させ、全てのセクションについてCRCエラーが無ければ問題なし。
 * (ただし、内容の同じセクションが2個以上あった場合、最初の1個以外は無視する。)
 *
 * @author normal
 */
public class SectionReconstructorTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, SectionReconstructor.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final TestPacket_EIT eits;

    private final PROGRAM_ID pids = PROGRAM_ID.EIT_GR_ST;

    private final List<TsPacketAligner> als = new ArrayList<>();

    public SectionReconstructorTest() throws DecoderException {
        LOG.debug(pids);
        eits = new TestPacket_EIT();
        for (int pid : pids.getPids()) {
            this.als.add(new TsPacketAligner(pid));
        }
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

    private Map<Integer, List<TsPacketParcel>> getParcels() {
        List<TsPacket> packets = this.eits.getEitList();
        Map<Integer, List<TsPacketParcel>> parcels = new HashMap<>();

        //pid毎に分類し、欠落、重複しているパケットを除去する。
        for (TsPacketAligner alligner : this.als) {
            for (TsPacket p : packets) {
                alligner.add(p);
            }
            parcels.put(alligner.getPid(), alligner.getPackets());
        }
        return Collections.unmodifiableMap(parcels);
    }

    @Test(expected = IllegalArgumentException.class)
    public void con_NG_multi_pid_list() throws DecoderException {
        LOG.debug("con_NG");

        LOG.debug("expect 18 but was 17");

        TestPacket_SDT sdt = new TestPacket_SDT();

        TsPacketAligner al = new TsPacketAligner(0x11);

        for (TsPacket p : sdt.getPackets()) {
            al.add(p);
        }
        List<TsPacketParcel> p = al.getPackets();

        SectionReconstructor instance = new SectionReconstructor(p, 18);

    }

    /**
     * Test of getPid method, of class SectionReconstructor.
     */
    @Test
    public void testGetPid() {
        LOG.debug("getPid");

        Map<Integer, List<TsPacketParcel>> p = this.getParcels();

        int k = p.keySet().iterator().next();

        LOG.debug("k=" + k);

        SectionReconstructor instance = new SectionReconstructor(p.get(k), k);
        int expResult = k;
        int result = instance.getPid();
        assertEquals(expResult, result);

    }

    private Set<byte[]> makeEITList() {

        Set<byte[]> ret = new HashSet<>();

        for (int pid : pids.getPids()) {
            ret.addAll(new SectionReconstructor(this.getParcels().get(pid), pid).getSectionByteArrays());
        }

        return Collections.unmodifiableSet(ret);
    }


//    /**
//     * Test of getSections method, of class SectionReconstructor.
//     */
//    @Test
//    public void testGetSections() {
//        System.out.println("getSections");
//        SectionReconstructor instance = null;
//        Set<Section> expResult = null;
//        Set<Section> result = instance.getSections();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getSectionByteArrays method, of class SectionReconstructor.
     */
    @Test
    public void testGetSectionByteArrays() {

        LOG.debug("getSectionByteArrays");
        Set<byte[]> result = makeEITList();

        StringBuilder sbl = new StringBuilder();
        sbl.append("要素数=");
        sbl.append(result.size());
        sbl.append("\n");

        for (byte[] b : this.makeEITList()) {
            Section s = new Section(b);

            sbl.append(Hex.encodeHexString(s.getData()));
            sbl.append("\n");

            assertEquals(s.checkCRC(), CRC_STATUS.NO_CRC_ERROR);

        }
        LOG.debug(sbl.toString());
    }

}
