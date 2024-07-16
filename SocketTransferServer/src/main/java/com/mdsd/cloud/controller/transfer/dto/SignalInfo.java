package com.mdsd.cloud.controller.transfer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class SignalInfo {

    @Schema(description = "入网模式:NR5G-SA,NR5G-NSA，LTE")
    private String mode;

    @Schema(description = "NR5G-SA,NR5G-NSA,LTE")
    private String mcc;

    @Schema(description = "NR5G-SA,NR5G-NSA,LTE")
    private String mnc;

    @Schema(description = "NR5G-SA,LTE")
    private String cellid;

    @Schema(description = "NR5G-SA,NR5G-NSA,LTE")
    private String pcid;

    @Schema(description = "NR5G-SA,LTE")
    private String tac;

    @Schema(description = "NR5G-SA,NR5G-NSA")
    private String arfcn;

    @Schema(description = "NR5G-SA,NR5G-NSA")
    private String band;

    @Schema(description = "NR5G-SA")
    private String nr_dl_bandwidth;

    @Schema(description = "NR5G-SA,NR5G-NSA,LTE")
    private String rsrp;

    @Schema(description = "NR5G-SA,NR5G-NSA,LTE")
    private String sinr;

    @Schema(description = "NR5G-SA,LTE")
    private String tx_power;

    @Schema(description = "NR5G-SA,LTE")
    private String srxlev;

    @Schema(description = "LTE")
    private String cqi;

    @Schema(description = "LTE")
    private String earfcn;

    @Schema(description = "LTE")
    private String freq_band_ind;

    @Schema(description = "LTE")
    private String ul_bandwidth;

    @Schema(description = "LTE")
    private String dl_bandwidth;

    @Schema(description = "LTE")
    private String is_tdd;

    @Schema(description = "LTE")
    private String rssi;

    @Schema(description = "NR5G-SA")
    private String duplex_mode;

    @Schema(description = "经度")
    private Double lng;

    @Schema(description = "纬度")
    private Double lat;

    @Schema(description = "高度,单位:米")
    private Float height;

    @Schema(description = "时间戳,单位:秒")
    private Long timestamp;

    @Schema(description = "云盒状态: 1-线缆故障,2-OSDK未激活,3-正常,4-云盒固件更新")
    private Byte boxstate;

    @Schema(description = "运营商")
    private String isp;

    @Schema(description = "模组芯片温度,单位:摄氏度")
    private Byte tempMax;

    @Schema(description = "prx路径的rsrp值")
    private String prx_rsrp;

    @Schema(description = "drx路径的rsrp值")
    private String drx_rsrp;

    @Schema(description = "rx2路径的rsrp值")
    private String rx2_rsrp;

    @Schema(description = "rx3路径的rsrp值")
    private String rx3_rsrp;

    @Schema(description = "云盒编号")
    private String box_sn;

    @Schema(description = "当前使用的SIM卡在卡槽中的位置: 1 or 2")
    private Byte sim_use_seat;

}
