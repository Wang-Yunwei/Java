package com.mdsd.cloud.controller.hangar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-09-03]
 */
@Getter
@Setter
public class OperateInp {

    @Schema(description = "机库ID",example = "M31220230425010")
    private String hangarId;

    @Schema(description = "指令: 0x00-舱门, 0x10-推杆, 0x20-空调, 0x30-无人机",example = "0x00")
    private int command;

    @Schema(description = "动作: 0x01-开启, 0x02-关闭",example = "0x01")
    private int action;
}
