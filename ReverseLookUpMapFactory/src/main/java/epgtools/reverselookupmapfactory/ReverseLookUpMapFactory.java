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
package epgtools.reverselookupmapfactory;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 重複のないSetを持つEnumを、そのSetに保管されているいずれかの値を使って逆引きするマップを作るクラス。 Enumは重複してもよい。
 * Enum内のSetからキーを抽出する際は関数オブジェクトを使用する。
 *
 * @author normal
 * @param <KEY_TYPE> Setのデータ型
 * @param <KEYSET> キーとして使用する値の入ったSetの型
 * @param <V> 値として使うEnum
 */
public class ReverseLookUpMapFactory<KEY_TYPE, KEYSET extends Set<KEY_TYPE>, V extends Enum> {

    private final Map<KEY_TYPE, V> dict;
    private final Function<V, KEYSET> function;

    public ReverseLookUpMapFactory(Function<V, KEYSET> function) {
        this.function = function;

        if (this.function == null) {
            throw new NullPointerException("関数オブジェクトが指定されていません。");
        }
        this.dict = new ConcurrentHashMap<>();
    }

    /**
     * enumのHashSetが持つすべての値とenumを関連付けてマップに追加する。
     *
     * @param e 追加したいenum
     * @throws IllegalArgumentException 既に同じキーが存在した場合。
     */
    public final void put(V e) {
        for (KEY_TYPE k : this.function.apply(e)) {
            if (!this.dict.containsKey(k)) {
                this.dict.put(k, e);
            } else {
                MessageFormat msg = new MessageFormat("キーが重複しています。重複したキー={0} 重複したときに登録しようとしたenum={1}");
                Object[] parameters = {k.toString(), e.toString()};
                throw new IllegalArgumentException(msg.format(parameters));
            }
        }
    }

    /**
     * @return @see java.util.concurrent.ConcurrentHashMap
     */
    public final int size() {
        return dict.size();
    }

    /**
     * @return @see java.util.concurrent.ConcurrentHashMap
     */
    public final boolean isEmpty() {
        return dict.isEmpty();
    }

    /**
     * @param key
     * @return
     * @see java.util.concurrent.ConcurrentHashMap
     */
    public final boolean containsKey(KEY_TYPE key) {
        return dict.containsKey(key);
    }

    /**
     * @param value
     * @return
     * @see java.util.concurrent.ConcurrentHashMap
     */
    public final boolean containsValue(V value) {
        return dict.containsValue(value);
    }

    public final Map<KEY_TYPE, V> getDict() {
        return Collections.unmodifiableMap(dict);
    }

}
