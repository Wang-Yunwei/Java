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
package com.example.response.exception;

/**
 * Parameter exception.
 */
public class ParameterException extends BaseException {

    private static final long serialVersionUID = 6257945742204588120L;

    /**
     * DEFAULT_PARAM_CODE.
     */
    public static final String DEFAULT_PARAM_CODE = BaseResponseEnum.PARAMETER_EXCEPTION.getCode();

    /**
     * DEFAULT_PARAM_MESSAGE.
     */
    public static final String DEFAULT_PARAM_MESSAGE = BaseResponseEnum.PARAMETER_EXCEPTION.getMessage();

    private final String code;

    private final String message;

    /**
     * default constructor.
     */
    public ParameterException() {

        super();
        this.code = DEFAULT_PARAM_CODE;
        this.message = DEFAULT_PARAM_MESSAGE;
    }

    /**
     * @param message error message
     */
    public ParameterException(final String message) {

        super(DEFAULT_PARAM_CODE, message);
        this.code = DEFAULT_PARAM_CODE;
        this.message = message;
    }

    /**
     * @param code    error code
     * @param message error message
     */
    public ParameterException(final String code, final String message) {

        super(code, message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {

        return this.code;
    }

    @Override
    public String getMessage() {

        return this.message;
    }
}
