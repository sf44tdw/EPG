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
public enum XML_ELEMENT_NAME {
    /**
     * EPG関連 チャンネル要素の名前
     */
    EPG_CHANNEL("channel"),
    /**
     * EPG関連 チャンネル要素のチャンネルIDの名前
     */
    EPG_CHANNEL_ID("id"),
    /**
     * EPG関連 チャンネル要素の物理チャンネル番号の名前
     */
    EPG_PHYSICAL_CHANNEL_NUMBER("tp"),
    /**
     * EPG関連 チャンネル要素の局名の要素名(読み込み用) xmltv.dtdに定義されていたのでそのまま使う。
     */
    EPG_DISPLAY_NAME("display-name"),
    /**
     * EPG関連 チャンネル要素の局名の言語コードの属性名
     */
    EPG_DISPLAY_NAME_LANG("lang"),
    /**
     * EPG関連 チャンネル要素の局名のトランスポートストリーム識別の要素名
     */
    TRANSPORT_STREAM_ID("transport_stream_id"),
    /**
     * EPG関連 チャンネル要素の局名のオリジナルネットワーク識別の要素名
     */
    ORIGINAL_NETWORK_ID("original_network_id"),
    /**
     * EPG関連 チャンネル要素の局名のサービス識別の要素名
     */
    SERVICE_ID("service_id");
    
    
    
    
    private final String value;

    private XML_ELEMENT_NAME(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
