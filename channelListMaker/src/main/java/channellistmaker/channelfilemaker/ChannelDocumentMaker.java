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
package channellistmaker.channelfilemaker;

import channellistmaker.dataextractor.KeyFields;
import channellistmaker.dataextractor.channel.Channel;
import static channellistmaker.dataextractor.channel.XML_ELEMENT_NAME.XML_ELEMENT_NAME;
import static channellistmaker.dataextractor.channel.XML_ELEMENT_NAME.XML_ELEMENT_NAME;
import static channellistmaker.dataextractor.channel.XML_ELEMENT_NAME.XML_ELEMENT_NAME;
import static channellistmaker.dataextractor.channel.XML_ELEMENT_NAME.XML_ELEMENT_NAME;
import static channellistmaker.dataextractor.channel.XML_ELEMENT_NAME.XML_ELEMENT_NAME;
import static channellistmaker.dataextractor.channel.XML_ELEMENT_NAME.XML_ELEMENT_NAME;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author normal
 */
public final class ChannelDocumentMaker {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    private final Map<MultiKey<Integer>, Channel> channels;

    public ChannelDocumentMaker(Map<MultiKey<Integer>, Channel> channels) {
        Map<MultiKey<Integer>, Channel> temp = new ConcurrentHashMap<>();
        temp.putAll(channels);
        this.channels = Collections.unmodifiableMap(temp);
    }

    public Map<MultiKey<Integer>, Channel> getChannels() {
        return channels;
    }

    /**
     * このような要素を追加する。 {@literal <element_name>text_value</element_name>}
     *
     * @param　document 要素を追加するXML文書
     * @param　parent 追加する要素の親となる要素
     * @param　elementName 要素名
     * @param　textValue 要素の値
     */
    private void addTextElement(Document document, Element parent, String elementName, String textValue) {
        final Element e = document.createElement(elementName);
        parent.appendChild(e);
        final Text textMessage = document.createTextNode(textValue);
        e.appendChild(textMessage);
    }

    private void addChannelElement(Document document, Element channels, Channel channel_o) {
        KeyFields kf = channel_o.getKeyfields();
        Element channel = document.createElement(EPG_CHANNEL);
        channels.appendChild(channel);
        this.addTextElement(document, channel, TRANSPORT_STREAM_ID, Integer.toString(kf.getTransportStreamId()));
        this.addTextElement(document, channel, ORIGINAL_NETWORK_ID, Integer.toString(kf.getOriginalNetworkId()));
        this.addTextElement(document, channel, SERVICE_ID, Integer.toString(kf.getServiceId()));
        this.addTextElement(document, channel, EPG_PHYSICAL_CHANNEL_NUMBER, Integer.toString(channel_o.getPhysicalChannelNumber()));
        this.addTextElement(document, channel, EPG_DISPLAY_NAME, channel_o.getDisplayName());

        MessageFormat mf = new MessageFormat("トランスポートストリーム識別 = {0} オリジナルネットワーク識別 = {1} サービス識別 = {2} 物理チャンネル = {3} 放送局名 = {4}");
        Object[] message = {kf.getTransportStreamId(), kf.getOriginalNetworkId(), kf.getServiceId(), channel_o.getPhysicalChannelNumber(), channel_o.getDisplayName()};
        LOG.info(mf.format(message));
    }

    /**
     * @return 渡されたチャンネル一覧をX変換したML文書の文字列。エラーが起きた場合は空文字列を返す。
     */
    public String getChannelList() {
        try {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final Document document = db.newDocument();
            // >>>>> DOMの生成
            Element channels_e = document.createElement("channels");//<-rootドキュメント
            document.appendChild(channels_e);

            final Set<MultiKey<Integer>> keys = this.channels.keySet();
            for (MultiKey<Integer> key : keys) {
                Channel ch = channels.get(key);
                this.addChannelElement(document, channels_e, ch);
            }

            TransformerFactory tf = TransformerFactory.newInstance();

            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            StringWriter writer = new StringWriter();// <-ここでは文字列として出力している
            StreamResult result = new StreamResult(writer);

            DOMSource source = new DOMSource(document);
            transformer.transform(source, result);

            return writer.toString();
        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            LOG.fatal(ex);
            return "";
        } catch (TransformerException ex) {
            LOG.fatal(ex);
            return "";
        }
    }
}
