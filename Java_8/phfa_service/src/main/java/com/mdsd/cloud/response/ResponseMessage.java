package com.mdsd.cloud.response;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Wenjun Gu
 */
public class ResponseMessage<T> implements Serializable {

    private static final long serialVersionUID = -1262175309463137081L;

    /**
     * 错误消息
     */
    @Getter
    @Setter
    protected String message;

    /**
     * 成功时响应内容
     */
    @Getter
    @Setter
    protected T result;

    /**
     * 状态码
     */
    @Getter
    @Setter
    protected int status;

    /**
     * 业务自定义状态码
     */
    @Getter
    @Setter
    protected String code;

    /**
     * 响应内容的字段
     */
    @Getter
    @Setter
    protected LinkedHashSet<String> fields;

    /**
     * 时间戳
     */
    @Getter
    @Setter
    protected Long timestamp;

    /**
     * 过滤字段：指定需要序列化的字段
     */
    @Getter
    protected transient Map<Class<?>, Set<String>> includes;

    /**
     * 过滤字段：指定不需要序列化的字段
     */
    @Getter
    protected transient Map<Class<?>, Set<String>> excludes;

    public ResponseMessage() {

    }

    public static <T> ResponseMessage<T> error(final String message) {

        return error(500, CodeEnum.UNKNOWN.getValue(), message);
    }

    public static <T> ResponseMessage<T> error(final String message, final Object... args) {

        return error(500, message, args);
    }

    public static <T> ResponseMessage<T> error(final int status, final String message) {

        return error(status, CodeEnum.UNKNOWN.getValue(), message);
    }

    public static <T> ResponseMessage<T> error(final int status, final String message, final Object... args) {

        return error(status, CodeEnum.UNKNOWN.getValue(), message, args);
    }

    public static <T> ResponseMessage<T> error(final int status, final String code, final String message) {

        return error(status, code, message, null);
    }

    public static <T> ResponseMessage<T> error(final int status, final String code, final String message, final Object... args) {

        final ResponseMessage<T> msg = new ResponseMessage<>();
        msg.message = StrUtil.format(message, args);
        msg.status(status);
        msg.code(code);
        return msg.putTimeStamp();
    }

    public static <T> ResponseMessage<T> ok() {

        return ok(null);
    }

    public static <T> ResponseMessage<T> ok(final T result) {

        return new ResponseMessage<T>()
                .result(result)
                .putTimeStamp()
                .code(CodeEnum.SUCCESS.getValue())
                .status(200);
    }

    private ResponseMessage<T> putTimeStamp() {

        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ResponseMessage<T> result(final T result) {

        this.result = result;
        return this;
    }

    public ResponseMessage<T> include(final Class<?> type, final String... fields) {

        return this.include(type, Arrays.asList(fields));
    }

    public ResponseMessage<T> include(final Class<?> type, final Collection<String> fields) {

        if (this.includes == null) {
            this.includes = new HashMap<>();
        }
        if (fields == null || fields.isEmpty()) {
            return this;
        }
        fields.forEach(field -> {
            if (field.contains(".")) {
                final String[] tmp = field.split("[.]", 2);
                try {
                    final Field field1 = type.getDeclaredField(tmp[0]);
                    if (field1 != null) {
                        this.include(field1.getType(), tmp[1]);
                    }
                } catch (final Throwable e) {
                }
            } else {
                this.getStringListFromMap(this.includes, type).add(field);
            }
        });
        return this;
    }

    public ResponseMessage<T> exclude(final Class type, final Collection<String> fields) {

        if (this.excludes == null) {
            this.excludes = new HashMap<>();
        }
        if (fields == null || fields.isEmpty()) {
            return this;
        }
        fields.forEach(field -> {
            if (field.contains(".")) {
                final String[] tmp = field.split("[.]", 2);
                try {
                    final Field field1 = type.getDeclaredField(tmp[0]);
                    if (field1 != null) {
                        this.exclude(field1.getType(), tmp[1]);
                    }
                } catch (final Throwable e) {
                }
            } else {
                this.getStringListFromMap(this.excludes, type).add(field);
            }
        });
        return this;
    }

    public ResponseMessage<T> exclude(final Collection<String> fields) {

        if (this.excludes == null) {
            this.excludes = new HashMap<>();
        }
        if (fields == null || fields.isEmpty()) {
            return this;
        }
        final Class type;
        if (this.getResult() != null) {
            type = this.getResult().getClass();
        } else {
            return this;
        }
        this.exclude(type, fields);
        return this;
    }

    public ResponseMessage<T> include(final Collection<String> fields) {

        if (this.includes == null) {
            this.includes = new HashMap<>();
        }
        if (fields == null || fields.isEmpty()) {
            return this;
        }
        final Class type;
        if (this.getResult() != null) {
            type = this.getResult().getClass();
        } else {
            return this;
        }
        this.include(type, fields);
        return this;
    }

    public ResponseMessage<T> exclude(final Class type, final String... fields) {

        return this.exclude(type, Arrays.asList(fields));
    }

    public ResponseMessage<T> exclude(final String... fields) {

        return this.exclude(Arrays.asList(fields));
    }

    public ResponseMessage<T> include(final String... fields) {

        return this.include(Arrays.asList(fields));
    }

    protected Set<String> getStringListFromMap(final Map<Class<?>, Set<String>> map, final Class type) {

        return map.computeIfAbsent(type, k -> new HashSet<>());
    }

    public ResponseMessage<T> status(final int status) {

        this.status = status;
        return this;
    }

    public ResponseMessage<T> code(final String code) {

        this.code = code;
        return this;
    }

    public ResponseMessage<T> fields(final LinkedHashSet<String> fields) {

        this.fields = fields;
        return this;
    }

    public boolean is1xxInformational() {

        return HttpStatusEnum.valueOf(this.status).is1xxInformational();
    }

    public boolean is2xxSuccessful() {

        return HttpStatusEnum.valueOf(this.status).is2xxSuccessful();
    }

    public boolean is3xxRedirection() {

        return HttpStatusEnum.valueOf(this.status).is3xxRedirection();
    }

    public boolean is4xxClientError() {

        return HttpStatusEnum.valueOf(this.status).is4xxClientError();
    }

    public boolean is5xxServerError() {

        return HttpStatusEnum.valueOf(this.status).is5xxServerError();
    }
}
