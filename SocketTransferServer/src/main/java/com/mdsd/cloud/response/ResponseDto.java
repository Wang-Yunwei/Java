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

    private LocalDateTime timestamp = LocalDateTime.now();

    private T body;

    public ResponseDto() {
    }

    public ResponseDto(BusinessException e) {

        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public static <T> ResponseDto<T> wrapSuccess() {

        return new ResponseDto<T>();
    }

    public static <T> ResponseDto<T> wrapSuccess(final T body) {

        return new ResponseDto<T>().setBody(body);
    }

    public static <T> ResponseDto<T> wrapException(final RuntimeException e) {

        return wrapException(new RuntimeException(e));
    }

    public static <T> ResponseDto<T> wrapException(final BusinessException e) {

        return new ResponseDto<T>(e);
    }

}
