/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
public class DescriptorsLoopTest_eit {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, DescriptorsLoop.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final DescriptorsLoop target1;
    private final DescriptorsLoop target2;
    private final DescriptorsLoop target3;
    private final DescriptorsLoop target4;
    private final DescriptorsLoop target5;
    private final DescriptorsLoop target6;
    private final DescriptorsLoop target7;
    private final DescriptorsLoop target8;

    List<DescriptorsLoop> ldl = new ArrayList<>();

    public DescriptorsLoopTest_eit() throws DecoderException {
        target1 = new DescriptorsLoop(Hex.decodeHex("4dbb6a706e3d1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea791b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea15006f1b3006a706ec409f203100f0f6f6a706ec70e0008300501136a706e006a706e005406a8ff54ff49ffc10184d609120067736100687361".toCharArray()));
        target2 = new DescriptorsLoop(Hex.decodeHex("4da56a706e331b7ca2cbe121211b7daab7eaabb8ea436e1b7ea8b1b4a921561b7cdbf3c81b7ea1bf1b7cb8b81b7dce1b7cd7eddd213cba21576da4c4e2d8f3cab3c8d0abeab7c6a4c62122a4a4abb2f3cab3c8b7ab3840efcaa41b7cb8b821232156d0d021221b7dcaf3c71b7cb8b81b7dc8376b3a27b7bfce1b7ebf21571b7dc84a39afabb8ea436ecb21221b7cd0d01b7dacaac9edafd9ad3b57a43d504f43f2386ceb1b7ea15006f1b3006a706ec409f203100f0f6f6a706e540270ffc10184d6091200676abb00686abb".toCharArray()));
        target3 = new DescriptorsLoop(Hex.decodeHex("4dad6a706e3f1b7ca2cbe12121b9ddf3b82126dcd62156d3adcbbfa6f31b7df2487eb7af1b7ea1215721561b7cb2a4ea213c1b7dce416a4b4021571b242a3b1b7dfadafad6693f3ca43324ce446ccba2eb3324446c45543b5421261b7cd3adcbbfa6f31b7dcb3d3be01b7cb9ddf3b82126dcd61b7dc843673456bfc1ac372bea392db2eb21221b7ce6213ce2e9b91b7dc7335ab7a4467c3e6ff24941a4bf1b7ccfa4c6f3b7e7f32126a2cbe11b7ea15006f1b3006a706ec40ff203100f0f6f6a706e467c4b5c386cc40df203110f0f2f656e673151386cc70e0008300501136a706e006a706e00540271ffc10184d6091200676abc00686abc".toCharArray()));
        target4 = new DescriptorsLoop(Hex.decodeHex("4da66a706e151b7ec2d31b7cd7ecdec3d72156d1f34e39212321578c1b7eb3376eb2b9467c4c6bb73b7e483e4a7c417721561b7cd1f34e39212321571b7dce4c254e4f2123357e4554495ccf467c4b5c306c1b7cd1f31b7df23f29d9eb434f30682122cabc38454554ce357e4554ac1b7ebf4c5a466e403232461b7dc84222323c4a664748ac357e4554ceaaa4b7a41b7cd1f31b7df23f29d9deafeabdce486b4c29f24335eb21235006f1b3006a706ec409f203100f0f6f6a706e540427ff87ffc10184d609120067798d0068798d".toCharArray()));
        target5 = new DescriptorsLoop(Hex.decodeHex("4db26a706e311b7cb0ec213cc8cda4c1e3213c1b7eb2ede9eeae21562148322b36621b7dce4267324f2149466e4a461b7ca2debef321577c466e4a461b7ca2debef3406e1b7dcbcf2122497d1b7eb41b7caded1b7dce4267324fac4363c83975ce1b7eb23f271b7dcb4a4cecbfdede1b7eb1b01b7caded304a3e654e2e1b7deceb49543b573544ca3e6c3d6aaca2eb21233877ce3243383ac7322b36623f27cb3531af1b7ccdb0ed406e1b7dce4064374a1b7ea15006f1b3006a706ec409f203100f0f6f6a706e540282ffc10184d6091200676fb600686fb6".toCharArray()));
        target6 = new DescriptorsLoop(Hex.decodeHex("4dca6a706e361b7ed4cfcbd9cf1b7cc7a3213cd71b7ea121564c254f471b7dce4b7c325a3640212140694d5521263149442e21571b242a3b1b7dfad68f343f335a3339c8b7c64c3e3962a440694d5521263149442e2123434f3068b0ebdfc73a4640381b7cd7edb8a7afc81b7dac3f4a395443661b7ea13d77402d1b7cd0213cc6f3c0213c1b7dcf4541457d375d473dc71b7ea14267335840381b7dcf1b7cb4df3d261b7da4c71b7ea1475135751b7df24d784d51b7bf3c633c543f4d3524ce1b7cb9ddc3c81b7de21b7ea15006f1b3006a706ec409f203100f0f6f6a706ec70e0008300501136a706e006a706e00540255ffc10184d6091200676ac000686ac0".toCharArray()));
        target7 = new DescriptorsLoop(Hex.decodeHex("4da46a706e1ecbc3ddf349344c3e3b3321564d53447d3b3321571b242a3b1b7dfad6faea814b4c3324463bce4d53447d3b331b7ea8b1b8b9b8eda939711b7dce45374133352d47304a2acb3b58446ab5ecebaa3256482af2335ab7df212235704267ca3250387df2e1b0ea21223b33443ad82123325c305049593b4ec8e23846d0eceb4d25487eca3b334d46ce3b4d3528e44f3ccb4d2fad3d50b940364e2ee23e52327021235006f1b3006a706ec409f203100f0f6f6a706ec70e0008300501136a706e006a706e00540682ff81ffa0ffc10184d6091200676ac200686ac2".toCharArray()));
        target8 = new DescriptorsLoop(Hex.decodeHex("4dc46a706e3b1b7cd7ecdfa2e0b7cdde21561b7eb0b0b7af1b7cb9aba4d5a9213ceb21571b7ebc1b7cecbf213cdcc3afb9b5a4ba1b7ebe1b242a3b1b7dfadafad6843f4d35241b7cb9d1a42126a2afb7e7f3b7ea213cba1b7eb2b33a6e4c5c21233a2332731b7dac1b7eb345594c5c1b7dc8caeb1b7cc0cba8eb2126afeca4b031691b7db8eb1b7cb8a7213ce0ba2126dcf3c91b7dac21224860ce3e653b4a1b7ecd1b7df24140a64666ce48483a6141483f25c841544064ca462ea4f2372bea392db2eb21235006f1b3006a706ec40ff203100f0f6f6a706e467c4b5c386cc40df203110f0f2f656e673151386cc70e0008300501136a706e006a706e00540260ffc10184d6091200676ac600686ac6".toCharArray()));

        ldl.add(target1);
        ldl.add(target2);
        ldl.add(target3);
        ldl.add(target4);
        ldl.add(target5);
        ldl.add(target6);
        ldl.add(target7);
        ldl.add(target8);

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
     * Test of toString method, of class DescriptorsLoop.
     */
    @Test
    public void testToString() {
        LOG.debug("toString");
        for (DescriptorsLoop x : this.ldl) {
            LOG.debug(x);
        }
    }

}
