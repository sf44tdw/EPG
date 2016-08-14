/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtool;

import java.lang.invoke.MethodHandles;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class EqualsCheckerTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    public EqualsCheckerTest() {
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

    @Rule
    public ExpectedExceptionRule rule = new ExpectedExceptionRule();

    /**
     * Test of check method, of class EqualsChecker.
     */
    @Test
    public void testCheck0() {
        LOG.info("");
        String target1 = "a";
        String target2 = "a";
        String target3 = "a";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean expResult = true;
        boolean result = instance.check(target1, target2, target3);
        assertEquals(expResult, result);
    }

    /**
     * Test of check method, of class EqualsChecker.
     */
    @Test
    public void testCheck1() {
        LOG.info("");
        String target1 = "a";
        String target2 = "b";
        String target3 = "c";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean expResult = false;
        boolean result = instance.check(target1, target2, target3);
        assertEquals(expResult, result);
    }

    /**
     * Test of check method, of class EqualsChecker.
     */
    @Test
    public void testCheck2() {
        LOG.info("");
        String target1 = "a";
        String target2 = "a";
        String target3 = "c";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean expResult = false;
        boolean result = instance.check(target1, target2, target3);
        assertEquals(expResult, result);

    }

    @Test
    @ExpectedExceptionMessage("^.*nullのセットは禁止.*$")
    public void testCheck3() {
        LOG.info("");
        String target1 = "a";
        String target2 = null;
        String target3 = "c";
        EqualsChecker<String> instance = new EqualsChecker<>();
        boolean expResult = true;
        boolean result = instance.check(target1, target2, target3);
    }
}
