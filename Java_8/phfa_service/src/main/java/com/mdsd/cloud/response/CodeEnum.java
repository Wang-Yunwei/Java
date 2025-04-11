package com.mdsd.cloud.response;

/**
 * @author Wenjun Gu
 */
public enum CodeEnum {
    UNKNOWN("-1"),
    SUCCESS("0"),
    UNAUTHORIZED("401");

    private final String value;

    private CodeEnum(final String value) {

        this.value = value;
    }

    @Override
    public String toString() {

        return this.value;
    }

    public String getValue() {

        return this.value;
    }
}
