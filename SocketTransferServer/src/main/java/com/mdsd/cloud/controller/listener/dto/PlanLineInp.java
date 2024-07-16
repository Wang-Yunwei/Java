package com.mdsd.cloud.controller.listener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class PlanLineInp extends BaseInp{

    @Schema(description = "航线数据", type = "byte[]")
    private byte[] data;
}
