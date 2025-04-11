package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 * 特殊患者统计返回
 */
@Data
public class ReceptionStatisticsCountDetailVo {

    /**
     * 患者类型
     */
    private String patientType;

    private int count;

    public ReceptionStatisticsCountDetailVo(String patientType, int count) {
        this.patientType = patientType;
        this.count = count;
    }
}
