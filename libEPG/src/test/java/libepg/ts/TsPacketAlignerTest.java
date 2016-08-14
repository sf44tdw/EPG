/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.ts;

import libepg.ts.aligner.TsPacketAligner;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import libepg.common.packet.TestPacket_Only_Adaptation_Field;
import libepg.common.packet.TestPacket_pid0x11_count_d_0_0;
import libepg.common.packet.TsPacket_error_indicator;
import libepg.ts.packet.TsPacket;
import libepg.ts.packet.TsPacketParcel;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
public class TsPacketAlignerTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, TsPacketAligner.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final TestPacket_pid0x11_count_d_0_0 testData;

    public TsPacketAlignerTest() throws DecoderException {
        testData = new TestPacket_pid0x11_count_d_0_0();
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
     * Test of getPid method, of class TsPacketAligner.
     */
    @Test
    public void testGetPid() {
        LOG.debug("\ngetPid");
        TsPacketAligner instance = new TsPacketAligner(10);
        int expResult = 10;
        int result = instance.getPid();
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class TsPacketAligner.
     */
    @Test
    public void testAdd_ONLY_ADAPTATION() throws DecoderException {
        LOG.debug("\nadd_正常系_アダプテーションフィールドのみ");

        TestPacket_Only_Adaptation_Field tspa = new TestPacket_Only_Adaptation_Field();

        TsPacketAligner instance = new TsPacketAligner(0x1ff);
        TsPacket p;
        TsPacketAligner.ADD_RESULT add;

        TsPacket ps[] = {tspa.target00, tspa.target01, tspa.target02, tspa.target03};

        //想定される戻り値
        List<TsPacketParcel> expResult = new ArrayList<>();
        TsPacket expPs[] = ps;
        for (TsPacket pk : expPs) {
            expResult.add(new TsPacketParcel(pk, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        }

        long count = 1;
        for (TsPacket pk : ps) {
            p = pk;
            add = instance.add(p);
            LOG.trace(count);
            LOG.trace(pk);
            LOG.trace(add);
            count++;
            boolean ret = (add == TsPacketAligner.ADD_RESULT.OK || add == TsPacketAligner.ADD_RESULT.DUPLICATION);
            assertEquals(true, ret);
        }
        List<TsPacketParcel> result = instance.getPackets();
        result.stream().map((pkp) -> {
            boolean ret = true;
            ret = ret && pkp.isMissingJustBefore().getFlag();
            return ret;
        }).forEach((ret) -> {
            assertEquals(false, ret);
        });

        long xx = 0;
        for (TsPacketParcel ppk : result) {
            if (xx == 0 && ppk.getPacket().getContinuity_counter() != 0) {
                fail("結果リストのカウンタが0以外から開始");
            }
            xx++;
        }

        assertEquals(result, expResult);
    }

    /**
     * Test of add method, of class TsPacketAligner.
     */
    @Test
    public void testAdd_NG() throws DecoderException {
        LOG.debug("\nadd_異常系");
        TsPacketAligner instance;
        TsPacket p;
        TsPacketAligner.ADD_RESULT add;
        boolean ret;

        LOG.debug("\n連送パケット");
        p = this.testData.target_p0d;
        instance = new TsPacketAligner(p.getPid());
        add = instance.add(p);
        ret = (add == TsPacketAligner.ADD_RESULT.OK);
        assertEquals(true, ret);
        add = instance.add(p);
        ret = (add == TsPacketAligner.ADD_RESULT.DUPLICATION);
        assertEquals(true, ret);

        LOG.debug("\nエラーパケット");
        p = new TsPacket_error_indicator().target_error01;
        instance = new TsPacketAligner(p.getPid());
        add = instance.add(p);
        ret = (add == TsPacketAligner.ADD_RESULT.ERROR_PACKET);
        assertEquals(true, ret);

        LOG.debug("\npid相違");
        p = new TestPacket_Only_Adaptation_Field().target00;
        instance = new TsPacketAligner(0x11);
        add = instance.add(p);
        assertEquals(TsPacketAligner.ADD_RESULT.DEFFERENT_PID, add);

        LOG.debug("\nnull追加");
        p = new TestPacket_Only_Adaptation_Field().target00;
        instance = new TsPacketAligner(p.getPid());
        ret = false;
        try {
            add = instance.add(null);
            ret = false;
        } catch (NullPointerException e) {
            ret = true;
        }

        assertEquals(true, ret);
    }

    /**
     * Test of getPackets method, of class TsPacketAligner.
     */
    @Test
    public void testGetPackets() {
        LOG.debug("\ngetPackets");
        TsPacketAligner instance = new TsPacketAligner(0x11);
        TsPacket p;
        TsPacketAligner.ADD_RESULT add;

        TsPacket ps[] = {this.testData.target_p0d, this.testData.target_p0e, this.testData.target_p0f, this.testData.target_p10, this.testData.target_p11, this.testData.target_p12, this.testData.target_p13, this.testData.target_p14, this.testData.target_p15,
            this.testData.target_p16, this.testData.target_p17, this.testData.target_p18, this.testData.target_p19, this.testData.target_p1a, this.testData.target_p1b, this.testData.target_p1c, this.testData.target_p1d, this.testData.target_p1e,
            this.testData.target_p1f, this.testData.target_p20, this.testData.target_p21, this.testData.target_p22, this.testData.target_p23, this.testData.target_p24, this.testData.target_p25, this.testData.target_p26, this.testData.target_p27,
            this.testData.target_p28, this.testData.target_p29, this.testData.target_p2a, this.testData.target_p3b, this.testData.target_p3c, this.testData.target_p3d, this.testData.target_p3e, this.testData.target_p3f};

        //想定される戻り値
        List<TsPacketParcel> expResult = new ArrayList<>();
        expResult.add(new TsPacketParcel(this.testData.target_p1d, TsPacketParcel.MISSING_PACKET_FLAG.MISSING_JUST_BEFORE));
        TsPacket expPs[] = {this.testData.target_p0e, this.testData.target_p0f, this.testData.target_p10, this.testData.target_p11, this.testData.target_p12, this.testData.target_p13, this.testData.target_p14, this.testData.target_p15,
            this.testData.target_p16, this.testData.target_p17, this.testData.target_p18, this.testData.target_p19, this.testData.target_p1a, this.testData.target_p1b, this.testData.target_p1c, this.testData.target_p1d, this.testData.target_p1e,
            this.testData.target_p1f, this.testData.target_p20, this.testData.target_p21, this.testData.target_p22, this.testData.target_p23, this.testData.target_p24, this.testData.target_p25, this.testData.target_p26, this.testData.target_p27,
            this.testData.target_p28, this.testData.target_p29, this.testData.target_p2a, this.testData.target_p3b, this.testData.target_p3c, this.testData.target_p3d, this.testData.target_p3e, this.testData.target_p3f};
        for (TsPacket pk : expPs) {
            expResult.add(new TsPacketParcel(pk, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        }

        long count = 1;
        for (TsPacket pk : ps) {
            p = pk;
            add = instance.add(p);
            LOG.trace(count);
            LOG.trace(add);
            count++;
            boolean ret = (add == TsPacketAligner.ADD_RESULT.OK || add == TsPacketAligner.ADD_RESULT.DUPLICATION);
            assertEquals(true, ret);
        }

        List<TsPacketParcel> result = instance.getPackets();

        assertEquals(result, expResult);
    }

    /**
     * Test of getPackets method, of class TsPacketAligner.
     */
    @Test
    public void testGetPackets_missing() {
        LOG.debug("\ngetPackets_パケット飛びあり");
        TsPacketAligner instance = new TsPacketAligner(0x11);
        TsPacket p;
        TsPacketAligner.ADD_RESULT add;

        TsPacket ps[] = {this.testData.target_p0d, this.testData.target_p0e, this.testData.target_p0f, this.testData.target_p10, this.testData.target_p11, this.testData.target_p12, this.testData.target_p13, this.testData.target_p14, this.testData.target_p15,
            this.testData.target_p16, this.testData.target_p17, this.testData.target_p19, this.testData.target_p1a, this.testData.target_p1b, this.testData.target_p1c, this.testData.target_p1d, this.testData.target_p1e,
            this.testData.target_p1f, this.testData.target_p20, this.testData.target_p21, this.testData.target_p22, this.testData.target_p23, this.testData.target_p24, this.testData.target_p25, this.testData.target_p26, this.testData.target_p27,
            this.testData.target_p28, this.testData.target_p29, this.testData.target_p2a, this.testData.target_p3b, this.testData.target_p3c, this.testData.target_p3d, this.testData.target_p3e, this.testData.target_p3f};

        //想定される戻り値
        List<TsPacketParcel> expResult = new ArrayList<>();
        expResult.add(new TsPacketParcel(this.testData.target_p0d, TsPacketParcel.MISSING_PACKET_FLAG.MISSING_JUST_BEFORE));
        expResult.add(new TsPacketParcel(this.testData.target_p0e, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p0f, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p10, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p11, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p12, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p13, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p14, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p15, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p16, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p17, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p19, TsPacketParcel.MISSING_PACKET_FLAG.MISSING_JUST_BEFORE));
        expResult.add(new TsPacketParcel(this.testData.target_p1a, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p1b, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p1c, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p1d, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p1e, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        expResult.add(new TsPacketParcel(this.testData.target_p1f, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));

        TsPacket expPs[] = {this.testData.target_p20, this.testData.target_p21, this.testData.target_p22, this.testData.target_p23, this.testData.target_p24, this.testData.target_p25, this.testData.target_p26, this.testData.target_p27,
            this.testData.target_p28, this.testData.target_p29, this.testData.target_p2a, this.testData.target_p3b, this.testData.target_p3c, this.testData.target_p3d, this.testData.target_p3e, this.testData.target_p3f};
        for (TsPacket pk : expPs) {
            expResult.add(new TsPacketParcel(pk, TsPacketParcel.MISSING_PACKET_FLAG.NOT_MISSING));
        }

        long count = 1;
        for (TsPacket pk : ps) {
            p = pk;
            add = instance.add(p);
            LOG.trace(count);
            LOG.trace(add);
            count++;
            boolean ret = (add == TsPacketAligner.ADD_RESULT.OK || add == TsPacketAligner.ADD_RESULT.DUPLICATION);
            assertEquals(true, ret);
        }

        List<TsPacketParcel> result = instance.getPackets();

        assertEquals(result, expResult);

    }



}
