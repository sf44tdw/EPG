/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.util.bytearray;


import libepg.util.LoggerFactory;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

/**
 *
 * @author normal
 */
public class ByteArraySplitterTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ByteArraySplitter.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    public ByteArraySplitterTest() {
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
     * Test of split method, of class ByteArraySplitter.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testSplit() throws DecoderException {
        LOG.debug("split");
        byte[] src = Hex.decodeHex("00112233445566778899aabbccddeeff".toCharArray());
        int size = 3;
        List<byte[]> expResult = new ArrayList<>();
        expResult.add(Hex.decodeHex("001122".toCharArray()));
        expResult.add(Hex.decodeHex("334455".toCharArray()));
        expResult.add(Hex.decodeHex("667788".toCharArray()));
        expResult.add(Hex.decodeHex("99aabb".toCharArray()));
        expResult.add(Hex.decodeHex("ccddee".toCharArray()));
        expResult.add(Hex.decodeHex("ff0000".toCharArray()));
        List result = ByteArraySplitter.split(src, size);

        Iterator<byte[]> it_result = result.iterator();
        Iterator<byte[]> it_expResult = expResult.iterator();
        while (it_result.hasNext() && it_expResult.hasNext()) {
            StringBuilder s = new StringBuilder();
            byte[] res = it_result.next();
            byte[] expRes = it_expResult.next();
            if (Arrays.equals(res, expRes) == false) {
                fail("リストの内容が想定と違います。");
            } else {
                s.append(Hex.encodeHexString(expRes));
                s.append(" = ");
                s.append(Hex.encodeHexString(res));
                LOG.debug(s.toString());
            }
        }
    }

    /**
     * Test of splitByLengthField method, of class ByteArraySplitter.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testSplitByLengthField_3args() throws DecoderException {
        LOG.debug("splitByLengthField");
        byte[] src = Hex.decodeHex("0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray());
        int lengthFieldPosition = 4;
        int lengthFieldLength = 2;
        List<byte[]> expResult = new ArrayList<>();
        expResult.add(Hex.decodeHex("0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408".toCharArray()));
        expResult.add(Hex.decodeHex("0409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08".toCharArray()));
        expResult.add(Hex.decodeHex("040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe08".toCharArray()));
        expResult.add(Hex.decodeHex("0588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray()));
        List<byte[]> result = ByteArraySplitter.splitByLengthField(src, lengthFieldPosition, lengthFieldLength);//今回たまたま余計に混じっている4ビットがすべて0なので、長さフィールドの処理はいらない。

        Iterator<byte[]> it_result = result.iterator();
        Iterator<byte[]> it_expResult = expResult.iterator();
        while (it_result.hasNext() && it_expResult.hasNext()) {
            StringBuilder s = new StringBuilder();
            byte[] res = it_result.next();
            byte[] expRes = it_expResult.next();
            if (Arrays.equals(res, expRes) == false) {
                fail("リストの内容が想定と違います。");
            } else {
                s.append(Hex.encodeHexString(expRes));
                s.append(" = ");
                s.append(Hex.encodeHexString(res));
                LOG.debug(s.toString());
            }
        }
    }

    /**
     * Test of splitByLengthField method, of class ByteArraySplitter.
     *
     * @throws org.apache.commons.codec.DecoderException
     */
    @Test
    public void testSplitByLengthField_4args() throws DecoderException {
        LOG.debug("splitByLengthField");
        byte[] src = Hex.decodeHex("0408f31020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray());
        int lengthFieldPosition = 4;
        int lengthFieldLength = 2;
        List<byte[]> expResult = new ArrayList<>();
        expResult.add(Hex.decodeHex("0408f31020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f0010408".toCharArray()));//余計な部分が混じっている。
        expResult.add(Hex.decodeHex("0409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08".toCharArray()));
        expResult.add(Hex.decodeHex("040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe08".toCharArray()));
        expResult.add(Hex.decodeHex("0588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32".toCharArray()));
        List<byte[]> result = ByteArraySplitter.splitByLengthField(src, lengthFieldPosition, lengthFieldLength, (x) -> x & 0x0FFF);

        Iterator<byte[]> it_result = result.iterator();
        Iterator<byte[]> it_expResult = expResult.iterator();
        while (it_result.hasNext() && it_expResult.hasNext()) {
            StringBuilder s = new StringBuilder();
            byte[] res = it_result.next();
            byte[] expRes = it_expResult.next();
            if (Arrays.equals(res, expRes) == false) {
                fail("リストの内容が想定と違います。");
            } else {
                s.append(Hex.encodeHexString(expRes));
                s.append(" = ");
                s.append(Hex.encodeHexString(res));
                LOG.debug(s.toString());
            }
        }
    }

}
