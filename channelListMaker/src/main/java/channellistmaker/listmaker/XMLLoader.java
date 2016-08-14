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
package channellistmaker.listmaker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 指定された文字コードでEPG XMLファイルを読み込む。
 *
 * @author dosdiaopfhj
 */
public class XMLLoader {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }
    private final Charset charset;

    
    
    /**
     * @param charset XMLファイルの文字コード
     */
    public XMLLoader(Charset charset) {
        this.charset = charset;
    }

    public final synchronized Charset getCharset() {
        return charset;
    }

    /**
     * XMLを読み込む
     *
     * @param F XMLファイル
     * @return XMLファイルから作ったDocumentオブジェクト。
     * @author dosdiaopfhj
     */
    public synchronized Document Load(File F) {
        try {
            LOG.info("ファイル = " + F + " 文字コード = " + getCharset() + " 読み込み開始。");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            documentBuilder.setEntityResolver(new XmlTvDtdResolver());
            Document document = documentBuilder.parse(new InputSource(new InputStreamReader(new FileInputStream(F), getCharset())));
//            Element root = document.getDocumentElement();
            LOG.info("ファイル = " + F + " 文字コード = " + getCharset() + " 読み込み完了。");
            return document;
        } catch (ParserConfigurationException | UnsupportedEncodingException | FileNotFoundException ex) {
            LOG.fatal("例外発生。", ex);
            return null;
        } catch (SAXException | IOException ex) {
            LOG.fatal("例外発生。", ex);
            return null;
        }

    }

}
