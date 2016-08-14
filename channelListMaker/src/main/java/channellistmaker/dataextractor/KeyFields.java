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

import java.util.Objects;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.math.IntRange;

/**
 * EPGDumpのXMLファイルにおいて、検索に使用する項目と、キーに直接従属する項目(チャンネルID)を保持し、マップ用の複合キーを生成する。
 *
 * @author dosdiaopfhj
 */
public final class KeyFields {

    /**
     * チャンネルid
     */
    private final String channelId;

    /**
     * 各識別の値の範囲
     */
    private static final IntRange ID_RANGE = new IntRange(0, 0xffff);

    /**
     * トランスポートストリーム識別
     */
    private final int transportStreamId;

    /**
     * オリジナルネットワーク識別
     */
    private final int originalNetworkId;

    /**
     * サービス識別
     */
    private final int serviceId;

    /**
     * @param channelId チャンネルID
     * @param transportStreamId トランスポートストリーム識別
     * @param originalNetworkId オリジナルネットワーク識別
     * @param serviceId サービス識別
     * @throws IllegalArgumentException チャンネルIDが設定されていないか、チャンネルID以外のフィールドのいずれかに16ビット符号なし2進数で表記可能な範囲外の値が設定された場合。
     */
    public KeyFields(String channelId, int transportStreamId, int originalNetworkId, int serviceId) throws IllegalArgumentException{
        this.channelId = channelId;
        this.transportStreamId = transportStreamId;
        this.originalNetworkId = originalNetworkId;
        this.serviceId = serviceId;
        if (this.channelId == null || "".equals(this.channelId)) {
            throw new IllegalArgumentException("チャンネルIDがありません。");
        }
        String errorFieldName = null;
        ID_CHECK:
        {
            if (!ID_RANGE.containsInteger(this.transportStreamId)) {
                errorFieldName = "トランスポートストリーム識別";
                break ID_CHECK;
            }
            if (!ID_RANGE.containsInteger(this.originalNetworkId)) {
                errorFieldName = "オリジナルネットワーク識別";
                break ID_CHECK;
            }
            if (!ID_RANGE.containsInteger(this.serviceId)) {
                errorFieldName = "サービス識別";
                break ID_CHECK;
            }
        }
        if (errorFieldName != null) {
            throw new IllegalArgumentException("0x0以上0xffff以下の範囲外の値がセットされました。フィールド = " + errorFieldName);
        }
    }

    /**
     * @return チャンネルID
     */
    public final synchronized String getChannelId() {
        return channelId;
    }

    /**
     * @return トランスポートストリーム識別
     */
    public final synchronized int getTransportStreamId() {
        return transportStreamId;
    }

    /**
     * @return オリジナルネットワーク識別
     */
    public final synchronized int getOriginalNetworkId() {
        return originalNetworkId;
    }

    /**
     * @return サービス識別
     */
    public final synchronized int getServiceId() {
        return serviceId;
    }

    /**
     * トランスポートストリーム識別、オリジナルネットワーク識別、サービス識別から、Map用の複合キーを生成する。
     * @see java.util.Map
     * @return 複合キー
     */
    public final synchronized MultiKey<Integer> getMuiltiKey() {
        return new MultiKey<>(this.getTransportStreamId(), this.getOriginalNetworkId(), this.getServiceId());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.channelId);
        hash = 11 * hash + this.transportStreamId;
        hash = 11 * hash + this.originalNetworkId;
        hash = 11 * hash + this.serviceId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KeyFields other = (KeyFields) obj;
        if (this.transportStreamId != other.transportStreamId) {
            return false;
        }
        if (this.originalNetworkId != other.originalNetworkId) {
            return false;
        }
        if (this.serviceId != other.serviceId) {
            return false;
        }
        if (!Objects.equals(this.channelId, other.channelId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
