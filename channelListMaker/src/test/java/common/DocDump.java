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
package common;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * XMLのダンプ用クラス。
 * http://www.fireproject.jp/feature/xml/programing/java-dom.html
 * @author normal
 */
public class DocDump {

    protected Document doc;
    protected int tab;

    public DocDump(Document doc) {
        this.doc=doc;
        tab = 0;
    }

    public void getNodeInfo(Node node) {
        tabbing();
        /* ノードの種類を出力 */
        System.out.println("Node type= " + node.getNodeType());
        tabbing();
        /* ノード名を出力 */
        System.out.println("Node name= " + node.getNodeName());
        tabbing();
        /* ノードの値を出力 */
        System.out.println("Node value= " + node.getNodeValue());
    }

    /* 全ノードを探索 */
    public void walkThrough() {
        Node root = doc.getDocumentElement();
        recursiveWalk(root);
    }

    private void recursiveWalk(Node node) {
        /*
これは,XML文書のインデントなどの空白のノードを読み飛ばすための処理.
Node.TEXT_NODE ノードがテキストで,ノードの値の空白を除いた文字列の長さが0の場合は読み飛ばす.
         */

        if (node.getNodeType() == Node.TEXT_NODE && node.getNodeValue().trim().length() == 0) {
            return;
        }
        getNodeInfo(node);
        tab++;
        /* node.getFirstChild : nodeの最初の子を得る */
 /* child.getNextSibling : childの兄弟ノードを得る */
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            recursiveWalk(child);
        }
        tab--;
    }

    protected void tabbing() {
        for (int i = 0; i < tab; i++) {
            System.out.print("\t");
        }
    }

}
