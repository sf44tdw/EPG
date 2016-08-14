/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.sdt;

import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import libepg.epg.section.Section;
import libepg.epg.section.body.servicedescriptiontable.ServiceDescriptionTableBody;
import libepg.epg.section.body.servicedescriptiontable.ServiceDescriptionTableRepeatingPart;
import libepg.util.LoggerFactory;

/*
想定
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
パディング:ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff

 */
/**
 *
 * @author normal
 */
public class ServiceDescriptionTableBodyTest {

    private final ServiceDescriptionTableBody sdth;
    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ServiceDescriptionTableBodyTest.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    public ServiceDescriptionTableBodyTest() throws DecoderException {

        byte[] b = Hex.decodeHex("42f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray());

        this.sdth = new ServiceDescriptionTableBody(new Section( b).
                getSectionBody()
        );
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
     * Test of getTransport_stream_id method, of class
     * ServiceDescriptionTableBody.
     */
    @Test
    public void testGetTransport_stream_id() {
        LOG.debug("getTransport_stream_id");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 0x7fe1;
        int result = instance.getTransport_stream_id();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReserved2 method, of class ServiceDescriptionTableBody.
     */
    @Test
    public void testGetReserved2() {
        LOG.debug("getReserved2");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 3;
        int result = instance.getReserved2();
        assertEquals(expResult, result);
    }

    /**
     * Test of getVersion_number method, of class ServiceDescriptionTableBody.
     */
    @Test
    public void testGetVersion_number() {
        LOG.debug("getVersion_number");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 8;
        int result = instance.getVersion_number();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCurrent_next_indicator method, of class
     * ServiceDescriptionTableBody.
     */
    @Test
    public void testGetCurrent_next_indicator() {
        LOG.debug("getCurrent_next_indicator");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 1;
        int result = instance.getCurrent_next_indicator();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSection_number method, of class ServiceDescriptionTableBody.
     */
    @Test
    public void testGetSection_number() {
        LOG.debug("getSection_number");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 0;
        int result = instance.getSection_number();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLast_section_number method, of class
     * ServiceDescriptionTableBody.
     */
    @Test
    public void testGetLast_section_number() {
        LOG.debug("getLast_section_number");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 0;
        int result = instance.getLast_section_number();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOriginal_network_id method, of class
     * ServiceDescriptionTableBody.
     */
    @Test
    public void testGetOriginal_network_id() {
        LOG.debug("getOriginal_network_id");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 0x7fe1;
        int result = instance.getOriginal_network_id();
        assertEquals(expResult, result);
    }

    /**
     * Test of getReserved_future_use2 method, of class
     * ServiceDescriptionTableBody.
     */
    @Test
    public void testGetReserved_future_use2() {
        LOG.debug("getReserved_future_use2");
        ServiceDescriptionTableBody instance = this.sdth;
        int expResult = 0xff;
        int result = instance.getReserved_future_use2();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRepeatingPart method, of class ServiceDescriptionTableBody.
     */
    @Test
    public void testGetSDTBody() throws DecoderException {
        LOG.debug("getSDTBody");
        ServiceDescriptionTableBody instance = this.sdth;
        byte[] expResult = Hex.decodeHex("0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray());
        byte[] result = instance.getRepeatingPart();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of getSDTRepeatingPartList method, of class
     * ServiceDescriptionTableBody.
     */
    @Test
    public void testGetSDTBodyList() throws DecoderException, Throwable {
        LOG.debug("getSDTBodyList");

        ServiceDescriptionTableRepeatingPart expResult1 = ClassGetter.init(Hex.decodeHex("0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408".toCharArray()));
        ServiceDescriptionTableRepeatingPart expResult2 = ClassGetter.init(Hex.decodeHex("0409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08".toCharArray()));
        ServiceDescriptionTableRepeatingPart expResult3 = ClassGetter.init(Hex.decodeHex("040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe08".toCharArray()));
        ServiceDescriptionTableRepeatingPart expResult4 = ClassGetter.init(Hex.decodeHex("0588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray()));

        ServiceDescriptionTableBody instance = this.sdth;
        List<ServiceDescriptionTableRepeatingPart> result = instance.getSDTRepeatingPartList();
        assertEquals(true, result.contains(expResult1));
        assertEquals(true, result.contains(expResult2));
        assertEquals(true, result.contains(expResult3));
        assertEquals(true, result.contains(expResult4));

    }

    /**
     * Test of toString method, of class ServiceDescriptionTableBody.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        ServiceDescriptionTableBody instance = this.sdth;
        LOG.debug(instance.toString());

    }
}
