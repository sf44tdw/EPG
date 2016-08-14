/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.common.descriptor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import java.util.Arrays;
import libepg.epg.section.descriptor.DESCRIPTOR_TAG;
import libepg.epg.section.descriptor.Descriptor;
import libepg.util.LoggerFactory;



/**
 *
 * @author normal
 */
public class Descriptors {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Descriptors.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    //短形式イベント記述子
    private final String SHORT_EVENT_DESCRIPTOR_STRING = "4dbb6a706e3d1b7ceff3eff3d1c3b3ed1b7ea11b7cade3e91b7dc8e21b7cef213cebc9212148741b7dd33d50bb1b7ea13f4d35241b7cbdf3b01b242a3b1b7dfad6faea791b7ceff3eff31b7dbfc1ac2122b3c9e248564148c73f4d3524ce324ef2335ab7af3e5232702123364acf2156e6adc0ebdece1b7ce6ad1b7dc1e3f3c821572156a4c3b7e7cae9d8a4adc0e82157cac9212321561b7cc9ecdfd5a121261b7dc9213ccac3c41b7ea121571b7dceecc3b7213cac45503e6c1b7ea1";
    private final byte[] SHORT_EVENT_DESCRIPTOR_BYTE;

    //拡張形式イベント記述子
    private final String EXTENDED_EVENT_DESCRIPTOR_STRING = "4eec026a706ee6084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca00";
//    private final String EXTENDED_EVENT_DESCRIPTOR_STRING = "4efb026a706ee6084856414846624d46dc3c673f4d3878cf3f373f4d4c2132683248fe4c7045673877b5f3214a0e32360f214bfa42673358423436483865fd306c4559cf42673c6a0e49540f346b3648cb3d223f26b7bfacfd3b52c9e2ce3a22abe9ce4c34c0c3bf4c2132683248ce463bf2447ce1adecba0e330f472fc742603f26b7bffa48603d77ce1b7cc7d3e5f91972386532211b7db7b7bfceacfb0e5745420f4c213268fcce40243326fab3b33f74472fc70e5745421b6f5e732cce352448ac0f3c21213945503e6cb7fd4c2132683248cbcaeb1b6f41637339e20f392dacc3bffae8a6e4af3925adca0f0e4e484b451d461d6c310f456c357e";
    private final byte[] EXTENDED_EVENT_DESCRIPTOR_BYTE;

    //サービス記述子
    private final String SERVICE_DESCRIPTOR_STRING = "481201000f0e4e484b451d461d6c310f456c357e";
    private final byte[] SERVICE_DESCRIPTOR_BYTE;

    //デジタルコピー制御記述子(使わない)
    private final String DIGITAL_COPY_CONTROL_DESCRIPTOR_STRING = "c10184";
    private final byte[] DIGITAL_COPY_CONTROL_DESCRIPTOR_BYTE;

    ////ロゴ伝送記述子
    private final String LOGO_TRANSMISSION_DESCRIPTOR_STRING = "cf0701fe08f0010408";
    private final byte[] LOGO_TRANSMISSION_DESCRIPTOR_BYTE;

    //コンテント記述子
    private final String CONTENT_DESCRIPTOR_STRING = "540682ff81ffa0ff";
    private final byte[] CONTENT_DESCRIPTOR_BYTE;

//強制呼び出し
    public static final Descriptor init(byte[] data) throws InvocationTargetException {
        try {
            Object[] args = {data};
            Class<?>[] params = {byte[].class};
            Constructor<Descriptor> constructor = Descriptor.class.getDeclaredConstructor(params);
            constructor.setAccessible(true);
            Descriptor target = constructor.newInstance(args);
            return target;
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
            LOG.fatal(ex);
        }
        return null;
    }

    public Descriptors() throws DecoderException {
        this.SERVICE_DESCRIPTOR_BYTE = Hex.decodeHex(this.SERVICE_DESCRIPTOR_STRING.toCharArray());
        this.DIGITAL_COPY_CONTROL_DESCRIPTOR_BYTE = Hex.decodeHex(this.DIGITAL_COPY_CONTROL_DESCRIPTOR_STRING.toCharArray());
        this.LOGO_TRANSMISSION_DESCRIPTOR_BYTE = Hex.decodeHex(this.LOGO_TRANSMISSION_DESCRIPTOR_STRING.toCharArray());
        this.SHORT_EVENT_DESCRIPTOR_BYTE = Hex.decodeHex(this.SHORT_EVENT_DESCRIPTOR_STRING.toCharArray());
        this.EXTENDED_EVENT_DESCRIPTOR_BYTE = Hex.decodeHex(this.EXTENDED_EVENT_DESCRIPTOR_STRING.toCharArray());
        this.CONTENT_DESCRIPTOR_BYTE = Hex.decodeHex(this.CONTENT_DESCRIPTOR_STRING.toCharArray());

    }

    public Descriptor getSERVICE_DESCRIPTOR() {
        try {
            return init(this.SERVICE_DESCRIPTOR_BYTE);
        } catch (InvocationTargetException ex) {
            LOG.fatal(ex.getCause());
        }
        return null;
    }

    public Descriptor getSHORT_EVENT_DESCRIPTOR() {
        try {
            return init(this.SERVICE_DESCRIPTOR_BYTE);
        } catch (InvocationTargetException ex) {
            LOG.fatal(ex.getCause());
        }
        return null;
    }

    public Descriptor getEXTENDED_EVENT_DESCRIPTOR() {
        try {
            return init(this.EXTENDED_EVENT_DESCRIPTOR_BYTE);
        } catch (InvocationTargetException ex) {
            LOG.fatal(ex.getCause());
        }
        return null;
    }

    public Descriptor getDIGITAL_COPY_CONTROL_DESCRIPTOR() {
        try {
            return init(this.DIGITAL_COPY_CONTROL_DESCRIPTOR_BYTE);
        } catch (InvocationTargetException ex) {
            LOG.fatal(ex.getCause());
        }
        return null;
    }

    public Descriptor getLOGO_TRANSMISSION_DESCRIPTOR() {
        try {
            return init(this.LOGO_TRANSMISSION_DESCRIPTOR_BYTE);
        } catch (InvocationTargetException ex) {
            LOG.fatal(ex.getCause());
        }
        return null;
    }

    public Descriptor getCONTENT_DESCRIPTOR() {
        try {
            return init(this.CONTENT_DESCRIPTOR_BYTE);
        } catch (InvocationTargetException ex) {
            LOG.fatal(ex.getCause());
        }
        return null;
    }

    private byte[] copyIt(byte[] src) {
        return Arrays.copyOf(src, src.length);
    }

    public byte[] getSHORT_EVENT_DESCRIPTOR_BYTE() {
        return copyIt(SHORT_EVENT_DESCRIPTOR_BYTE);
    }

    public byte[] getEXTENDED_EVENT_DESCRIPTOR_BYTE() {
        return copyIt(EXTENDED_EVENT_DESCRIPTOR_BYTE);
    }

    public byte[] getSERVICE_DESCRIPTOR_BYTE() {
        return copyIt(SERVICE_DESCRIPTOR_BYTE);
    }

    public byte[] getDIGITAL_COPY_CONTROL_DESCRIPTOR_BYTE() {
        return copyIt(DIGITAL_COPY_CONTROL_DESCRIPTOR_BYTE);
    }

    public byte[] getLOGO_TRANSMISSION_DESCRIPTOR_BYTE() {
        return copyIt(LOGO_TRANSMISSION_DESCRIPTOR_BYTE);
    }

    public byte[] getCONTENT_DESCRIPTOR_BYTE() {
        return copyIt(CONTENT_DESCRIPTOR_BYTE);
    }

}
