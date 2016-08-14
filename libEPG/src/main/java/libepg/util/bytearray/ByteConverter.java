/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.util.bytearray;

/**
 *
 * @author normal
 */
public class ByteConverter {

    private ByteConverter() {
    }

    /**
     * byte(8bit)のシフト演算は、内部でint(32bit)に変換されてから処理されるらしい。 このため、最上位（符号）ビットが 1 （負）
     * の場合は、byte のままシフト演算できないため、 数値変換した後で下位8bit以外を0にする。
     *
     * byte b = (byte)0x55 1010 1010 b = b >>> 1;
     *
     * は、右側の式 b >>> 1 が評価される際に、 b が int 型に変換され、 1111 1111 1111 1111 1111 1111
     * 1010 1010 となる。
     *
     * これを符号なし右シフトすると 0111 1111 1111 1111 1111 1111 1101 0101 となる。
     *
     * @param src 変換したいbyte型変数
     * @return 変換した数値(符号なしint)
     */
    public static int byteToInt(byte src) {
        return Byte.toUnsignedInt(src);
    }

    /**
     * byte(8bit)配列を符号なしintに変換する。使えるのは4バイトまで。
     *
     * @param src 変換したいbyte型変数
     * @return 変換した数値。
     */
    public static int bytesToInt(byte[] src) throws IndexOutOfBoundsException {
        int temp = 0;
        if (src.length > 4) {
            throw new IndexOutOfBoundsException("配列が大きすぎます。4バイトを超える配列には対応していません。");
        }
        for (byte b : src) {
            temp = (temp << 8) + (b & 0xff);
        }
        return temp;
    }
}
