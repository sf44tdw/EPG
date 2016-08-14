/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package channellistmaker.listmaker;

import channellistmaker.listmaker.fileseeker.FileSeeker;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.w3c.dom.Document;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 指定されたディレクトリからxmlファイルを捜索する。サブディレクトリは探査しない。
 *
 * @author dosdiaopfhj
 */
public class EPGListMaker {

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = LogFactory.getLog(myClass);
    }
    private final FileSeeker seeker;

    private static final IOFileFilter SUFFIX_1 = FileFilterUtils.suffixFileFilter("xml");
    private static final IOFileFilter SUFFIX_2 = FileFilterUtils.suffixFileFilter("Xml");
    private static final IOFileFilter SUFFIX_3 = FileFilterUtils.suffixFileFilter("xMl");
    private static final IOFileFilter SUFFIX_4 = FileFilterUtils.suffixFileFilter("xmL");
    private static final IOFileFilter SUFFIX_5 = FileFilterUtils.suffixFileFilter("XMl");
    private static final IOFileFilter SUFFIX_6 = FileFilterUtils.suffixFileFilter("xML");
    private static final IOFileFilter SUFFIX_7 = FileFilterUtils.suffixFileFilter("XmL");
    private static final IOFileFilter SUFFIX_8 = FileFilterUtils.suffixFileFilter("XML");

    private final Charset charset;

    /**
     * @param sourceDir ファイルを検索するディレクトリ
     * @param charset 読み込むファイルの文字コード。
     */
    public EPGListMaker(File sourceDir, Charset charset) {
        this.charset = charset;
        seeker = new FileSeeker(sourceDir, FileFilterUtils.or(SUFFIX_1, SUFFIX_2, SUFFIX_3, SUFFIX_4, SUFFIX_5, SUFFIX_6, SUFFIX_7, SUFFIX_8));
        seeker.setRecursive(false);
    }

    /**
     * EPG XMLファイルの検索を行い、見つかったファイルを読み込む。
     *
     * @return 見つかったファイルを変換したDocumentオブジェクトのリスト。読み込みに失敗したファイルは無視する。
     */
    public synchronized Set<Document> seek() {
        Set<Document> EPGs = Collections.synchronizedSet(new HashSet<Document>());
        List<File> FL = this.seeker.seek();
        for (File F : FL) {
            Document d = new XMLLoader(charset).Load(F);
            if (d != null) {
                LOG.info("EPGファイルが読み込まれました。 EPG FILE = " + F.toString());
                EPGs.add(d);
            } else {
                LOG.warn("EPGファイルが読み込まれませんでした。このファイルは無視されます。 EPG FILE = " + F.toString());
            }
        }
        return Collections.unmodifiableSet(EPGs);
    }
}
