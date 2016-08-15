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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import libepg.util.LoggerFactory;

/**
 *
 * @author normal
 */
public final class ByteArraySplitter {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ByteArraySplitter.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private ByteArraySplitter() {
    }

    /**
     * 長さフィールドを含むバイト列の集団を、そのバイト列に含まれる長さフィールドに従って分割する。半端な部分は0で埋める。<br>
     * 1.与えられる配列の先頭は、長さフィールドを含むバイト列の先頭であることを前提とする<br>
     * 2.長さフィールドを含むバイト列の長さフィールドには、長さフィールドを含むバイト列の、長さフィールド直後から最後までの長さが入っているものとする<br>
     *
     *
     * @author normal
     * @param src 分割処理対象
     * @param lengthFieldPosition
     * 長さフィールド開始位置(分割後の配列の先頭から何バイト目以降が長さフィールドの開始位置を含んでいるか)<br>
     * @param lengthFieldLength 長さフィールドが持つバイト数
     * @param preProcessor 長さ項目のビット数が8の倍数ではない場合に、余計な項目を除去する条件式。
     * @return 分割されたバイト列
     *
     * 下記のように分割される<br>
     * X=長さフィールドの前にある部分<br>
     * Y=長さフィールド<br>
     * Z=長さフィールドの後にある部分。<br>
     * 渡された配列=XXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZXXXXYYZZZZZZZ<br>
     * 分割された配列1=XXXXYYZZZZZZ<br>
     * 分割された配列2=XXXXYYZZZZZZ<br>
     * 分割された配列3=XXXXYYZZZZZZ<br>
     * 分割された配列4=XXXXYYZZZZZZ<br>
     * 分割された配列5=XXXXYYZZZZZZ<br>
     * 分割された配列6=XXXXYYZZZZZZ<br>
     * 分割された配列7=XXXXYYZZZZZZ<br>
     * 分割された配列8=XXXXYYZZZZZZ<br>
     * 分割された配列9=XXXXYYZZZZZZZ<br>
     */
    public static synchronized List<byte[]> splitByLengthField(byte[] src, int lengthFieldPosition, int lengthFieldLength, UnaryOperator<Integer> preProcessor) {

        UnaryOperator<Integer> preProcessor_t;
        if (preProcessor == null) {
            throw new NullPointerException("長さ項目の前処理が指定されていません。");
        } else {
            preProcessor_t = preProcessor;
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("データ = " + Hex.encodeHexString(src));
            LOG.trace("長さフィールド位置 = " + lengthFieldPosition);
            LOG.trace("長さフィールドの長さ = " + lengthFieldLength);
        }

        List<byte[]> dest = new ArrayList<>();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(src)) {
            while (bis.available() > 0) {
                //現在位置が長さフィールドを含むバイト列の先端であるとの前提で、現在位置をマーク
                bis.mark(0);

                //現在位置が長さフィールドを含むバイト列の先端であるとの前提で、長さフィールド開始位置まで読み飛ばす。
                bis.skip(lengthFieldPosition - 1);
                //長さフィールドを取得。
                byte[] lengthFieldValue_Byte = new byte[lengthFieldLength];
                if (bis.read(lengthFieldValue_Byte) == -1) {
                    break;
                }
                int lengthFieldValue = ByteConverter.bytesToInt(lengthFieldValue_Byte);//長さフィールド
                int lengthFieldValue_Processed = preProcessor_t.apply(lengthFieldValue);

                if (LOG.isTraceEnabled()) {
                    LOG.trace("長さフィールド(byte) = " + Hex.encodeHexString(lengthFieldValue_Byte));
                    LOG.trace("長さフィールド(調整前) = " + lengthFieldValue);
                    LOG.trace("長さフィールド(調整後) = " + lengthFieldValue_Processed);
                }
                //マーク位置へ戻る。
                bis.reset();

                //マーク位置からコピーすべきバイト数(先頭から長さフィールドまでのバイト数-1+長さフィールドのバイト数+長さフィールドの値)
                int partLength = lengthFieldPosition - 1 + lengthFieldLength + lengthFieldValue_Processed;
                if (LOG.isTraceEnabled()) {
                    LOG.trace("コピーすべきバイト数= " + partLength);
                }

                //一時的な格納先を確保。
                byte[] temp = new byte[partLength];

                //コピーする。
                if (bis.read(temp) == -1) {
                    break;
                }
                if (LOG.isTraceEnabled()) {
                    LOG.trace("バイト列= " + Hex.encodeHexString(temp));
                }
                dest.add(temp);
            }
        } catch (IOException ex) {
            LOG.info("バイト列のクローズにおいてエラーが発生。対応の必要なし。");
        } finally {
            return dest;
        }
    }

    /**
     * バイト配列を指定の要素数で分割する。半端な部分は0で埋める。
     *
     * @param src 分割処理対象。
     * @param size 分割したい要素数。
     * @return 分割された配列のリスト。
     */
    public static synchronized List<byte[]> split(byte[] src, int size) {
        List<byte[]> dest = new ArrayList<>();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(src)) {
            while (bis.available() > 0) {
                //現在位置をマーク
                bis.mark(0);
                //一時的な格納先を確保。
                byte[] temp = new byte[size];
                //コピーする。
                if (bis.read(temp) == -1) {
                    break;
                }
                dest.add(temp);
            }
        } catch (IOException ex) {
            LOG.info("バイト列のクローズにおいてエラーが発生。対応の必要なし。");
        } finally {
            return dest;
        }
    }

    /**
     * 長さフィールドの大きさがちょうどnバイトの場合、こちらを使用できる。
     *
     * @see ByteArraySplitter.splitByLengthField
     */
    public static synchronized List<byte[]> splitByLengthField(byte[] src, int lengthFieldPosition, int lengthFieldLength) {
        return splitByLengthField(src, lengthFieldPosition, lengthFieldLength, (x) -> x);
    }
}
