package com.mdsd.cloud.controller.cardnumbermanage.dto;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/18 16:57
 */
@Data
public class HisSearchParamDto extends BaseDto {

    /**
     * 查询条件开始时间
     */

    private LocalDateTime startTime;

    /**
     * 查询条件结束时间
     */

    private LocalDateTime endTime;

    /**
     * 患者名称
     */
    private String name;

    /**
     * 就诊卡号
     */
    private String cardId;

}
