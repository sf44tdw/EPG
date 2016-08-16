/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.xml.channel;

import libepg.epg.section.descriptor.servicedescriptor.SERVICE_ID;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * XMLのチャンネル情報を生成する。
 *
 * @author normal
 */
public final class Channel {
//    //サービス種別
//    private final SERVICE_ID serviceType;
//    //チャンネルID(オリジナルネットワーク識別、トランスポートストリーム識別、サービス識別からハッシュ関数で自動生成)
//    private final String id;
//    //物理チャンネル番号(設定ファイルからロード)
//    private final String tp;
//    //局名の言語
//    private final String display_name_lang;
//    //局名
//    private final String display_name;
//    
//    private final int transport_stream_id;
//    private final int original_network_id;
//    private final int service_id;
//
//    public Channel(String id, String tp, String display_name_lang, String display_name, int transport_stream_id, int original_network_id, int service_id) {
//        this.id = id;
//        this.tp = tp;
//        this.display_name_lang = display_name_lang;
//        this.display_name = display_name;
//        this.transport_stream_id = transport_stream_id;
//        this.original_network_id = original_network_id;
//        this.service_id = service_id;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getTp() {
//        return tp;
//    }
//
//    public String getDisplay_name_lang() {
//        return display_name_lang;
//    }
//
//    public String getDisplay_name() {
//        return display_name;
//    }
//
//    public int getTransport_stream_id() {
//        return transport_stream_id;
//    }
//
//    public int getOriginal_network_id() {
//        return original_network_id;
//    }
//
//    public int getService_id() {
//        return service_id;
//    }
//
//    /**
//     * 指定されたxml文書に対して、このクラスが保持する情報を元にしたchannel要素を追加する。
//     *
//     * @param document
//     */
//    public synchronized void addChannelElement(Document document) {
//        final Element channel = document.createElement("channel");
//        channel.setAttribute("id", this.getId());
//        channel.setAttribute("tp", this.getTp());
//
//        final Element e_display_name = document.createElement("display-name");
//        e_display_name.setAttribute("lang", this.getDisplay_name_lang());
//        final Text textnode1 = document.createTextNode(this.getDisplay_name());
//        e_display_name.appendChild(textnode1);
//        channel.appendChild(e_display_name);
//
//        final Element e_transport_stream_id = document.createElement("transport_stream_id");
//        final Text textnode2 = document.createTextNode(Integer.toString(this.getTransport_stream_id()));
//        e_transport_stream_id.appendChild(textnode2);
//        channel.appendChild(e_transport_stream_id);
//
//        final Element e_service_id = document.createElement("service_id");
//        final Text textnode3 = document.createTextNode(Integer.toString(this.getOriginal_network_id()));
//        e_service_id.appendChild(textnode3);
//        channel.appendChild(e_service_id);
//
//        final Element e_original_network_id = document.createElement("original_network_id");
//        final Text textnode4 = document.createTextNode(Integer.toString(this.getService_id()));
//        e_original_network_id.appendChild(textnode4);
//        channel.appendChild(e_original_network_id);
//
//        document.appendChild(channel);
//    }
//
//    @Override
//    public String toString() {
//        return ReflectionToStringBuilder.toString(this);
//    }

}
