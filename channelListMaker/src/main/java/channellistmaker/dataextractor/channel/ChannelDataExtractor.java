/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package channellistmaker.dataextractor.channel;

import channellistmaker.dataextractor.AbstractEPGFileExtractor;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * チャンネル関係の情報だけオブジェクトに格納する
 *
 * 地上波、衛星とも トランスポートストリーム識別、オリジナルネットワーク識別、サービス識別を取得。
 *
 * 地上波の場合 EPGのチャンネルid(GR_*)? :id EPG取得時の物理チャンネル番号 :tp テレビ局名 :display-name
 *
 * BSの場合 EPGのチャンネルid?(BS_物理チャンネル番号) :id EPG取得時の物理チャンネル番号(BS_*_*) :tp
 * テレビ局名:display-name
 *
 * を取得し、リストに格納する。
 *
 * @author dosdiaopfhj
 */
public class ChannelDataExtractor extends AbstractEPGFileExtractor<Channel> {

    /**
     * EPG関連 チャンネル要素の要素名
     */
    private static final String EPG_CHANNEL = "channel";

    /**
     * EPG関連 チャンネルIDが地上波の場合の接頭辞
     */
    private static final String PREFIX_GR = "GR";

    /**
     * EPG関連 チャンネルIDがBSの場合の接頭辞
     */
    private static final String PREFIX_BS = "BS";

//    /**
//     * EPG関連 チャンネルIDがCSの場合の接頭辞
//     */
//    private static final String PREFIX_CS = "CS";
    public ChannelDataExtractor(Document doc) {
        super(doc, ChannelDataExtractor.EPG_CHANNEL);
    }

    private synchronized int getChannelNumber(String ch_S, String tp_S) {
//チャンネルidの頭2文字を取り出す。
        int r;
        String pref = ch_S.substring(0, 2);
        if (LOG.isTraceEnabled()) {
            LOG.trace("チャンネルid = " + ch_S + " 頭2文字 = " + pref);
        }
        switch (pref) {
            case ChannelDataExtractor.PREFIX_GR:
                //地上波の場合
                r = Integer.parseInt(tp_S);
                break;
            default:
                //BSかCSの場合(CSについては憶測)
                //チャンネルidの頭3文字以外を数字に変換して物理チャンネル番号とする。
                r = Integer.parseInt(ch_S.substring(3));
                break;
        }
        return r;
    }

    @Override
    protected final synchronized Channel dump(Node N) throws IllegalArgumentException {
        /**
         * EPG関連 チャンネル要素のチャンネルIDの属性名
         */
        final String EPG_CHANNEL_ID = "id";

        /**
         * EPG関連 チャンネル要素の物理チャンネル番号の属性名
         */
        final String EPG_CHANNEL_TP = "tp";

        /**
         * EPG関連 チャンネル要素の局名の要素名
         */
        final String EPG_DISPLAY_NAME = "display-name";
        /**
         * EPG関連 チャンネル要素の局名の言語コードの属性名
         */
        final String EPG_DISPLAY_NAME_LANG = "lang";
        /**
         * EPG関連 チャンネル要素の局名のトランスポートストリーム識別の要素名
         */
        final String TRANSPORT_STREAM_ID = "transport_stream_id";
        /**
         * EPG関連 チャンネル要素の局名のオリジナルネットワーク識別の要素名
         */
        final String ORIGINAL_NETWORK_ID = "original_network_id";
        /**
         * EPG関連 チャンネル要素の局名のサービス識別の要素名
         */
        final String SERVICE_ID = "service_id";

        final String ch_S;
        final String tp_S;
        final String display_name_l;
        final String display_name_S;
        final int transport_stream_id;
        final int original_network_id;
        final int service_id;

        NamedNodeMap attrs_channel = N.getAttributes();  // チャンネルの属性リストを取得
        ch_S = attrs_channel.getNamedItem(EPG_CHANNEL_ID).getNodeValue();
        tp_S = attrs_channel.getNamedItem(EPG_CHANNEL_TP).getNodeValue();
        if (LOG.isTraceEnabled()) {
            LOG.trace(EPG_CHANNEL_ID + " = " + ch_S);
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace(EPG_CHANNEL_TP + " = " + tp_S);
        }

        Node displayNameNode = null;
        Node transPortStreamIdNode = null;
        Node originalNetWorkIdNode = null;
        Node serviceIdNode = null;

        NodeList channelChildren = N.getChildNodes();
        int Nodes = channelChildren.getLength();
        for (int i = 0; i < Nodes; i++) {
            Node gchild = channelChildren.item(i);
            switch (gchild.getNodeName()) {
                case EPG_DISPLAY_NAME:
                    displayNameNode = gchild;
                    LOG.trace(EPG_DISPLAY_NAME);
                    LOG.trace(getNodeInfo(gchild));
                    break;
                case TRANSPORT_STREAM_ID:
                    transPortStreamIdNode = gchild;
                    LOG.trace(TRANSPORT_STREAM_ID);
                    LOG.trace(getNodeInfo(gchild));
                    break;
                case ORIGINAL_NETWORK_ID:
                    originalNetWorkIdNode = gchild;
                    LOG.trace(ORIGINAL_NETWORK_ID);
                    LOG.trace(getNodeInfo(gchild));
                    break;
                case SERVICE_ID:
                    serviceIdNode = gchild;
                    LOG.trace(SERVICE_ID);
                    LOG.trace(getNodeInfo(gchild));
                    break;
            }
        }
        String lang = "";
        String disp = "";
        try {
            NamedNodeMap attrs_dname = displayNameNode.getAttributes();
            Node l = attrs_dname.getNamedItem(EPG_DISPLAY_NAME_LANG);
            lang = l.getNodeValue();
            disp = displayNameNode.getFirstChild().getNodeValue();
            if (LOG.isTraceEnabled()) {
                LOG.trace("放送局名言語 = " + lang);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("放送局名 = " + disp);
            }
        } catch (NullPointerException e) {    //たまに空欄になっていることがあるので、その場合は仮の名前を記入する。
            lang = "ja_JP";
            disp = "Unknown";
            LOG.warn("放送局名無し", e);
        } finally {
            display_name_l = lang;
            display_name_S = disp;
        }

        int tsId = Integer.MIN_VALUE;
        try {
            tsId = Integer.parseInt(transPortStreamIdNode.getFirstChild().getNodeValue());
        } catch (NullPointerException e) {    //たまに空欄になっていることがあるので、その場合は仮の名前を記入する。
            LOG.warn("トランスポートストリーム識別無し", e);
        } finally {
            transport_stream_id = tsId;
        }

        int netId = Integer.MIN_VALUE;
        try {
//            LOG.trace(getNodeInfo(originalNetWorkIdNode.getFirstChild()));
            netId = Integer.parseInt(originalNetWorkIdNode.getFirstChild().getNodeValue());
        } catch (NullPointerException e) {    //たまに空欄になっていることがあるので、その場合は仮の名前を記入する。
            LOG.warn("ネットワーク識別無し", e);
        } finally {
            original_network_id = netId;
        }

        int servId = Integer.MIN_VALUE;
        try {
//            LOG.trace(getNodeInfo(serviceIdNode.getFirstChild()));
            servId = Integer.parseInt(serviceIdNode.getFirstChild().getNodeValue());
        } catch (NullPointerException e) {    //たまに空欄になっていることがあるので、その場合は仮の名前を記入する。
            LOG.warn("サービス識別無し", e);
        } finally {
            service_id = servId;
        }

        return new Channel(getChannelNumber(ch_S, tp_S), display_name_l, display_name_S, ch_S, transport_stream_id, original_network_id, service_id);
    }
}