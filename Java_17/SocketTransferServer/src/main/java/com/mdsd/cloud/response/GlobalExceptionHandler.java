package com.mdsd.cloud.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author WangYunwei [2024-08-09]
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 默认异常处理
     */
    @ExceptionHandler({Exception.class})
    public ResponseDto<?> defaultHandleException(final RuntimeException e) {

        log.error(e.getMessage(), e);
        return ResponseDto.wrapException(e);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler({BusinessException.class})
    public ResponseDto<?> handleBusinessException(final BusinessException e) {

        log.error(e.getMessage(), e);
        return ResponseDto.wrapException(e);
    }
}
