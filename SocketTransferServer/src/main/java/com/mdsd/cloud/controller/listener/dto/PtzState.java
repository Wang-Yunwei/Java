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
public class PtzState {

    @Schema(description = "俯仰角 (范围: -120° ~ 30°)")
    private Float pitch;

    @Schema(description = "横滚角 (范围: -90° ~ 60°)")
    private Float roll;

    @Schema(description = "偏航角 (范围: -180° ~ 180°)")
    private Float yaw;

    @Schema(description = "云台模式: 1-自由模式,2-跟随模式,3-FPV模式")
    private Byte gimbalMode;

}
