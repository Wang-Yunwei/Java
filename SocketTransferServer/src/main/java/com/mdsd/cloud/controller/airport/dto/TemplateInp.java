package com.mdsd.cloud.controller.airport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-08-09]
 */
@Getter
@Setter
public class TemplateInp {

    @Schema(description = "机库编号")
    private String hangarId;

    @Schema(description = "机场列表接口中InstructDTO对象的instructId")
    private Integer instructId;
}
