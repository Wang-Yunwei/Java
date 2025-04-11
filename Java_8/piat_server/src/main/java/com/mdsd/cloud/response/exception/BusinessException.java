package com.mdsd.cloud.response.exception;

public class BusinessException extends BaseException {

    public static final String DEFAULT_BUSINESS_CODE = BaseResponseEnum.BUSINESS_EXCEPTION.getCode();

    public static final String DEFAULT_BUSINESS_MESSAGE = BaseResponseEnum.BUSINESS_EXCEPTION.getMessage();

    private final String code;

    private final String message;

    /**
     * default constructor.
     */
    public BusinessException() {

        super();
        this.code = DEFAULT_BUSINESS_CODE;
        this.message = DEFAULT_BUSINESS_MESSAGE;
    }

    /**
     * @param exceptionEnum
     */
    public BusinessException(final BaseResponseEnum exceptionEnum) {

        super();
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    /**
     * @param message error message
     */
    public BusinessException(final String message) {

        super(DEFAULT_BUSINESS_CODE, message);
        this.code = DEFAULT_BUSINESS_CODE;
        this.message = message;
    }

    /**
     * @param code    error code
     * @param message error message
     */
    public BusinessException(final String code, final String message) {

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
