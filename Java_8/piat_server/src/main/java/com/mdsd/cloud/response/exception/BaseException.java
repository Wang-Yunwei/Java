/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.mdsd.cloud.response.exception;

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -5430955945096897330L;

    private final String code;

    private String message;

    /**
     * default constructor.
     */
    public BaseException() {

        super(BaseResponseEnum.SYSTEM_EXCEPTION.getMessage());
        this.code = BaseResponseEnum.SYSTEM_EXCEPTION.getCode();
        this.message = BaseResponseEnum.SYSTEM_EXCEPTION.getMessage();

    }

    /**
     * @param message error message
     */
    public BaseException(final String message) {

        super(message);
        this.code = BaseResponseEnum.SYSTEM_EXCEPTION.getCode();
    }

    /**
     * @param code    error code
     * @param message error message
     */
    public BaseException(final String code, final String message) {

        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {

        return this.code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }
}
