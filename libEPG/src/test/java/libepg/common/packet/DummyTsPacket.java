/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libepg.common.packet;

import libepg.ts.packet.TsPacket;
import libepg.ts.packet.TsPacket.ADAPTATION_FIELD_CONTROL;
import libepg.ts.packet.TsPacket.PAYLOAD_UNIT_START_INDICATOR;
import libepg.ts.packet.TsPacket.TRANSPORT_SCRAMBLING_CONTROL;


/**
 * テスト用オブジェクト
 *
 * @author normal
 */
public class DummyTsPacket extends TsPacket {

    private byte[] Payload;
    private byte[] Adaptation_field;
    private int Continuity_counter;
    private ADAPTATION_FIELD_CONTROL Adaptation_field_control;
    private TRANSPORT_SCRAMBLING_CONTROL Transport_scrambling_control;
    private int pid;
    private int Transport_priority;
    private PAYLOAD_UNIT_START_INDICATOR Payload_unit_start_indicator;
    private int Transport_error_indicator;
    private int Sync_byte;

    private boolean isSetData = false;

    public DummyTsPacket(byte[] data) throws IllegalArgumentException {
        super(data);

    }

    public void setData() {
        if (this.isSetData == false) {
//            なぜかエラーになる。
//            this.Adaptation_field = super.getAdaptation_field();
//            this.Adaptation_field_control = super.getAdaptation_field_control();
//            this.Continuity_counter = super.getContinuity_counter();
//            this.Payload = super.getPayload();
//            this.Payload_unit_start_indicator = super.getPayload_unit_start_indicator();
//            this.Sync_byte = super.getSync_byte();
//            this.Transport_error_indicator = super.getTransport_error_indicator();
//            this.Transport_priority = super.getTransport_priority();
//            this.Transport_scrambling_control = super.getTransport_scrambling_control();
//            this.pid = super.getPid();
            this.isSetData = true;
        }
    }

    @Override
    public byte[] getPayload() {
        return Payload;
    }

    public void setPayload(byte[] Payload) {
        this.setData();
        this.Payload = Payload;
    }

    @Override
    public byte[] getAdaptation_field() {
        return Adaptation_field;
    }

    public void setAdaptation_field(byte[] Adaptation_field) {
        this.setData();
        this.Adaptation_field = Adaptation_field;
    }

    @Override
    public int getContinuity_counter() {
        return Continuity_counter;
    }

    public void setContinuity_counter(int Continuity_counter) {
        this.setData();
        this.Continuity_counter = Continuity_counter;
    }

    @Override
    public ADAPTATION_FIELD_CONTROL getAdaptation_field_control() {
        return Adaptation_field_control;
    }

    public void setAdaptation_field_control(ADAPTATION_FIELD_CONTROL Adaptation_field_control) {
        this.setData();
        this.Adaptation_field_control = Adaptation_field_control;
    }

    @Override
    public TRANSPORT_SCRAMBLING_CONTROL getTransport_scrambling_control() {
        return Transport_scrambling_control;
    }

    public void setTransport_scrambling_control(TRANSPORT_SCRAMBLING_CONTROL Transport_scrambling_control) {
        this.setData();
        this.Transport_scrambling_control = Transport_scrambling_control;
    }

    @Override
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.setData();
        this.pid = pid;
    }

    @Override
    public int getTransport_priority() {
        return Transport_priority;
    }

    public void setTransport_priority(int Transport_priority) {
        this.setData();
        this.Transport_priority = Transport_priority;
    }

    @Override
    public PAYLOAD_UNIT_START_INDICATOR getPayload_unit_start_indicator() {
        return Payload_unit_start_indicator;
    }

    public void setPayload_unit_start_indicator(PAYLOAD_UNIT_START_INDICATOR Payload_unit_start_indicator) {
        this.setData();
        this.Payload_unit_start_indicator = Payload_unit_start_indicator;
    }

    @Override
    public int getTransport_error_indicator() {
        return Transport_error_indicator;
    }

    public void setTransport_error_indicator(int Transport_error_indicator) {
        this.setData();
        this.Transport_error_indicator = Transport_error_indicator;
    }

    @Override
    public int getSync_byte() {
        return Sync_byte;
    }

    public void setSync_byte(int Sync_byte) {
        this.setData();
        this.Sync_byte = Sync_byte;
    }

    @Override
    public String toString() {
        return "ダミーパケット";
    }



    
    
}
