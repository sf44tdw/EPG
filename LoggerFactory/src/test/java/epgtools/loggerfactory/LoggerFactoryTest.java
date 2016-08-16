/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epgtools.loggerfactory;

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
public class LoggerFactoryTest {

    private static final Log MAIN_LOG = LogFactory.getLog(LoggerFactoryTest.class);

    public LoggerFactoryTest() {
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
     * Test of getLOG method, of class LoggerFactory.
     */
    @Test
    public void testGetLOG() {
        MAIN_LOG.info("getLOG");
        LoggerFactory instance = new LoggerFactory(this.getClass(), true);
        Log expResult = LogFactory.getLog(this.getClass());
        Log result = instance.getLOG();

        //とりあえず両方出力されれば問題なし。
        expResult.info("Message from expResult.");
        result.info("Message from result.");

        assertEquals(expResult, result);
    }

    /**
     * Test of isOutLog method, of class LoggerFactory.
     */
    @Test
    public void testIsOutLog() {
        MAIN_LOG.info("isOutLog");
        LoggerFactory instance1 = new LoggerFactory(this.getClass(), true);
        boolean expResult1 = true;
        boolean result1 = instance1.isOutLog();
        Log l1 = instance1.getLOG();
        assertEquals(expResult1, result1);
        //メッセージが出力される。
        l1.info("Message from normal logger.");

        //何もしないダミーのロガーを返す。
        LoggerFactory instance2 = new LoggerFactory(this.getClass(), false);
        boolean expResult2 = false;
        boolean result2 = instance2.isOutLog();
        Log l2 = instance2.getLOG();
        assertEquals(expResult2, result2);
        //このメッセージは出力されない。
        l2.info("Message from dummy logger.");
    }

}
