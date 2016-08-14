/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.sectionreconstructor;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import libepg.common.packet.TestPacket_EIT;
import libepg.epg.section.sectionreconstructor.PayLoadSplitter;
import libepg.ts.packet.TsPacket;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
public class PayLoadSplitterTest {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, PayLoadSplitter.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    
    private TestPacket_EIT testData;

    public PayLoadSplitterTest() throws DecoderException {
        testData = new TestPacket_EIT();
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
     * Test of getPacket method, of class PayLoadSplitter.
     */
    @Test
    public void testGetPacket() {
        LOG.debug("getPacket");
        TsPacket packet = this.testData.getEit0();
        PayLoadSplitter instance = new PayLoadSplitter();
        instance.setPacket(packet);
        TsPacket expResult = this.testData.getEit0();
        TsPacket result = instance.getPacket();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSplittedPayLoad method, of class PayLoadSplitter.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testGetSplittedPayLoad01() throws DecoderException {
        LOG.debug("getSplittedPayLoad_先端有り 分割有り");
        TsPacket packet = this.testData.getEit0();

        PayLoadSplitter instance = new PayLoadSplitter();
        instance.setPacket(packet);
        Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> expResult = new HashMap<>();
        expResult.put(PayLoadSplitter.PAYLOAD_PART_KEY.PREV_POINTER, Hex.decodeHex("4a2cc0b1ce36753456004e24126a706e1e001cf231693d50fcbdf3ca432ace317c3f3ca44c254e4fcb4777eadeb9fa004e2f226a706e29063d5031693c5421215a3d503169215b4170342240354d3a2124215a386cea215b63264c6e4d243b52005006f1b3006a706e5406a2ff22ff84ffc10184c409f203100fff6f6a706ec70e0008300501136a706e006a706e00d60d13040844f5040944f5040a44f53246fb41".toCharArray()));
        expResult.put(PayLoadSplitter.PAYLOAD_PART_KEY.NEXT_POINTER, Hex.decodeHex("4ef2920408d101017fe17fe1014e44f8dfe1233000".toCharArray()));
        Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> result = instance.getSplittedPayLoad();

        assertEquals(expResult.size(), result.size());

        Set<PayLoadSplitter.PAYLOAD_PART_KEY> expResKeys = expResult.keySet();
        Set<PayLoadSplitter.PAYLOAD_PART_KEY> resKeys = result.keySet();
        assertEquals(expResKeys, resKeys);

        for (PayLoadSplitter.PAYLOAD_PART_KEY key : resKeys) {
            byte[] expResBytes = expResult.get(key);
            byte[] ResBytes = result.get(key);
            assertArrayEquals("配列を比較", expResBytes, ResBytes);

        }

    }

    /**
     * Test of getSplittedPayLoad method, of class PayLoadSplitter.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testGetSplittedPayLoad02() throws DecoderException {
        LOG.debug("getSplittedPayLoad_先端有り 分割なし");
        TsPacket packet = this.testData.getEit20();

        PayLoadSplitter instance = new PayLoadSplitter();
        instance.setPacket(packet);
        Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> expResult = new HashMap<>();
        expResult.put(PayLoadSplitter.PAYLOAD_PART_KEY.PAYLOAD_AFTER_2_BYTE, Hex.decodeHex("4ef0c10409d100017fe17fe1014e44f5dfe123000000300000a64d6f6a706e11487ece5464fe412afb432afc1b243b7a56593f483661ca1d46f91d5ef243663f34cbfd487e3d5134553e5ef20e33c4ce1d441d5cc7efabeae4b9af0f3b58466eb9eb3f3734363350487e3d5148564148fa3a233273cffb432afcfa304646624c7221274170342240354d3a5006f1b3006a706e5406a2ff22ff84ffc10184c409f203100fff6f6a706ec70e0008300501136a706e006a70".toCharArray()));
        Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> result = instance.getSplittedPayLoad();

        assertEquals(expResult.size(), result.size());

        Set<PayLoadSplitter.PAYLOAD_PART_KEY> expResKeys = expResult.keySet();
        Set<PayLoadSplitter.PAYLOAD_PART_KEY> resKeys = result.keySet();
        assertEquals(expResKeys, resKeys);

        for (PayLoadSplitter.PAYLOAD_PART_KEY key : resKeys) {
            byte[] expResBytes = expResult.get(key);
            byte[] ResBytes = result.get(key);
            assertArrayEquals("配列を比較", expResBytes, ResBytes);

        }
    }

    /**
     * Test of getSplittedPayLoad method, of class PayLoadSplitter.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testGetSplittedPayLoad03() throws DecoderException {
        LOG.debug("getSplittedPayLoad_先端なし 分割なし");
        TsPacket packet = this.testData.getEit19();

        PayLoadSplitter instance = new PayLoadSplitter();
        instance.setPacket(packet);
        Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> expResult = new HashMap<>();
        expResult.put(PayLoadSplitter.PAYLOAD_PART_KEY.ALL_PAYLOAD, Hex.decodeHex("300501136a706e006a706e00d609120408494004094940950ea6b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray()));
        Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> result = instance.getSplittedPayLoad();

        assertEquals(expResult.size(), result.size());

        Set<PayLoadSplitter.PAYLOAD_PART_KEY> expResKeys = expResult.keySet();
        Set<PayLoadSplitter.PAYLOAD_PART_KEY> resKeys = result.keySet();
        assertEquals(expResKeys, resKeys);

        for (PayLoadSplitter.PAYLOAD_PART_KEY key : resKeys) {
            byte[] expResBytes = expResult.get(key);
            byte[] ResBytes = result.get(key);
            assertArrayEquals("配列を比較", expResBytes, ResBytes);

        }
    }

}
