/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section;

import libepg.util.bytearray.ByteConverter;
import java.lang.invoke.MethodHandles;
import libepg.common.packet.TestPacket_SDT;
import libepg.util.LoggerFactory;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import testtool.EqualsChecker;
import testtool.testrule.regexmessagerule.ExpectedExceptionMessage;
import testtool.testrule.regexmessagerule.ExpectedExceptionRule;

/*
バイト列 = 42f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
セクション共通項目(
table_id:42
(section_syntax_indicator=1,reserved_future_use=1,reserved=3,section_length=151):f097
)
SDT固有項目(
transport_stream_id:7fe1
(reserved=3,version_number=8,current_next_indicator=1):d1
section_number:00
last_section_number:00
original_network_id:7fe1
reserved_future_use:ff

残りの部分 = 0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
1個目(
service_id:0408
(reserved_future_use=7,EIT_user_defined_flags=4,EIT_schedule_flag=1,EIT_present_following_flag=1):f3
(running_status=0,free_CA_mode=0,descriptors_loop_length=32):0020
481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408
)
2個目(
service_id:0409
(reserved_future_use=7,EIT_user_defined_flags=4,EIT_schedule_flag=1,EIT_present_following_flag=1):f3
(running_status=0,free_CA_mode=0,descriptors_loop_length=28):001c
481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08
)
3個目(
service_id:040a
(reserved_future_use=7,EIT_user_defined_flags=4,EIT_schedule_flag=1,EIT_present_following_flag=1):f3
(running_status=0,free_CA_mode=0,descriptors_loop_length=28):001c
481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe08
)
4個目(
service_id:0588
(reserved_future_use=7,EIT_user_defined_flags=1,EIT_schedule_flag=0,EIT_present_following_flag=1):e5
(running_status=0,free_CA_mode=0,descriptors_loop_length=31):001f
480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32
)
CRC:722fa2b5
 */
/**
 *
 * @author normal
 */
public class SectionTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Section.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    @Rule
    public ExpectedExceptionRule rule = new ExpectedExceptionRule();

    private final byte[] section_byte;
    private final Section testSection_OK;

    public SectionTest() throws DecoderException {
        this.section_byte = Hex.decodeHex("42f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray());
        this.testSection_OK = new Section(this.section_byte);

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

//テーブルID定数は、テーブルIDの取りうる値すべてを網羅しているので、このエラーは発生しない。
//    @Test
//    @ExpectedExceptionMessage("^テーブル識別値が見つかりません.*")
//    public void testConstructor_2() {
//        LOG.debug("異常系_記述子タグ相違");
//        
//        Section instance = new Section(TABLE_ID.OTHER_TABLE_IDS, this.testSection_OK.getData());
//    }
    @Test
    @ExpectedExceptionMessage("^セクション長フィールドの値が大きすぎます.*")
    public void testConstructor_3() {
        LOG.debug("異常系_セクション長フィールド値最大値超過");
        byte[] wrong02 = this.testSection_OK.getData();
        wrong02[1] = 0xf;
        wrong02[2] = 0xf;
        Section instance = new Section(wrong02);
    }

    /**
     * Test of getPreferedTableID method, of class Section.
     */
    @Test
    public void testGetTable_id_const() {
        LOG.debug("getTable_id_const");
        Section instance = this.testSection_OK;
        TABLE_ID expResult = TABLE_ID.SDT;
        TABLE_ID result = instance.getTable_id_const();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTable_id method, of class Section.
     */
    @Test
    public void testGetTable_id() {
        LOG.debug("getTable_id");
        Section instance = this.testSection_OK;
        int expResult = 0x42;
        int result = instance.getTable_id();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSection_syntax_indicator method, of class Section.
     */
    @Test
    public void testGetSection_syntax_indicator() {
        LOG.debug("getSection_syntax_indicator");
        Section instance = this.testSection_OK;
        int expResult = 1;
        int result = instance.getSection_syntax_indicator();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReservedFutureUse1 method, of class Section.
     */
    @Test
    public void testGetReservedFutureUse1() {
        LOG.debug("getReservedFutureUse1");
        Section instance = this.testSection_OK;
        int expResult = 1;
        int result = instance.getReservedFutureUse1();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReserved1 method, of class Section.
     */
    @Test
    public void testGetReserved1() {
        LOG.debug("getReserved1");
        Section instance = this.testSection_OK;
        int expResult = 3;
        int result = instance.getReserved1();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSectionLength method, of class Section.
     */
    @Test
    public void testGetSectionLength() {
        LOG.debug("getSectionLength");
        Section instance = this.testSection_OK;
        int expResult = 0x097;
        int result = instance.getSectionLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class Section.
     */
    @Test
    public void testGetSectionBody() throws DecoderException {
        LOG.debug("getSectionBody");
        Section instance = this.testSection_OK;
        byte[] expResult = Hex.decodeHex("7fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray());
        byte[] result = instance.getSectionBody().getData();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getTable_id_extension method, of class Section.
     */
    @Test
    public void testGetTable_id_extension() {
        LOG.debug("getTable_id_extension");
        Section instance = this.testSection_OK;
        int expResult = 0x7fe1;
        int result = instance.getTable_id_extension();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCRC method, of class Section.
     */
    @Test
    public void testGetCRC() throws DecoderException {
        LOG.debug("getCRC");
        Section instance = this.testSection_OK;
        int expResult = ByteConverter.bytesToInt(Hex.decodeHex("722fa2b5".toCharArray()));
        LOG.debug(expResult);
        int result = instance.getCRC();
        LOG.debug(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkCRC method, of class Section.
     */
    @Test
    public void testCheckCRC() {
        LOG.debug("checkCRC");
        Section instance = this.testSection_OK;
        Section.CRC_STATUS expResult = Section.CRC_STATUS.NO_CRC_ERROR;
        Section.CRC_STATUS result = instance.checkCRC();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Section.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        Section instance = this.testSection_OK;
        LOG.debug(instance);
    }

    /**
     * Test of hashCode method, of class Section.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testHashCode() throws DecoderException {
        LOG.debug("hashCode");
        Section instance1 = this.testSection_OK;
        Section instance2 = this.testSection_OK;
        assertTrue(instance1.hashCode() == instance2.hashCode());

        byte[] b = ArrayUtils.remove(new TestPacket_SDT().getTarget200().getPayload(), 0);
        Section instance3 = new Section(b);

//        System.out.println(instance1);
//        System.out.println(instance3);
        assertTrue(instance1.hashCode() != instance3.hashCode());
    }

    /**
     * Test of equals method, of class Section.
     */
    @Test
    public void testEquals() {
        LOG.debug("equals_ok");
        EqualsChecker<Section> ec = new EqualsChecker<>();
        Section instance1 = this.testSection_OK;
        Section instance2 = this.testSection_OK;
        Section instance3 = this.testSection_OK;
        boolean result = ec.check_same(instance1, instance1, instance1);
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class Section.
     */
    @Test
    public void testEquals_ng() throws DecoderException {
        LOG.debug("equals_ng");
        Section instance1 = this.testSection_OK;

        byte[] b = ArrayUtils.remove(new TestPacket_SDT().getTarget200().getPayload(), 0);
        Section instance2 = new Section(b);

        boolean result = instance1.equals(instance2);
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of getData method, of class Section.
     */
    @Test
    public void testGetData() throws DecoderException {
        LOG.debug("getData");
        Section instance = testSection_OK;
        byte[] expResult = Hex.decodeHex("42f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5".toCharArray());
        byte[] result = instance.getData();
//        LOG.debug(Hex.encodeHexString(expResult));
//        LOG.debug(Hex.encodeHexString(result));
        assertArrayEquals(expResult, result);

    }

}
