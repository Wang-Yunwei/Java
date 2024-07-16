package com.mdsd.cloud.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2024-07-11]
 */
@Getter
@Setter
public final class ResponseDto<T> implements Serializable {

    @Schema(description = "业务状态码,除000000以外都是错误状态")
    private String code = "000000";

    private String message = "SUCCESS";

    private LocalDateTime responseTime = LocalDateTime.now();

    private T body;

    public static <T> ResponseDto<T> wrapSuccess() {

        return new ResponseDto<T>();
    }

    public static <T> ResponseDto<T> wrapSuccess(final T body) {

        ResponseDto<T> tResponseDto = new ResponseDto<>();
        tResponseDto.setBody(body);
        return tResponseDto;
    }
}
