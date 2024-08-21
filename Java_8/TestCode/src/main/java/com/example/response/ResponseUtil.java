package com.example.response;

import com.example.response.exception.BaseException;
import com.example.response.exception.BaseResponseEnum;
import com.example.response.exception.SystemException;

import java.io.Serializable;

/**
 * @author WangYunwei [2020-07-07]
 */
public final class ResponseUtil<T> implements Serializable {

    public static <T> ResponseDto<T> wrapSuccess() {

        return new ResponseDto<T>(BaseResponseEnum.SUCCESS);
    }

    public static <T> ResponseDto<T> wrapSuccess(final T body) {

        return new ResponseDto<T>(BaseResponseEnum.SUCCESS, body);
    }

    public static <T> ResponseDto<T> wrapException(final String code, final String message) {

        return new ResponseDto<T>(code, message);
    }

    public static <T> ResponseDto<T> wrapException(final BaseException e) {

        return new ResponseDto<T>(e);
    }

    public static <T> ResponseDto<T> wrapException(final Exception e) {

        return wrapException(new SystemException(e));
    }

    public static <T> ResponseDto<T> wrapException(final String code, final String message, final T body) {

        return new ResponseDto<T>(code, message, body);
    }
}
