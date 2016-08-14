/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section;

import java.text.MessageFormat;
import java.util.Objects;
import org.apache.commons.codec.binary.Hex;
import util.bytearray.ByteDataBlock;

/**
 * セクション本体部分のバイト列を保管する。
 *
 * @author normal
 */
public class SectionBody {
    
    private final TABLE_ID tableID;
    private final ByteDataBlock data;

    /**
     * 基底クラスから生成する。具体的なフィールドを定義したサブクラスの生成で使用する。
     *
     * @param sectionBody
     */
    public SectionBody(SectionBody sectionBody) {
        this.tableID = sectionBody.tableID;
        this.data = sectionBody.data;
        if (this.getClass() != this.tableID.getDataType()) {
            MessageFormat msg1 = new MessageFormat("このクラスでは渡されたデータをパースできません。このクラスの型={0} データの型={1} データ={2}");
            Object[] parameters1 = {this.getClass(), this.tableID.getDataType(), Hex.encodeHexString(this.data.getData())};
            throw new IllegalArgumentException(msg1.format(parameters1));
        }
    }

    /**
     * データとテーブルIDから生成する。Sectionオブジェクトのみが使用する。
     *
     * @param tableID
     * @param data
     */
    SectionBody(TABLE_ID tableID, byte[] data) {
        this.tableID = tableID;
        this.data = new ByteDataBlock(data);
    }

    /**
     * このクラスが管理するデータが存在していたセクションのテーブルIDを取得する。
     *
     * @return テーブルID
     */
    public TABLE_ID getTableID() {
        return tableID;
    }

    /**
     * セクション本体のバイト列を取得する。
     *
     * @return セクション本体
     */
    public final synchronized byte[] getData() {
        return this.data.getData();
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.tableID);
        hash = 73 * hash + Objects.hashCode(this.data);
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
        final SectionBody other = (SectionBody) obj;
        if (this.tableID != other.tableID) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }
    
    private static final MessageFormat SECTION_BODY_DESC = new MessageFormat(
            "テーブル識別値 = {0}\n"
            + "バイト列 = {1}\n"
    );
    
    @Override
    public String toString() {
        Object[] parameters = {this.getTableID(),
            this.data.toString()};
        return SECTION_BODY_DESC.format(parameters);
    }
    
}
