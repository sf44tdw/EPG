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
package channellistmaker.main;

import channellistmaker.dataextractor.KeyFields;
import channellistmaker.dataextractor.channel.AllChannelDataExtractor;
import channellistmaker.dataextractor.channel.Channel;
import channellistmaker.listmaker.EPGListMaker;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

/**
 *
 * @author normal
 */
public class Main {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            new Main().start(args);
            System.exit(0);
        } catch (ParseException ex) {
            LOG.fatal("オプションの解釈に失敗しました。", ex);
            System.exit(1);
        }
    }

    public void start(String[] args) throws org.apache.commons.cli.ParseException {

        final Option charSetOption = Option.builder("c")
                .required(false)
                .longOpt("charset")
                .desc("読み込み用文字コード")
                .hasArg()
                .type(String.class)
                .build();

        final Option directoryNameOption = Option.builder("d")
                .required()
                .longOpt("directoryname")
                .desc("ディレクトリ名")
                .hasArg()
                .type(String.class)
                .build();

        final Option destFileNameOption = Option.builder("f")
                .required()
                .longOpt("destname")
                .desc("保存先ファイル名")
                .hasArg()
                .type(String.class)
                .build();

        Options opts = new Options();
        opts.addOption(charSetOption);
        opts.addOption(directoryNameOption);
        opts.addOption(destFileNameOption);
        CommandLineParser parser = new DefaultParser();

        HelpFormatter help = new HelpFormatter();
        CommandLine cl = parser.parse(opts, args);

        final Charset charSet;
        try {
            charSet = Charset.forName(cl.getOptionValue(charSetOption.getOpt()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("読み込み用文字コードの指定が正しくありません。", e);
        }
        LOG.info("読み込み用文字コード = " + charSet);

        final File dirName = new File(cl.getOptionValue(directoryNameOption.getOpt()));
        if (!dirName.isDirectory()) {
            throw new IllegalArgumentException("読み込み先にディレクトリ以外が指定されたか、存在しません。");
        }
        LOG.info("読み込み先ディレクトリ = " + dirName.getAbsolutePath());

        final File destFile = new File(cl.getOptionValue(destFileNameOption.getOpt()));
        LOG.info("書き込み先ファイル = " + destFile.getAbsolutePath());

        final Set<Document> docs = new EPGListMaker(dirName, charSet).seek();

        Map<MultiKey<Integer>, Channel> channels = new AllChannelDataExtractor(docs).getAllEPGRecords();

        docs.clear();
        
        Set<MultiKey<Integer>> keys = channels.keySet();
        for (MultiKey<Integer> k : keys) {
            MessageFormat mf = new MessageFormat("トランスポートストリーム識別 = {0} オリジナルネットワーク識別 = {1} サービス識別 = {2} 物理チャンネル = {3} 放送局名 = {4}");
            Channel ch = channels.get(k);
            KeyFields kf = ch.getKeyfields();
            Object[] message2 = {kf.getTransportStreamId(), kf.getOriginalNetworkId(), kf.getServiceId(), ch.getPhysicalChannelNumber(), ch.getDisplayName()};
            LOG.info(mf.format(message2));
//            LOG.info(channels.get(k));
        }

    }
}
