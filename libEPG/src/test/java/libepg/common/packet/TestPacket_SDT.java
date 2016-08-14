/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.common.packet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import libepg.ts.packet.TsPacket;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author normal
 */
public class TestPacket_SDT {

    private final char[] p100 = "474011100042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p101 = "474011110042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p102 = "474011120042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p103 = "474011130042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p104 = "474011140042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p105 = "474011150042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p106 = "474011160042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p107 = "474011170042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p108 = "474011180042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p109 = "474011190042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p110 = "4740111a0042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p111 = "4740111b0042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p112 = "4740111c0042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p113 = "4740111d0042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p114 = "4740111e0042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p115 = "4740111f0042f0977fe1d100007fe1ff0408f30020481201000f0e4e484b451d461d6c310f456c357ec10184cf0701fe08f00104080409f3001c481201000f0e4e484b451d461d6c320f456c357ec10184cf0302fe08040af3001c481201000f0e4e484b451d461d6c330f456c357ec10184cf0302fe080588e5001f480ec0000b0e4e484b0f374842530e32c10188cf0a030e4e484b0f215d0e32722fa2b5ffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();

    private final TsPacket target100;
    private final TsPacket target101;
    private final TsPacket target102;
    private final TsPacket target103;
    private final TsPacket target104;
    private final TsPacket target105;
    private final TsPacket target106;
    private final TsPacket target107;
    private final TsPacket target108;
    private final TsPacket target109;
    private final TsPacket target110;
    private final TsPacket target111;
    private final TsPacket target112;
    private final TsPacket target113;
    private final TsPacket target114;
    private final TsPacket target115;

    private final char[] p200 = "474011100042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p201 = "474011110042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p202 = "474011120042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p203 = "474011130042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p204 = "474011140042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p205 = "474011150042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p206 = "474011160042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p207 = "474011170042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p208 = "474011180042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p209 = "474011190042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p210 = "4740111a0042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p211 = "4740111b0042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p212 = "4740111c0042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p213 = "4740111d0042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p214 = "4740111e0042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();
    private final char[] p215 = "4740111f0042f0807fe0cd00007fe0ff0400f3002248140100110e4e484b0f416d39670e31fe0f456c357ec10184cf0701fe00f00004000401f3001e48140100110e4e484b0f416d39670e32fe0f456c357ec10184cf0302fe000580e500254814c000110e4e484b0f374842530e47fe0f456c357ec10188cf0a030e4e484b0f215d0e47009f6694ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff".toCharArray();

    private final TsPacket target200;
    private final TsPacket target201;
    private final TsPacket target202;
    private final TsPacket target203;
    private final TsPacket target204;
    private final TsPacket target205;
    private final TsPacket target206;
    private final TsPacket target207;
    private final TsPacket target208;
    private final TsPacket target209;
    private final TsPacket target210;
    private final TsPacket target211;
    private final TsPacket target212;
    private final TsPacket target213;
    private final TsPacket target214;
    private final TsPacket target215;

    public TestPacket_SDT() throws DecoderException {
        target100 = new TsPacket(Hex.decodeHex(p100));
        target101 = new TsPacket(Hex.decodeHex(p101));
        target102 = new TsPacket(Hex.decodeHex(p102));
        target103 = new TsPacket(Hex.decodeHex(p103));
        target104 = new TsPacket(Hex.decodeHex(p104));
        target105 = new TsPacket(Hex.decodeHex(p105));
        target106 = new TsPacket(Hex.decodeHex(p106));
        target107 = new TsPacket(Hex.decodeHex(p107));
        target108 = new TsPacket(Hex.decodeHex(p108));
        target109 = new TsPacket(Hex.decodeHex(p109));
        target110 = new TsPacket(Hex.decodeHex(p110));
        target111 = new TsPacket(Hex.decodeHex(p111));
        target112 = new TsPacket(Hex.decodeHex(p112));
        target113 = new TsPacket(Hex.decodeHex(p113));
        target114 = new TsPacket(Hex.decodeHex(p114));
        target115 = new TsPacket(Hex.decodeHex(p115));

        target200 = new TsPacket(Hex.decodeHex(p200));
        target201 = new TsPacket(Hex.decodeHex(p201));
        target202 = new TsPacket(Hex.decodeHex(p202));
        target203 = new TsPacket(Hex.decodeHex(p203));
        target204 = new TsPacket(Hex.decodeHex(p204));
        target205 = new TsPacket(Hex.decodeHex(p205));
        target206 = new TsPacket(Hex.decodeHex(p206));
        target207 = new TsPacket(Hex.decodeHex(p207));
        target208 = new TsPacket(Hex.decodeHex(p208));
        target209 = new TsPacket(Hex.decodeHex(p209));
        target210 = new TsPacket(Hex.decodeHex(p210));
        target211 = new TsPacket(Hex.decodeHex(p211));
        target212 = new TsPacket(Hex.decodeHex(p212));
        target213 = new TsPacket(Hex.decodeHex(p213));
        target214 = new TsPacket(Hex.decodeHex(p214));
        target215 = new TsPacket(Hex.decodeHex(p215));
    }

    public List<TsPacket> getPackets() {
        List<TsPacket> l = new ArrayList<>();
        
        l.add(target100);
        l.add(target101);
        l.add(target102);
        l.add(target103);
        l.add(target104);
        l.add(target105);
        l.add(target106);
        l.add(target107);
        l.add(target108);
        l.add(target109);
        l.add(target110);
        l.add(target111);
        l.add(target112);
        l.add(target113);
        l.add(target114);
        l.add(target115);

        l.add(target200);
        l.add(target201);
        l.add(target202);
        l.add(target203);
        l.add(target204);
        l.add(target205);
        l.add(target206);
        l.add(target207);
        l.add(target208);
        l.add(target209);
        l.add(target210);
        l.add(target211);
        l.add(target212);
        l.add(target213);
        l.add(target214);
        l.add(target215);
        
        return Collections.unmodifiableList(l);
    }

    public TsPacket getTarget100() {
        return target100;
    }

    public TsPacket getTarget101() {
        return target101;
    }

    public TsPacket getTarget102() {
        return target102;
    }

    public TsPacket getTarget103() {
        return target103;
    }

    public TsPacket getTarget104() {
        return target104;
    }

    public TsPacket getTarget105() {
        return target105;
    }

    public TsPacket getTarget106() {
        return target106;
    }

    public TsPacket getTarget107() {
        return target107;
    }

    public TsPacket getTarget108() {
        return target108;
    }

    public TsPacket getTarget109() {
        return target109;
    }

    public TsPacket getTarget110() {
        return target110;
    }

    public TsPacket getTarget111() {
        return target111;
    }

    public TsPacket getTarget112() {
        return target112;
    }

    public TsPacket getTarget113() {
        return target113;
    }

    public TsPacket getTarget114() {
        return target114;
    }

    public TsPacket getTarget115() {
        return target115;
    }

    public TsPacket getTarget200() {
        return target200;
    }

    public TsPacket getTarget201() {
        return target201;
    }

    public TsPacket getTarget202() {
        return target202;
    }

    public TsPacket getTarget203() {
        return target203;
    }

    public TsPacket getTarget204() {
        return target204;
    }

    public TsPacket getTarget205() {
        return target205;
    }

    public TsPacket getTarget206() {
        return target206;
    }

    public TsPacket getTarget207() {
        return target207;
    }

    public TsPacket getTarget208() {
        return target208;
    }

    public TsPacket getTarget209() {
        return target209;
    }

    public TsPacket getTarget210() {
        return target210;
    }

    public TsPacket getTarget211() {
        return target211;
    }

    public TsPacket getTarget212() {
        return target212;
    }

    public TsPacket getTarget213() {
        return target213;
    }

    public TsPacket getTarget214() {
        return target214;
    }

    public TsPacket getTarget215() {
        return target215;
    }

}
