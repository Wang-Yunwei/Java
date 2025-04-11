package com.mdsd.cloud.response;

import com.mdsd.cloud.response.exception.BaseException;
import com.mdsd.cloud.response.exception.BaseResponseEnum;
import com.mdsd.cloud.response.exception.SystemException;

/**
 * @author WangYunwei [2020-07-07]
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class ResponseUtil<T> {

    /**
     * Wrap null response body of success.
     *
     * @return ResponseDto
     */

    public static ResponseDto wrapSuccess() {

        return new ResponseDto(BaseResponseEnum.SUCCESS);
    }

    /**
     * Wrap response body of success.
     *
     * @param body returned object
     * @return ResponseDto
     */
    public static ResponseDto wrapSuccess(final Object body) {

        return new ResponseDto(BaseResponseEnum.SUCCESS, body);
    }

    public static ResponseDto wrapExceptionLog(final String message, final Object body) {

        final String code = "40000";
        return new ResponseDto(code, message, body);
    }

    /**
     * @param code    error code
     * @param message error message
     * @return ResponseDto
     */
    public static ResponseDto wrapException(final String code, final String message) {

        return new ResponseDto(code, message);
    }

    /**
     * @param e ApplicationException
     * @return ResponseDto
     */
    public static ResponseDto wrapException(final BaseException e) {

        return new ResponseDto(e);
    }

    /**
     * @param e Exception
     * @return ResponseDto
     */
    public static ResponseDto wrapException(final Exception e) {

        return wrapException(new SystemException(e));
    }

    public static ResponseDto wrapException(final String code, final String message, final Object body) {

        return new ResponseDto(code, message, body);
    }
}
