package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 * 来诊时间段分布统计返回
 */
@Data
public class TimePeriodDistributionTimeCountVo {

    private String triageTime;

    private int count;
}
