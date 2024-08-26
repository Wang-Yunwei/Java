package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
public class InstructDTO {

    @Schema(description = "指令号")
    private Integer instructId;

    @Schema(description = "指令名称")
    private String instructName;
}
