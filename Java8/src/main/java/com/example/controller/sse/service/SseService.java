package com.example.controller.sse.service;

import com.example.controller.sse.dto.PushDateToSSEPo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author WangYunwei [2023-12-21]
 */
public interface SseService {

    /**
     * SSE - 建立连接
     */
    SseEmitter connectSSE(String sseId);

    /**
     * SSE - 推送数据
     */
    Boolean pushDataToSSE(PushDateToSSEPo param);
}
