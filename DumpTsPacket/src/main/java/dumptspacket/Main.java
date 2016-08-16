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
package dumptspacket;

import epgtools.loggerfactory.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import libepg.ts.packet.TsPacketParcel;
import libepg.ts.reader.TsReader;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;

/**
 * 指定されたtsファイルの先頭から数えて指定された数のパケットを読み込み、指定されたpid(複数)毎に分類し、16進ダンプとして出力する。<br>
 * 16進ダンプのファイル名は、tsファイル名+pid+.txtになる。<br>
 * 読み込んだパケットのうち、指定されていないpidのパケットはすべて無視する。<br>
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

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            new Main().start(args);
            System.exit(0);
        } catch (org.apache.commons.cli.ParseException ex) {
            LOG.fatal("オプションの解釈に失敗しました。", ex);
            System.exit(1);
        }
    }

    private String dumpCollection(Collection<?> target) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Object s : target) {
            sb.append(s.getClass());
            sb.append("=");
            sb.append(s);
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

    private String dumpArgs(String[] args) {
        Collection<String> strs = Arrays.asList(args);
        return this.dumpCollection(strs);
    }

    private String dumpSet(Set<?> target) {
        return this.dumpCollection(target);
    }

    public void start(String[] args) throws org.apache.commons.cli.ParseException {
        final String fileName;
        final Long limit;
        final Set<Integer> pids;

        System.out.println("args   : " + dumpArgs(args));

        final Option fileNameOption = Option.builder("f")
                .required()
                .longOpt("filename")
                .desc("tsファイル名")
                .hasArg()
                .type(String.class)
                .build();

        final Option limitOption = Option.builder("l")
                .required(false)
                .longOpt("limit")
                .desc("読み込みパケット数(省略可。省略した場合はEOFまで読む。)")
                .hasArg()
                .type(Long.class)
                .build();

        final Option pidsOption = Option.builder("p")
                .required()
                .longOpt("pids")
                .desc("pid(複数指定可。16進数で入力)")
                .type(String.class)
                .hasArgs()
                .build();

        Options opts = new Options();
        opts.addOption(fileNameOption);
        opts.addOption(limitOption);
        opts.addOption(pidsOption);
        CommandLineParser parser = new DefaultParser();
        CommandLine cl;
        HelpFormatter help = new HelpFormatter();

        try {

            // parse options
            cl = parser.parse(opts, args);

            // handle interface option.
            fileName = cl.getOptionValue(fileNameOption.getOpt());
            if (fileName == null) {
                throw new ParseException("ファイル名が指定されていません。");
            }

            // handlet destination option.
            Long xl = null;
            try {
                xl = Long.parseUnsignedLong(cl.getOptionValue(limitOption.getOpt()));
            } catch (NumberFormatException e) {
                xl = null;
            } finally {
                limit = xl;
            }

            Set<Integer> x = new HashSet<>();
            List<String> ls = new ArrayList<>();
            ls.addAll(Arrays.asList(cl.getOptionValues(pidsOption.getOpt())));
            for (String s : ls) {
                try {
                    x.add(Integer.parseUnsignedInt(s, 16));
                } catch (NumberFormatException e) {
                    throw new ParseException(e.getMessage());
                }
            }
            pids = Collections.unmodifiableSet(x);

            System.out.println("Starting application...");
            System.out.println("filename   : " + fileName);
            System.out.println("limit : " + limit);
            System.out.println("pids : " + dumpSet(pids));

            // your code
            TsReader reader;
            if (limit == null) {
                reader = new TsReader(new File(fileName), pids);
            } else {
                reader = new TsReader(new File(fileName), pids, limit);
            }

            Map<Integer, List<TsPacketParcel>> ret = reader.getPackets();
            try {
                for (Integer pid : ret.keySet()) {

                    FileWriter writer = new FileWriter(fileName + "_" + Integer.toHexString(pid) + ".txt");

                    for (TsPacketParcel par : ret.get(pid)) {
                        String text = Hex.encodeHexString(par.getPacket().getData());
                        writer.write(text + "\n");

                    }
                    writer.flush();
                    writer.close();
                }
            } catch (IOException ex) {
                LOG.fatal("", ex);
            }

        } catch (ParseException e) {
            // print usage.
            help.printHelp("My Java Application", opts);
            throw e;
        } catch (FileNotFoundException ex) {
            LOG.fatal("", ex);
        }
    }

}
