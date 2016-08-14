/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.descriptor.contentdescriptor;

import libepg.util.bytearray.ByteConverter;
import java.text.MessageFormat;
import java.util.Objects;
import util.bytearray.ByteDataBlock;

/**
 * ジャンルコード(2バイト)
 */
public final class Nibble {

    private final ByteDataBlock data;

    Nibble(byte[] data) {
        this.data = new ByteDataBlock(data);
        int lengthFromData = this.data.getData().length;
        if (lengthFromData != 2) {
            MessageFormat msg1 = new MessageFormat("渡されたデータの長さが想定と異なります。データから算出した配列長={0} 想定される配列長={1}");
            Object[] parameters1 = {lengthFromData, 2};
            throw new IllegalArgumentException(msg1.format(parameters1));
        }
    }

    /**
     * バイト長配列を取得する。
     *
     * @return 参照。
     */
    public synchronized byte[] getData() {
        return data.getData();
    }

    /**
     * content_nibble_level_1（ジャンル1）：この4 ビットのフィールドは、コンテント識別の
     * 第一段階分類を表す。符号化については別途規定する。（付録H 参照）
     *
     * @return
     */
    public synchronized NIBBLE_LEVEL_1 getContent_nibble_level_1() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[0]);
        temp = temp >>> 4;
        return NIBBLE_LEVEL_1.reverseLookUp(temp);
    }

    /**
     * content_nibble_level_2（ジャンル2）：この4 ビットのフィールドは、コンテント識別の
     * 第二段階分類を表す。符号化については別途規定する。（付録H 参照）
     *
     * @return
     */
    public synchronized NIBBLE_LEVEL_2 getContent_nibble_level_2() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[0]);
        temp = temp & 0xf;
        return NIBBLE_LEVEL_2.reverseLookUp(this.getContent_nibble_level_1().getCode(), temp);
    }

    /**
     * user_nibble（ユーザジャンル）：この4 ビットのフィールドは、放送事業者により定義される。
     *
     * @return
     */
    public synchronized int getUser_nibble1() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[1]);
        temp = temp >>> 4;
        return temp;
    }

    /**
     * user_nibble（ユーザジャンル）：この4 ビットのフィールドは、放送事業者により定義される。
     *
     * @return
     */
    public synchronized int getUser_nibble2() {
        int temp;
        temp = ByteConverter.byteToInt(this.getData()[1]);
        temp = temp & 0xf;
        return temp;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.data);
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
        final Nibble other = (Nibble) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    private static final MessageFormat NIBBLE_DESC = new MessageFormat(
            "バイト列 = {0}\n"
            + "ジャンル1 = {1}\n"
            + "ジャンル2 = {2}\n"
            + "ユーザジャンル1 = {3}\n"
            + "ユーザジャンル2 = {4}\n"
    );

    @Override
    public String toString() {
        Object[] parameters = {this.data, this.getContent_nibble_level_1(), this.getContent_nibble_level_2(), this.getUser_nibble1(), this.getUser_nibble2()};
        return NIBBLE_DESC.format(parameters);
    }

}
