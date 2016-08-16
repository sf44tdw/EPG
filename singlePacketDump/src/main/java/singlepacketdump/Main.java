/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singlepacketdump;

import epgtools.loggerfactory.LoggerFactory;
import java.lang.invoke.MethodHandles;
import libepg.ts.packet.TsPacket;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;

/**
 * tsパケット1個分のhexダンプを与えると、そのパケットの中身を出力する。
 *
 * @author normal
 */
public class Main {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;
    
    private static final Log LOG;
    
    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, Main.CLASS_LOG_OUTPUT_MODE).getLOG();
    }
    
    public static void main(String args[]) {
        try {
            new Main().start(args);
            System.exit(0);
        } catch (DecoderException | ParseException ex) {
            System.exit(1);
        }
    }
    
    private void printHelp(Options opts) {
        // print usage.
        HelpFormatter help = new HelpFormatter();
        help.printHelp("My Java Application", opts);
    }
    
    public void start(String args[]) throws DecoderException, ParseException {
        final byte[] packet;
        byte[] x = null;
        final Option packetOption = Option.builder("p")
                .required()
                .longOpt("packet")
                .desc("TSパケットのhexダンプ")
                .hasArg()
                .type(String.class)
                .build();
        
        Options opts = new Options();
        opts.addOption(packetOption);
        try {
            
            CommandLineParser parser = new DefaultParser();
            CommandLine cl = parser.parse(opts, args);
            
            x = Hex.decodeHex(cl.getOptionValue(packetOption.getOpt()).toCharArray());
            
        } catch (DecoderException ex) {
            LOG.fatal("hexダンプの変換に失敗しました。", ex);
            x = null;
            throw ex;
        } catch (ParseException ex) {
            LOG.fatal("オプションの解釈に失敗しました。", ex);
            x = null;
            throw ex;
        } finally {
            packet = x;
            if (packet == null) {
                this.printHelp(opts);
            }
        }
        
        TsPacket p = new TsPacket(packet);
        System.out.println(p);
        
    }
    
}
