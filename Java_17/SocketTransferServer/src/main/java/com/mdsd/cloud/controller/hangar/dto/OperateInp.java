package com.mdsd.cloud.controller.hangar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-09-03]
 */
@Getter
@Setter
@Accessors(chain = true)
public class OperateInp {

    @Schema(description = "机库ID",example = "H202409040001")
    private String hangarId;

    @Schema(description = "指令: 0x00-舱门, 0x01-推杆, 0x02-空调, 0x03-无人机, 0x04-充电操作",example = "0x00")
    private int command;

    @Schema(description = "动作: 0x01-开启, 0x02-关闭, 0x04: [0x01-开机, 0x02-关机, 0x03-待机, 0x04-充电, 0x05-检查]",example = "0x01")
    private int action;
}
