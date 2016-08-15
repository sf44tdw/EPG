/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.epg.section.body.servicedescriptiontable;

import libepg.util.bytearray.ByteConverter;
import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.Objects;
import org.apache.commons.logging.Log;
import libepg.epg.section.descriptor.DescriptorsLoop;
import libepg.util.LoggerFactory;
import libepg.util.bytearray.ByteDataBlock;

/**
 * サービス記述テーブルの繰り返し項目
 *
 * @author normal
 */
public final class ServiceDescriptionTableRepeatingPart {

    /**
     * falseのとき、このクラスはログを出さなくなる
     */
    public static final boolean CLASS_LOG_OUTPUT_MODE = true;

    private static final Log LOG;

    static {
        final Class<?> myClass = MethodHandles.lookup().lookupClass();
        LOG = new LoggerFactory(myClass, ServiceDescriptionTableRepeatingPart.CLASS_LOG_OUTPUT_MODE).getLOG();
    }

    private final ByteDataBlock data;

    ServiceDescriptionTableRepeatingPart(byte[] data) {
        this.data = new ByteDataBlock(data);
    }

    public byte[] getData() {
        return data.getData();
    }

    //繰り返し
    /**
     * service_id(サービス識別):これは16ビットのフィールドで、<br>
     * そのトランスポートストリーム内の他のサービスからこのサービスを識別するためのラベルの役割をする。<br>
     * サービス識別は、対応するプログラムマップセクション内の放送番組番号識別(program_number)に等しい<br>
     *
     * @return
     */
    public synchronized int getService_id() {
        byte[] t = new byte[2];
        System.arraycopy(this.data.getData(), 0, t, 0, t.length);
        return ByteConverter.bytesToInt(t);
    }

    /**
     * 符号化ビットストリームを定義する項の中で使用する場合、 <br>
     * その値が将来、ARIBSTD-B10が定義する拡張子として使用されるかもしれないことを表す。 3bit。<br>
     *
     * @return
     */
    public synchronized int getReserved_future_use1() {
        int temp;
        temp = ByteConverter.byteToInt(this.data.getData()[2]);
        temp = temp >>> 5;
        return temp;
    }

    /**
     * EIT_user_defined_flags(EIT事業者定義フラグ):この3ビットのフィールドは、<br>
     * EITの送出有無を示す拡張として、放送事業者が独自に定義することができる。 <br>
     * 使用しない場合は、「111」とする。<br>
     *
     * @return
     */
    public synchronized int getEIT_user_defined_flags() {
        int temp;
        temp = ByteConverter.byteToInt(this.data.getData()[2]);
        temp = temp >>> 2;
        temp = temp & 0x07;
        return temp;
    }

    /**
     * EIT_schedule_flag(EIT[スケジュール]フラグ):この1ビットのフィールドは、<br>
     * それが「1」の場合はそのサービスのEIT[スケジュール]情報が現在のトランスポートストリーム内にあることを示す。<br>
     * (EIT[スケジュール]サブテーブルの最大送出間隔については「ガイドライン」を参照。）<br>
     * このフラグが「0」の場合は、そのサービスのEIT[スケジュール]情報が現在のトランスポートストリーム内には存在しないことを表す。<br>
     *
     * @return
     */
    public synchronized int getEIT_schedule_flag() {
        int temp;
        temp = ByteConverter.byteToInt(this.data.getData()[2]);
        temp = temp >>> 1;
        temp = temp & 0x01;
        return temp;
    }

    /**
     * EIT_present_following_flag(EIT[現在/次]フラグ):この1ビットのフィールドは、<br>
     * それが「1」の場合はそのサービスのEIT[現在/次]情報が現在のトランスポートストリーム内にあることを示す。<br>
     * (EIT[現在/次]サブテーブルの最大送出間隔については「ガイドライン」を参照。)<br>
     * このフラグが「0」の場合は、そのサービスのEIT[現在/次]情報が現在のトランスポートストリーム内には存在しないことを表す。<br>
     *
     * @return
     */
    public synchronized int getEIT_present_following_flag() {
        int temp;
        temp = ByteConverter.byteToInt(this.data.getData()[2]);
        temp = temp & 0x01;
        return temp;
    }

    /**
     * running_status(進行状態):この3ビットフィールドは、サービスの状態を示す。<br>
     * 0__=_未定義<br>
     * 1__=_非実行中<br>
     * 2__=_数秒以内に開始（例：映像記録用）<br>
     * 3__=_停止中<br>
     * 4__=_実行中<br>
     * 5-7=_将来の使用のためにリザーブ<br>
     *
     * @return
     */
    public synchronized int getRunning_status() {
        int temp;
        temp = ByteConverter.byteToInt(this.data.getData()[3]);
        temp = temp >>> 5;
        return temp;
    }

    /**
     * free_CA_mode(スクランブル):この1ビットのフィールドは、
     * それが「0」の場合はそのサービス内の全てのコンポーネントストリームがスクランブルされていないことを示す。
     * 「1」の場合は、一つ以上のストリームへのアクセスがCAシステムにより制御されていることを示す。
     *
     * @return
     */
    public synchronized int getFree_CA_mode() {
        int temp;
        temp = ByteConverter.byteToInt(this.data.getData()[3]);
        temp = temp >>> 4;
        temp = temp & 0x01;
        return temp;
    }

    /**
     * descriptors_loop_length(記述子ループ長):これは、12ビットのフィールドで、 後続の記述子の全バイト長を規定する
     *
     * @return
     */
    public synchronized int getDescriptors_loop_length() {
        byte[] t = new byte[2];
        System.arraycopy(this.data.getData(), 3, t, 0, t.length);
        int it = ByteConverter.bytesToInt(t);
        it = it & 0x0FFF;
        return it;
    }

    /**
     * 記述子ループを取得する。
     *
     * @return
     */
    public synchronized DescriptorsLoop getDescriptors_loop() {
        byte[] t = new byte[this.getDescriptors_loop_length()];
        if (t.length > 0) {
            System.arraycopy(this.data.getData(), 5, t, 0, t.length);
        }
        return new DescriptorsLoop(t);
    }

    private static final MessageFormat SDT_RP_DESC = new MessageFormat("バイト列 = {0}\n"
            + "サービス識別 = {1}\n"
            + "予約 = {2}\n"
            + "EIT事業者定義フラグ = {3}\n"
            + "EIT[スケジュール]フラグ = {4}\n"
            + "EIT[現在/次]フラグ = {5}\n"
            + "進行状態 = {6}\n"
            + "スクランブル = {7}\n"
            + "記述子ループ長 = {8}\n"
            + "記述子ループ = {9}\n"
    );

    @Override
    public String toString() {
        Object[] parameters = {this.data.toString(),
            Integer.toHexString(this.getService_id()),
            Integer.toHexString(this.getReserved_future_use1()),
            Integer.toHexString(this.getEIT_user_defined_flags()),
            Integer.toHexString(this.getEIT_schedule_flag()),
            Integer.toHexString(this.getEIT_present_following_flag()),
            Integer.toHexString(this.getRunning_status()),
            Integer.toHexString(this.getFree_CA_mode()),
            this.getDescriptors_loop_length(),
            this.getDescriptors_loop().toString()};
        return SDT_RP_DESC.format(parameters);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.data);
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
        final ServiceDescriptionTableRepeatingPart other = (ServiceDescriptionTableRepeatingPart) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

}
