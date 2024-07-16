package com.mdsd.cloud.controller.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2024-07-12]
 */
@Getter
@Setter
@Accessors(chain = true)
public class GetTokenInp {


    @Schema(description = "AccessKeyID")
    private String accessKeyId;

    @Schema(description = "encryptStr=MD5(AccessKeyID+AccessKeySecret+timeStamp)")
    private String encryptStr;

    @Schema(description = "从1970年1月1日（UTC/GMT的午夜）开始所经过的秒毫,默认当前时间")
    private Long timeStamp = System.currentTimeMillis();
}
