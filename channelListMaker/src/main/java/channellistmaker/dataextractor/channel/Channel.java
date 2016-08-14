package channellistmaker.dataextractor.channel;

import channellistmaker.dataextractor.KeyFields;
import java.util.Objects;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import channellistmaker.dataextractor.EpgData;


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
/**
 * チャンネル情報保持用
 *
 * @author dosdiaopfhj
 */
public final class Channel implements EpgData {

    /**
     * キー項目
     */
    private final KeyFields keyfields;

    /**
     * 物理チャンネル番号
     */
    private final int physicalChannelNumber;

    /**
     * 放送局名の言語
     */
    private final String displayNameLang;

    /**
     * 放送局名
     */
    private final String displayName;

    /**
     * @param id チャンネルID
     * @param displayNameLang 放送局名の言語
     * @param displayName 放送局名
     * @param physicalChannelNumber 物理チャンネル番号
     * @param transportStreamId トランスポートストリーム識別
     * @param originalNetworkId オリジナルネットワーク識別
     * @param serviceId サービス識別
     */
    public Channel(int physicalChannelNumber, String displayNameLang, String displayName, String id, int transportStreamId, int originalNetworkId, int serviceId) {
        this.keyfields = new KeyFields(id, transportStreamId, originalNetworkId, serviceId);
        this.physicalChannelNumber = physicalChannelNumber;
        this.displayNameLang = displayNameLang;
        this.displayName = displayName;

        if (this.physicalChannelNumber < 0) {
            throw new IllegalArgumentException("物理チャンネル番号が0未満です。");
        }
        if (this.displayNameLang == null || "".equals(this.displayNameLang)) {
            throw new IllegalArgumentException("放送局名の言語コードがありません。");
        }
        if (this.displayName == null || "".equals(this.displayName)) {
            throw new IllegalArgumentException("放送局名がありません。");
        }
    }

    public synchronized int getPhysicalChannelNumber() {
        return physicalChannelNumber;
    }

    public synchronized String getDisplayNameLang() {
        return displayNameLang;
    }

    public synchronized String getBroadcastingStationName() {
        return displayName;
    }

    @Override
    public synchronized KeyFields getKeyfields() {
        return keyfields;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.keyfields);
        hash = 59 * hash + this.physicalChannelNumber;
        hash = 59 * hash + Objects.hashCode(this.displayNameLang);
        hash = 59 * hash + Objects.hashCode(this.displayName);
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
        final Channel other = (Channel) obj;
        if (this.physicalChannelNumber != other.physicalChannelNumber) {
            return false;
        }
        if (!Objects.equals(this.displayNameLang, other.displayNameLang)) {
            return false;
        }
        if (!Objects.equals(this.displayName, other.displayName)) {
            return false;
        }
        if (!Objects.equals(this.keyfields, other.keyfields)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
