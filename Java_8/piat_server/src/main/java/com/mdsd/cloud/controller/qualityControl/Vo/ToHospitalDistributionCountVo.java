package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 * 查询来院方式统计count
 */
@Data
public class ToHospitalDistributionCountVo {

    private String arriveType;

    private int count;
}
