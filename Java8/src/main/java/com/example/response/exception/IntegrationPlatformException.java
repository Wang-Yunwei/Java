package com.example.response.exception;

/**
 * @author WangYunwei [2020-02-28]
 */
public class IntegrationPlatformException extends RuntimeException {

    /**
     * 错误代码
     */
    private final String errorCode;

    /**
     * 错误名称
     */
    private final String errorName;

    /**
     * 错误消息
     */
    private final String errorMessage;

    /**
     * 唯一关键字
     */
    private final String priKey;

    /**
     * 出错的实体名
     */
    private final String entityName;

    /**
     * 预留字段1
     */
    private final String reserved1;

    /**
     * 预留字段2
     */
    private final String reserved2;

    public IntegrationPlatformException(final String errorCode, final String errorName, final String errorMessage,
                                        final String priKey, final String entityName, final String reserved1,
                                        final String reserved2) {

        super();
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
        this.priKey = priKey;
        this.entityName = entityName;
        this.reserved1 = reserved1;
        this.reserved2 = reserved2;
    }

    public String getErrorCode() {

        return this.errorCode;
    }

    public String getErrorName() {

        return this.errorName;
    }

    public String getErrorMessage() {

        return this.errorMessage;
    }

    public String getPriKey() {

        return this.priKey;
    }

    public String getEntityName() {

        return this.entityName;
    }

    public String getReserved1() {

        return this.reserved1;
    }

    public String getReserved2() {

        return this.reserved2;
    }

}
