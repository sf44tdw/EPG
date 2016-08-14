/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.ts.packet;

/**
 * TSパケットへの参照と、直前のパケットの欠落を示すフラグを保管する。
 *
 * @author normal
 */
public final class TsPacketParcel {

    /**
     * 前パケット欠落フラグ
     */
    public static enum MISSING_PACKET_FLAG {
        /**
         * 前パケット欠落なし(false)
         */
        NOT_MISSING(false),
        /**
         * 前パケット欠落あり(true)
         */
        MISSING_JUST_BEFORE(true);
        private boolean flag;

        private MISSING_PACKET_FLAG(boolean flag) {
            this.flag = flag;
        }

        public boolean getFlag() {
            return flag;
        }
    }

    private final MISSING_PACKET_FLAG MissingJustBefore;

    private final TsPacket packet;

    public TsPacketParcel(TsPacket packet, MISSING_PACKET_FLAG MissingJustBefore) {
        this.packet = packet;
        this.MissingJustBefore = MissingJustBefore;
    }

    /**
     * 直前のパケットの欠落を示すフラグ。デフォルトはfalse
     *
     * @return NOT_MISSINGならば、このオブジェクトに格納されているパケットの直前のパケットは欠落していない。
     */
    public MISSING_PACKET_FLAG isMissingJustBefore() {
        return MissingJustBefore;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.MissingJustBefore.getFlag() ? 1 : 0);
        hash = 97 * hash + this.packet.hashCode();
        return hash;
    }

    public TsPacket getPacket() {
        return packet;
    }

    /**
     * @return
     * 比較対象のオブジェクトがこのオブジェクトと同じクラスのインスタンスで、比較対象のオブジェクトと、このオブジェクトのフィールドの値が同じ内容であればtrue
     */
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
        final TsPacketParcel other = (TsPacketParcel) obj;
        if (this.MissingJustBefore.getFlag() != other.MissingJustBefore.getFlag()) {
            return false;
        }
        if (!this.packet.equals(other.packet)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nTsPacketParcel{" + "MissingJustBefore=" + MissingJustBefore + " TsPacket={" + this.packet.toString() + "}" + "}\n";
    }

}
