package com.mdsd.cloud.controller.pc.medicalrecord.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-05-25]
 */
@Getter
@Setter
@ApiModel(value = "TimeAxisUpdatePo对象", description = "病历管理 - 修改时间轴_入参")
public class TimeAxisUpdatePo extends TimeAxisRo {

}
