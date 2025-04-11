package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 * 来诊时间段分布查询条件
 */
@Data
public class TimePeriodDistributionRequestVo {

    private String triageTime;

    private String startTime;

    private String endTime;
}
