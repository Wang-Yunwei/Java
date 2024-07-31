package com.mdsd.cloud.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author WangYunwei [2024-07-11]
 */
@Getter
@Setter
@Accessors(chain = true)
public final class ResponseDto<T> implements Serializable {

    @Schema(description = "业务状态码,除000000以外都是错误状态")
    private String code = "000000";

    private String message = "SUCCESS";

    private Date timestamp = new Date();

    private T body;

    public static <T> ResponseDto<T> wrapSuccess() {

        return new ResponseDto<T>();
    }

    public static <T> ResponseDto<T> wrapSuccess(final T body) {

        return new ResponseDto<T>().setBody(body);
    }

    public static <T> ResponseDto<T> wrapParamError(final T body) {

        return new ResponseDto<T>().setBody(body).setCode("000001").setMessage("PARAMETER ERROR");
    }
}
