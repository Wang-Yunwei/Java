package com.mdsd.cloud.controller.tyjw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
public class GetTokenInp {

    @Schema(description = "AccessKeyID")
    private String accessKeyId = "qmfmd3Wy7M4dMcylnrTU";

    @Schema(description = "AccessKeySecret")
    private String accessKeySecret = "5WBU2uEhHZaVefpZdaxpFh4LgW9fZZaa";

    @Schema(description = "encryptStr=MD5(AccessKeyID+AccessKeySecret+timeStamp)")
    private String encryptStr;

    @Schema(description = "从1970年1月1日（UTC/GMT的午夜）开始所经过的秒毫,默认当前时间")
    private Long timeStamp = System.currentTimeMillis();
}
