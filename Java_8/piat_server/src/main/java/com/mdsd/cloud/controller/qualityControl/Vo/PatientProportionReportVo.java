package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author huxiaoneng
 * @version 1.0
 * 各级患者比例返回 月度年度
 */
@Data
public class PatientProportionReportVo {

    private String resetLevel;

    private int peopleNumber;

    private String proportion;

}
