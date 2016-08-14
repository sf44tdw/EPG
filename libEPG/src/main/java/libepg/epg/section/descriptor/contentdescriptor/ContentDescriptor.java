package libepg.epg.section.descriptor.contentdescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import libepg.epg.section.descriptor.Descriptor;
import libepg.util.bytearray.ByteArraySplitter;

/**
 * コンテント記述子（Content descriptor） コンテント記述子はイベントのジャンルを示すものである。
 */
public class ContentDescriptor extends Descriptor {

    public ContentDescriptor(Descriptor discriptor) {
        super(discriptor);
    }

    /**
     * ジャンルリストを取得する。
     *
     * @return
     */
    public synchronized List<Nibble> getNibbles() {
        List<byte[]> t = ByteArraySplitter.split(this.getDescriptor_Body(), 2);
        List<Nibble> tn = new ArrayList<>();
        for (byte[] b : t) {
            tn.add(new Nibble(b));
        }
        return Collections.unmodifiableList(tn);
    }

    @Override
    public String toString() {
        List<Nibble> tn = this.getNibbles();
        StringBuilder s = new StringBuilder();
        for (Nibble nb : tn) {
            s.append(nb);
        }
        return "ジャンルコード = " +s.toString()+"\n";
    }

}
