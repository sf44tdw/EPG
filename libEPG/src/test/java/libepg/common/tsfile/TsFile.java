/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.common.tsfile;

import java.io.File;

/**
 * さすがにテスト用のファイルは同梱できない。
 * @author normal
 */
public class TsFile {
     public static final String TS_FILE_NAME =  "H:/_D20160321161346P21020T90C101.ts";

    public static File getTsFile() {
        return new File(TS_FILE_NAME);
    }


     
    
}
