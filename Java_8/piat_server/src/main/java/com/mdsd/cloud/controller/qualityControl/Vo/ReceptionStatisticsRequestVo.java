package com.mdsd.cloud.controller.qualityControl.Vo;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 * 特殊患者查询条件
 */
@Data
public class ReceptionStatisticsRequestVo extends BaseDto {

    private String startTime;

    private String endTime;

}
