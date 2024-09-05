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

    @Schema(description = "机库ID",example = "H202409040001")
    private String hangarId;

    @Schema(description = "指令: 0x00-舱门, 0x10-推杆, 0x20-空调, 0x30-无人机, 0x40-充电操作",example = "0x00")
    private int command;

    @Schema(description = "动作: 0x01-开启, 0x02-关闭, 0x40: [0x01-开机, 0x02-关机, 0x03-待机, 0x04-充电, 0x05-检查]",example = "0x01")
    private int action;
}
