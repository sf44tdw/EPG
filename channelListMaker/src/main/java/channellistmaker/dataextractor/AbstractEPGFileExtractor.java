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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NamedNodeMap;

/**
 * 読み込んだEPG XMLから必要な情報を取り出す。
 *
 * @author dosdiaopfhj
 * @param <T> EPGDataインタフェースを実装したクラス。
 */
public abstract class AbstractEPGFileExtractor<T extends EpgData> {

    protected static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }
    private final Document doc;
    private final String nodeName;

    /**
     *
     * @param doc パースされたEPGのXMLデータ
     * @param nodeName 情報を取り出したい要素のノード名
     */
    public AbstractEPGFileExtractor(Document doc, String nodeName) {
        this.doc = doc;
        this.nodeName = nodeName;

    }

    /**
     * traceレベルのログを取得している場合、ノードとその子ノードのダンプをとる
     * @param node ノード
     * @return traceレベルのログを取得している場合、ノードとその子ノード全てのダンプ。そうでないか、ノードが無い場合は空文字列。
     */
    protected final String getNodeInfo(final Node node) {
        if (LOG.isTraceEnabled()) {
            if (node == null) {
                //ノードが無いなら空文字列を返す。
                return "";
            } else {
                final StringBuilder sb = new StringBuilder();
                final String nodeName_t;
                if (node.getNodeName() != null && !"".equals(node.getNodeName())) {
                    nodeName_t = node.getNodeName();
                } else {
                    nodeName_t = "no_Name";
                }
                sb.append(nodeName_t).append(" [ ");
                /* ノードの種類を出力 */
                sb.append("Node_type = ").append(node.getNodeType()).append(" ");
                sb.append(" ");
                /*(あれば)属性を出力*/
                NamedNodeMap attrs = node.getAttributes();  // NamedNodeMapの取得
                if (attrs != null) {
                    sb.append("Attribute [ ");
                    for (int index = 0; index < attrs.getLength(); index++) {
                        Node attr = attrs.item(index);  // 属性ノード
                        sb.append("( ");
                        sb.append("Attribute_name = ").append(attr.getNodeName()).append(" "); // 属性の名前
                        sb.append("Attribute_value = ").append(attr.getNodeValue()).append(" "); // 属性の値
                        sb.append(")");
                    }
                    sb.append(" ] ");
                }
                /* ノードの値 sb.append("] ");を出力 */
                sb.append("Node_value = ").append(node.getNodeValue()).append(" ");
                /*(あれば)子ノードの値を出力 */
                if (node.hasChildNodes()) {
                    sb.append(" \n");
                    NodeList Children = node.getChildNodes();
                    int Nodes = Children.getLength();
                    for (int i = 0; i < Nodes; i++) {
                        Node child = Children.item(i);
                        sb.append(getNodeInfo(child));
                    }
                }
                sb.append("] ");
                return sb.toString();
            }

        } else {
            return "";
        }
    }

    /**
     * 特定の要素名のノードを全て確保し、1件ごとに実装されたdump()を呼び出してデータの抽出処理を行う。
     *
     * @author dosdiaopfhj
     * @return 抽出されたデータのリスト
     *
     */
    public final synchronized Map<MultiKey<Integer>, T> makeMap() {
        Map<MultiKey<Integer>, T> records = new ConcurrentHashMap<>();
        Element root;
        root = this.doc.getDocumentElement();
        NodeList nl = doc.getElementsByTagName(this.nodeName);
        int Nodes = nl.getLength();
        for (int i = 0; i < Nodes; i++) {
            Node N = nl.item(i);
            try {
                T record_val = dump(N);
                T ret = records.put(record_val.getKeyfields().getMuiltiKey(), record_val);
                if (ret != null) {
                    LOG.info("下記のデータが上書きされました。\n" + ret);
                }
            } catch (IllegalArgumentException ex) {
                LOG.warn("データに問題があるため、無視します。", ex);
            }
        }
        return Collections.unmodifiableMap(records);
    }

    /**
     * 取得した要素の内容から必要なデータを1件抽出する。makeMap()が使用することを前提にしているので、外部から直接使わないこと。
     *
     * @author dosdiaopfhj
     * @param N 取得した要素
     * @return 1件分のデータ。
     */
    protected abstract T dump(final Node N) throws IllegalArgumentException;

//    protected final synchronized void printNode(Node node) {
//
//        System.out.print("ノード名 = " + node.getNodeName() + " "); // ノード名
//        System.out.print("ノードタイプ = " + node.getNodeType() + " "); // ノードタイプ
//        System.out.println("ノード値 = " + node.getNodeValue()); // ノード値
//
//    }
}
