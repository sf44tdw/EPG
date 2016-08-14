package channellistmaker.dataextractor.channel;

import channellistmaker.dataextractor.KeyFields;
import java.util.Objects;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import channellistmaker.dataextractor.EpgData;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
