/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtool;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class TestUtilTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    public TestUtilTest() {
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
     * Test of methodName method, of class TestUtil.
     */
    @Test
    public void testMethodName() {
        LOG.info("");
        String expResult = "testMethodName";
        String result = TestUtil.methodName();
        assertEquals(expResult, result);
    }

    /**
     * Test of dumpCollection method, of class TestUtil.
     */
    @Test
    public void testDumpCollection() {
        LOG.info("");
        ArrayList<Set<Integer>> target = new ArrayList<>();

        Set<Integer> x1 = new HashSet<>();
        Set<Integer> x2 = new HashSet<>();

        target.add(x1);
        target.add(x2);

        x1.add(10);
        x2.add(20);
        x2.add(30);

        LOG.info(TestUtil.dumpCollection(target));
    }
}
