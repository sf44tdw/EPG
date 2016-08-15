/*
 * Copyright (C) 2016 normal
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package libepg.util.bytearray;

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
