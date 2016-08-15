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
package channellistmaker.dataextractor.channel;

/**
 *
 * @author normal
 */
public final class XmlElementName {

    /**
     * EPG関連 チャンネル要素の要素名
     */
    public static final String EPG_CHANNEL = "channel";

    /**
     * EPG関連 チャンネル要素のチャンネルIDの属性名
     */
    public static final String EPG_CHANNEL_ID = "id";

    /**
     * EPG関連 チャンネル要素の物理チャンネル番号の属性名
     */
    public static final String EPG_CHANNEL_TP = "tp";

    /**
     * EPG関連 チャンネル要素の局名の要素名(読み込み用)
     */
    public static final String EPG_DISPLAY_NAME_R = "display-name";
    
        /**
     * EPG関連 チャンネル要素の局名の要素名(書き込み用)
     */
    public static final String EPG_DISPLAY_NAME_W = "display_name";
    
    /**
     * EPG関連 チャンネル要素の局名の言語コードの属性名
     */
    public static final String EPG_DISPLAY_NAME_LANG = "lang";
    /**
     * EPG関連 チャンネル要素の局名のトランスポートストリーム識別の要素名
     */
    public static final String TRANSPORT_STREAM_ID = "transport_stream_id";
    /**
     * EPG関連 チャンネル要素の局名のオリジナルネットワーク識別の要素名
     */
    public static final String ORIGINAL_NETWORK_ID = "original_network_id";
    /**
     * EPG関連 チャンネル要素の局名のサービス識別の要素名
     */
    public static final String SERVICE_ID = "service_id";
    
            /**
     * EPG関連 チャンネル要素の物理チャンネル番号の要素名(書き込み用)
     */
    public static final String PHYSICAL_CHANNEL_NUMBER_W = "physical_channel_number";

    private XmlElementName() {
    }

}
