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

import channellistmaker.channelfilemaker.ChannelDocumentMaker;
import channellistmaker.dataextractor.channel.AllChannelDataExtractor;
import channellistmaker.dataextractor.channel.Channel;
import channellistmaker.listmaker.EPGListMaker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
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
            System.exit(1);
        }
    }

    public void start(String[] args) throws ParseException {

        final Option charSetOption = Option.builder("c")
                .required(false)
                .longOpt("charset")
                .desc("読み込み、書き込み用文字コード。省略した場合、システムの標準設定を使う。")
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
        CommandLine cl;
        try {
            cl = parser.parse(opts, args);
        } catch (ParseException ex) {
            LOG.fatal("オプションの解釈に失敗しました。", ex);
            help.printHelp("My Java Application", opts);
            throw ex;
        }

        final Charset charSet;
        try {
            final String chersetstr = cl.getOptionValue(charSetOption.getOpt());
            if (chersetstr == null || "".equals(chersetstr)) {
                charSet = Charset.defaultCharset();
                LOG.info("文字コードの指定が省略されました。システムの文字コード設定を使用します。");
            } else {
                charSet = Charset.forName(chersetstr);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("文字コードの指定が正しくありません。", e);
        }
        LOG.info("文字コード = " + charSet);

        final File dirName = new File(cl.getOptionValue(directoryNameOption.getOpt()));
        if (!dirName.isDirectory()) {
            throw new IllegalArgumentException("読み込み先にディレクトリ以外が指定されたか、存在しません。");
        }
        LOG.info("読み込み先ディレクトリ = " + dirName.getAbsolutePath());

        final File destFile = new File(cl.getOptionValue(destFileNameOption.getOpt()));
        LOG.info("書き込み先ファイル = " + destFile.getAbsolutePath());

        final Set<Document> docs = new EPGListMaker(dirName, charSet).seek();

        final Map<MultiKey<Integer>, Channel> channels = new AllChannelDataExtractor(docs).getAllEPGRecords();

        final ChannelDocumentMaker dm = new ChannelDocumentMaker(channels);

        try (
                FileOutputStream fos = new FileOutputStream(destFile);
                OutputStreamWriter os = new OutputStreamWriter(fos, charSet);
                BufferedWriter bw = new BufferedWriter(os);) {
            bw.write(dm.getChannelList());
            bw.flush();
            os.flush();
            fos.flush();
            LOG.info("リストを保存しました。");
        } catch (IOException ex) {
            LOG.fatal("書き込み失敗", ex);
        }
    }
}
