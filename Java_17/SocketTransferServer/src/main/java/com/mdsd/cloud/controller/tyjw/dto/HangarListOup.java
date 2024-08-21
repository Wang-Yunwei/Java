package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
@Accessors(chain = true)
public class HangarListOup {

    @Schema(description = "大疆机场")
    private DjDTO airportDj;

    @Schema(description = "充电机场")
    private RechargeDTO airportRecharge;

    @Schema(description = "换电机场")
    private ReplaceBatteryDTO replaceBattery;

}
