/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.sectionreconstructor;


import libepg.util.bytearray.ByteConverter;
import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import libepg.ts.packet.TsPacket;
import libepg.util.LoggerFactory;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;

/**
 * 渡されたTSパケットのペイロードから、ポインターフィールド(ペイロードの1バイト目)の内容に基づいてセクションの一部を取り出す。<br>
 *
 *
 * @author normal
 */
public final class PayLoadSplitter {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, PayLoadSplitter.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private TsPacket packet;

    public PayLoadSplitter() {
    }

    /**
     * @param packet パケット。
     * @throws IllegalArgumentException
     * パケットのアダプテーションフィールドコントロールがアダプテーションフィールドのみに設定されているか、不明な値の時。
     */
    public synchronized void setPacket(TsPacket packet) throws IllegalArgumentException {

        this.packet = packet;

        if ((this.packet.getAdaptation_field_control() == TsPacket.ADAPTATION_FIELD_CONTROL.ONLY_ADAPTATION_FIELD) || (this.packet.getAdaptation_field_control() == TsPacket.ADAPTATION_FIELD_CONTROL.RESERVED)) {
            MessageFormat msg2 = new MessageFormat("アダプテーションフィールドコントロールがアダプテーションフィールドのみに設定されているか、不明な値です。パケット={0}");
            Object[] parameters2 = {this.packet.toString()};
            throw new IllegalArgumentException(msg2.format(parameters2));
        }
    }

    public synchronized TsPacket getPacket() {
        return packet;
    }

    /**
     * ペイロードのどの部分が入っているか識別するキー
     */
    public enum PAYLOAD_PART_KEY {
        /**
         * ペイロードの2バイト目から最後までが入った要素
         */
        PAYLOAD_AFTER_2_BYTE(POINTER_FIELD_EXISTENCE.EXIST),
        /**
         * ペイロードの2バイト目から数えてポインタフィールドの値バイトまでが入った要素
         */
        PREV_POINTER(POINTER_FIELD_EXISTENCE.EXIST),
        /**
         * ペイロードのポインタフィールドの値バイトからペイロードの最後までが入った要素
         */
        NEXT_POINTER(POINTER_FIELD_EXISTENCE.EXIST),
        /**
         * ペイロードの内容全部
         */
        ALL_PAYLOAD(POINTER_FIELD_EXISTENCE.NOT_EXIST);

        /**
         * ペイロードにポインタフィールドが存在していたか
         */
        public enum POINTER_FIELD_EXISTENCE {
            /**
             * ペイロードにポインタフィールドがあった
             */
            EXIST(true),
            /**
             * ペイロードにポインタフィールドがなかった
             */
            NOT_EXIST(false);

            private final boolean value;

            private POINTER_FIELD_EXISTENCE(boolean value) {
                this.value = value;
            }

            public boolean isValue() {
                return value;
            }

            @Override
            public String toString() {
                MessageFormat msg = new MessageFormat("{0} (ポインタフィールドが存在していたか={1})");
                Object[] parameters = {super.toString(), this.isValue()};
                return msg.format(parameters);
            }
        }
        private final POINTER_FIELD_EXISTENCE pointer_field_existence;

        private PAYLOAD_PART_KEY(POINTER_FIELD_EXISTENCE pointer_field_existence) {
            this.pointer_field_existence = pointer_field_existence;
        }

        /**
         * ペイロードにポインタフィールドが存在していたか
         *
         * @return
         */
        public POINTER_FIELD_EXISTENCE getPointer_field_existence() {
            return pointer_field_existence;
        }

        @Override
        public String toString() {
            MessageFormat msg = new MessageFormat("{0}(ポインタフィールド有無={1})");
            Object[] parameters = {super.toString(), this.getPointer_field_existence()};
            return msg.format(parameters);
        }
    }

    /**
     * 1:payload_unit_start_indicatorが1、かつポインタフィールドが0であれば、ペイロードの2バイト目から数えて最後までが入った要素1個のマップ<br>
     * 2:payload_unit_start_indicatorが1、かつポインタフィールドが0以外であれば、<br>
     * ペイロードの2バイト目から数えてポインタフィールドの値バイトまでが入った要素と、<br>
     * ペイロードのポインタフィールドの値バイト+1からペイロードの終端までが入った要素を含む要素2個のマップ<br>
     * 3:payload_unit_start_indicatorが0であれば、ペイロードが入った要素1個のマップ
     *
     * @return 上記のとおり <br>
     */
    public synchronized Map<PAYLOAD_PART_KEY, byte[]> getSplittedPayLoad() {

        if (LOG.isTraceEnabled()) {
            LOG.trace(this.packet);
        }

        Map<PAYLOAD_PART_KEY, byte[]> temp = new HashMap<>();
        EXEC:
        {
            if (this.packet.getPayload_unit_start_indicator() == TsPacket.PAYLOAD_UNIT_START_INDICATOR.NOT_START_POINT) {
                LOG.trace("ペイロードにセクションの先端なし。");
                temp.put(PAYLOAD_PART_KEY.ALL_PAYLOAD, this.packet.getPayload());
                break EXEC;
            }
            if (this.packet.getPayload_unit_start_indicator() == TsPacket.PAYLOAD_UNIT_START_INDICATOR.START_PES_OR_START_SECTION) {
                LOG.trace("ペイロードにセクションの先端あり。");
                int pointerField = ByteConverter.byteToInt(this.packet.getPayload()[0]);
                int tempLength = this.packet.getPayload().length - 1;
                byte[] tempArray = new byte[tempLength];
                System.arraycopy(this.packet.getPayload(), 1, tempArray, 0, tempArray.length);
                if (LOG.isTraceEnabled()) {
                    MessageFormat msg1 = new MessageFormat("ポインタフィールド={0} ペイロード={1} ペイロード2バイト目以降={2}");
                    Object[] parameters1 = {pointerField, Hex.encodeHexString(this.packet.getPayload()), Hex.encodeHexString(tempArray)};
                    LOG.trace(msg1.format(parameters1));
                }
                if (pointerField == 0) {
                    temp.put(PAYLOAD_PART_KEY.PAYLOAD_AFTER_2_BYTE, tempArray);
                    break EXEC;
                } else {
                    byte[] prev = new byte[pointerField];
                    System.arraycopy(tempArray, 0, prev, 0, prev.length);
                    temp.put(PAYLOAD_PART_KEY.PREV_POINTER, prev);
                    byte[] next = new byte[tempArray.length - pointerField];
                    System.arraycopy(tempArray, pointerField, next, 0, next.length);
                    temp.put(PAYLOAD_PART_KEY.NEXT_POINTER, next);
                    break EXEC;
                }
            }
        }

        dumpMap(temp);

        return Collections.unmodifiableMap(temp);
    }

    private synchronized void dumpMap(Map<PayLoadSplitter.PAYLOAD_PART_KEY, byte[]> t_map) {
        if (LOG.isTraceEnabled() && PayLoadSplitter.CLASS_LOG_OUTPUT_MODE == true) {
            StringBuilder s = new StringBuilder();
            s.append("分割マップの内容。[");
            MessageFormat msg1 = new MessageFormat("キー={0} 配列={1}");
            Set<PayLoadSplitter.PAYLOAD_PART_KEY> keys = t_map.keySet();
            for (PayLoadSplitter.PAYLOAD_PART_KEY key : keys) {
                Object[] parameters1 = {key, Hex.encodeHexString(t_map.get(key))};
                s.append(msg1.format(parameters1));
            }
            s.append("]");
            LOG.trace(s.toString());
        }
    }

}
