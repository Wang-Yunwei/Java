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
public class GetTokenOup {

    @Schema(description = "云平台分配的用户ID")
    private Integer companyId;

    @Schema(description = "AccessToken")
    private String accessToken;
}
