/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import org.w3c.dom.Element;
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
