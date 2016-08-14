/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.common.packet;

import libepg.ts.packet.TsPacket;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author normal
 */
public class TsPacket_error_indicator {

    public final char[] p_error01 = "47b380eb9419ffffffffffffff56fffff416ffff08ffffffffffff73fffffeffff49ffffff64936493ffffffffffffffffff30ffffffff2257ffffffffecfbfbe3ffffffffff64ffffff4efffffffafee3ffffffff02e6ffff08a093ffffffffffd2acffccffff8afffffffffffffefff00f5bffffffffffffffb5ffffffffffffffffffff30ff04ffffffff4dffffffff869fffff93c2ffff52f862524f2aa6174db079154c325e2e2c571b30fdaeabcbcb05002b926666c59d9459".toCharArray();
    public final char[] p_error02 = "4785816468996aef3db83dd07bb1a0930470c297c30238b27f84c846596393030fd07a301000630e0e880f45e39863d47d7e1639ee15173b0157d22b77ff20834b746e3643fac910f0aef67823dbd0c900e4559a82f41680d27ed2e0387a06be55ade7ccfcd4ffdb1605e106d5696830c1518b403c2104deca60f4edf38653cd7436217918affc0b26f8314bfebbfe3fda7f34796ffbfc769efdffa1f8bb8d11b19b628d9fe8c12f41effe8f2fcb62ba2c15e99f880a84819493be06".toCharArray();
    public final TsPacket target_error01;
    public final TsPacket target_error02;

    public TsPacket_error_indicator() throws DecoderException {
        this.target_error01 = new TsPacket(Hex.decodeHex(p_error01));
        this.target_error02 = new TsPacket(Hex.decodeHex(p_error02));
    }

}
