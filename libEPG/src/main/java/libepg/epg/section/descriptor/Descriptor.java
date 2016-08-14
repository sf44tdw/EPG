/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor;

import libepg.util.bytearray.ByteConverter;
import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.Objects;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import libepg.util.LoggerFactory;
import util.bytearray.ByteDataBlock;

/**
 * 記述子(descriptor()の部分)の共通フィールド <br>
 *
 * @author normal
 */
public class Descriptor {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    protected static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Descriptor.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final ByteDataBlock data;
    private final DESCRIPTOR_TAG descriptorTag;

    /**
     * 基底クラスから生成する。具体的なフィールドを定義したサブクラスの生成で使用する。
     *
     * @param descriptor 記述子
     */
    public Descriptor(Descriptor descriptor) {

        this.descriptorTag = descriptor.descriptorTag;
        if (this.getClass() != this.descriptorTag.getDataType()) {
            MessageFormat msg1 = new MessageFormat("このクラスでは渡されたデータをパースできません。このクラスの型={0} データの型={1} データ={2}");
            Object[] parameters1 = {this.getClass(), this.descriptorTag.getDataType(), Hex.encodeHexString(descriptor.getData())};
            throw new IllegalArgumentException(msg1.format(parameters1));
        }
        this.data = descriptor.data;
    }

    /**
     * 記述子を受け取る。DescriptorsLoopがオブジェクトを生成する際に使う。
     *
     * @param data 記述子のデータ
     * @throws IllegalArgumentException
     * データに含まれる記述子タグがpreferedDescriptorTagで渡された定数に含まれていない場合。
     */
    Descriptor(byte[] data) {

        this.data = new ByteDataBlock(data);

        int lengthFromData = ByteConverter.byteToInt(this.data.getData()[1]) + 2;
        int lengthFromArray = this.getData().length;
        if (lengthFromData != lengthFromArray) {
            MessageFormat msg1 = new MessageFormat("渡されたデータの長さが想定と異なります。データから算出した配列長={0} 配列長={1} データ={2}");
            Object[] parameters1 = {lengthFromData, lengthFromArray, Hex.encodeHexString(this.data.getData())};
            throw new IllegalArgumentException(msg1.format(parameters1));
        }

        int tag=ByteConverter.byteToInt(this.data.getData()[0]);
        this.descriptorTag = DESCRIPTOR_TAG.reverseLookUp(tag);
        if (this.descriptorTag==null) {
            MessageFormat msg = new MessageFormat("記述子タグが見つかりません。タグの値={0} データ={1}");
            Object[] parameters = {Integer.toHexString(tag), Hex.encodeHexString(this.data.getData())};
            throw new IllegalArgumentException(msg.format(parameters));
        }
    }

    /**
     * バイト長配列を取得する。
     *
     * @return 参照。
     */
    public final synchronized byte[] getData() {
        return data.getData();
    }

    /**
     * @return コンストラクタで設定された記述子タグ定数
     */
    public final synchronized DESCRIPTOR_TAG getDescriptor_tag_const() {
        return this.descriptorTag;
    }

    /**
     * descriptor_tag（記述子タグ）<br>
     *
     * @return 記述子タグの値
     */
    public final synchronized int getDescriptor_tag() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[0]);
        return temp;
    }

    /**
     * descriptor_length（記述子長）：記述子長は8 ビットのフィールドで、このフィールドの
     * 直後に続く記述子のデータ部分の全バイト長を規定する。
     *
     * @return 記述子長
     */
    public final synchronized int getDescriptor_length() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[1]);
        return temp;
    }

    /**
     * 記述子のデータ部分(このクラスが管理するバイト配列の3バイト目から最後まで)を取得する。
     *
     * @return
     */
    public final synchronized byte[] getDescriptor_Body() {
        int length;
        length = this.getDescriptor_length();
        if (LOG.isTraceEnabled()) {
            LOG.trace("記述子長=" + length);
            LOG.trace("クラス管理下のバイト配列長-2=" + (this.data.length() - 2));
        }
        byte[] t = new byte[length];
        System.arraycopy(this.getData(), 2, t, 0, length);
        return t;
    }

    private static final MessageFormat DESC = new MessageFormat(
            "バイト列 = {0}\n"
            + "記述子タグ = {1}\n"
            + "記述子長 = {2}\n"
            + "記述子データ = {3}\n"
    );

    @Override
    public String toString() {
        Object[] parameters = {this.data.toString(),
            this.getDescriptor_tag_const(),
            this.getDescriptor_length(),
            Hex.encodeHexString(this.getDescriptor_Body())};
        return DESC.format(parameters);
    }

    /**
     * @return 保管している内容に基づくコード
     */
    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.data);
        hash = 83 * hash + Objects.hashCode(this.descriptorTag);
        return hash;
    }

    /**
     * @return 下記3つのすべてに当てはまればtrue<br>
     * 1.比較対象のオブジェクトがこのオブジェクトと同じクラスのインスタンスである。<br>
     * 2.比較対象のオブジェクトが保持しているバイト配列と、このオブジェクトが保持しているバイト配列が同じ内容である。<br>
     * 3.比較対象のオブジェクトの記述子タグ定数と、このオブジェクトの記述子タグ定数が同じ内容である。<br>
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Descriptor other = (Descriptor) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (this.descriptorTag != other.descriptorTag) {
            return false;
        }
        return true;
    }

}
