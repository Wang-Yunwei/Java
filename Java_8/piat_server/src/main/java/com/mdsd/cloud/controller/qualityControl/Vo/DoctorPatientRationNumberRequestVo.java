package com.mdsd.cloud.controller.qualityControl.Vo;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import lombok.Data;

/**
 * @author huxiaoneng
 * @version 1.0
 */
@Data
public class DoctorPatientRationNumberRequestVo extends BaseDto {

    /**
     * 开始年份
     */
    private String startTime;

    /**
     * 结束年份
     */
    private String endTime;

}
