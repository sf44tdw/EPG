/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor;

import libepg.util.bytearray.ByteConverter;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.apache.commons.logging.Log;
import libepg.util.LoggerFactory;
import libepg.util.bytearray.ByteArraySplitter;
import libepg.util.bytearray.ByteDataBlock;

/**
 * 記述子ループを保管するクラス。
 *
 * @author normal
 */
public class DescriptorsLoop {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    protected static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, DescriptorsLoop.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final ByteDataBlock data;

    public DescriptorsLoop(byte[] data) {
        this.data = new ByteDataBlock(data);
    }

    /**
     * 記述子ループを取得する。
     *
     * @return
     */
    public synchronized byte[] getData() {
        return this.data.getData();
    }

    /**
     * 記述子のリストを取得する。
     * 記述子は記述子タグに従ってキャストされる。キャスト中に問題が発生したか、キャスト先が基底クラスの記述子は、基底クラスとして返す。
     *
     * @return 記述子リスト。
     */
    public synchronized List<Descriptor> getDescriptors_loopList() {
        List<byte[]> t = ByteArraySplitter.splitByLengthField(this.getData(), 2, 1);
        List<Descriptor> dest = new ArrayList<>();
        for (byte[] temp : t) {
            Descriptor desc = new Descriptor(temp);

            Descriptor target = null;
            try {
                Object[] args = {desc};
                Class<?>[] params = {Descriptor.class};
                Constructor<? extends Descriptor> constructor = desc.getDescriptor_tag_const().getDataType().getDeclaredConstructor(params);
                target = constructor.newInstance(args);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                LOG.fatal("キャスト中に問題が発生しました。問題の発生した記述子 = " + desc.toString(), ex);
                target = desc;
            } finally {
                dest.add(target);
            }

        }
        return Collections.unmodifiableList(dest);
    }

    private static final MessageFormat DESC = new MessageFormat(
            "バイト列 = {0}\n"
            + "記述子 = {1}\n"
    );

    @Override
    public String toString() {
        List<Descriptor> l = this.getDescriptors_loopList();
        StringBuilder s = new StringBuilder();
        for (Descriptor desc : l) {
            s.append(desc);
        }
        Object[] parameters = {this.data, s.toString()};
        return DESC.format(parameters);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DescriptorsLoop other = (DescriptorsLoop) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

}
