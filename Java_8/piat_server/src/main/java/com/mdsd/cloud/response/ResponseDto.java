package com.mdsd.cloud.response;

import com.mdsd.cloud.response.exception.BaseException;
import com.mdsd.cloud.response.exception.IResponse;

import java.time.LocalDateTime;

/**
 * @author WangYunwei [2020-07-06]
 */
public class ResponseDto<T> implements IResponse<T> {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private LocalDateTime responseTime = LocalDateTime.now();

    private T body;

    /**
     * default constructor
     */
    public ResponseDto() {

    }

    /**
     * @param response returned body
     */
    public ResponseDto(final IResponse<T> response) {

        this.code = response.getCode();
        this.message = response.getMessage();
        this.body = response.getBody();
    }

    /**
     * @param response IResponse
     * @param body     returned body
     */
    public ResponseDto(@SuppressWarnings("rawtypes") final IResponse response, final T body) {

        this.code = response.getCode();
        this.message = response.getMessage();
        this.body = body;
    }

    /**
     * @param code    error code
     * @param message error message
     */
    public ResponseDto(final String code, final String message) {

        this.code = code;
        this.message = message;
    }

    /**
     * @param code    error code
     * @param message error message
     * @param body    returned body
     */
    public ResponseDto(final String code, final String message, final T body) {

        super();
        this.code = code;
        this.message = message;
        this.body = body;
    }

    /**
     * @param e ApplicationException
     */
    public ResponseDto(final BaseException e) {

        this.code = e.getCode();
        this.message = e.getMessage();
    }

    @Override
    public String getCode() {

        return this.code;
    }

    public void setCode(final String code) {

        this.code = code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }

    public void setMessage(final String message) {

        this.message = message;
    }

    public LocalDateTime getResponseTime() {

        return this.responseTime;
    }

    public void setTime(final LocalDateTime responseTime) {

        this.responseTime = responseTime;
    }

    @Override
    public T getBody() {

        return this.body;
    }

    public void setBody(final T body) {

        this.body = body;
    }

}
