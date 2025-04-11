package com.mdsd.cloud.config;

import com.beust.jcommander.ParameterException;
import com.mdsd.cloud.response.ResponseDto;
import com.mdsd.cloud.response.ResponseUtil;
import com.mdsd.cloud.response.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 默认异常处理
     */
    @ExceptionHandler({Exception.class})
    public ResponseDto<?> defaultHandleException(final Exception e) {

        log.error(e.getMessage(), e);
        return ResponseUtil.wrapException(e);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler({BaseException.class})
    public ResponseDto<?> handleBusinessException(final BaseException e) {

        log.error(e.getMessage(), e);
        return ResponseUtil.wrapException(e);
    }

    /**
     * 处理数据校验异常
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseDto<?> handleBusinessException(final Exception e) {

        log.error(e.getMessage(), e);

        List<FieldError> errors = null;
        if (e instanceof BindException) {
            errors = ((BindException) e).getFieldErrors();
        } else {
            errors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        return ResponseUtil.wrapException(new ParameterException(this.getFieldErrorString(errors)));
    }

    /**
     * 获取校验异常描述
     */
    private String getFieldErrorString(final List<FieldError> errors) {

        final StringBuilder sb = new StringBuilder();
        for (final FieldError error : errors) {
            sb.append(error.getField()).append(":").append(error.getDefaultMessage());
        }
        return sb.toString();
    }
}
