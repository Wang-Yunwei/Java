package com.mdsd.cloud.controller.qualityControl.Vo;

import lombok.Data;

/**
 * 病人年龄段分布返回值
 * @author huxiaoneng
 * @version 1.0
 */
@Data
public class AgeDistributionCountVo {

    private String regAge;

    private String gender;

    private int count;

}
