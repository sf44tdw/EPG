/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testtool.testrule.regexmessagerule;

import java.lang.invoke.MethodHandles;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;

/**
 *
 * @author normal
 */
public class ExpectedExceptionRuleTest {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    public ExpectedExceptionRuleTest() {
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
     * Test of apply method, of class ExpectedExceptionRule.
     */
    @Test
    @ExpectedExceptionMessage("^aaabbbccc.*$")
    public void testApply0() {
        LOG.info("");
        throw new IllegalArgumentException("aaabbbcccdddeeefff");
    }

    /**
     * Test of apply method, of class ExpectedExceptionRule.
     */
    @Test
    @ExpectedExceptionMessage("^.*aaabbbccc.*$")
    public void testApply1() {
        LOG.info("");
        throw new IllegalArgumentException("cccdddeeefffaaabbbcccyyy");
    }

//    /**
//     * これをやると例外はTestアノテーションにとられてしまうので検知できない。
//     */
//    @Test(expected=IllegalArgumentException.class)
//    @ExpectedExceptionMessage("^aaabbbccc.*$")
//    public void testApply_error() {
//        LOG.info("");
//        throw new IllegalArgumentException("cccdddeeefffaaabbbccc");
//    }
}
