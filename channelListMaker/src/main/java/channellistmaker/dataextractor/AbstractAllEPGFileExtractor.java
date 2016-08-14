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
package channellistmaker.dataextractor;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

/**
 * チャンネル。番組の情報を全てのXMLファイルから抽出し、リストにまとめる。
 *
 * @author dosdiaopfhj
 * @param <T> EPGDataインタフェースを実装したクラス。
 * @param <U> 情報取り出し用クラス
 */
public abstract class AbstractAllEPGFileExtractor<T extends EpgData, U extends AbstractEPGFileExtractor<T>> {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }
    private final Set<Document> EPGXMLs;

    /**
     * @param EPGXMLs ロード済みのEPG XMLファイルのリスト
     */
    public AbstractAllEPGFileExtractor(Set<Document> EPGXMLs) {
        this.EPGXMLs = EPGXMLs;
    }

    /**
     * XMLから情報を取り出すクラスを取得する処理を実装する。getAllEPGRecords()が使用する。
     *
     * @param doc ロードされたXMLファイル
     * @return 情報取り出し用クラス
     */
    protected abstract U getExtractor(Document doc);

    /**
     * 渡された全てのXMLから、 getExtractor()によって取得された抽出用オブジェクトによって抽出された情報を取得する。
     *
     * @return 取得された情報。
     */
    public final synchronized Map<MultiKey<Integer>, T> getAllEPGRecords() {
        Map<MultiKey<Integer>, T> temp1 = new ConcurrentHashMap<>();
        LOG.info("XMLファイルの数 = " + this.EPGXMLs.size());
        for (Document D : this.EPGXMLs) {
            Map<MultiKey<Integer>, T> temp2;
            temp2 = this.getExtractor(D).makeMap();
            temp1.putAll(temp2);
        }
        return Collections.unmodifiableMap(temp1);
    }
}
