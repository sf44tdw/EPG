/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.bytearray;

import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;

/**
 * バイト長配列の保持を行う。
 *
 * @author normal
 */
public final class ByteDataBlock {

    private final byte[] data;

    public ByteDataBlock(byte[] data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException("保管すべきデータが指定されていません。");
        }
        //ディープコピーする。
        this.data = Arrays.copyOf(data, data.length);
    }

    /**
     * このクラスが保持しているバイト長配列を取得する。
     *
     * @return このクラスが保持している配列のコピー
     */
    public synchronized final byte[] getData() {
        return Arrays.copyOf(this.data, this.data.length);
    }

    /**
     * このクラスが保持しているバイト長配列の長さを取得する。
     *
     * @return このクラスが保持している配列の長さ
     */
    public synchronized int length() {
        return this.data.length;
    }

    /**
     * @return このクラスが保持している配列の16進ダンプ
     */
    @Override
    public String toString() {
        return Hex.encodeHexString(this.data);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Arrays.hashCode(this.data);
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
        final ByteDataBlock other = (ByteDataBlock) obj;
        if (!Arrays.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

}
