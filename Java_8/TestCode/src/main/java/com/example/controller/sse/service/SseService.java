package com.example.controller.sse.service;

import com.example.controller.sse.dto.PushDatePo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author WangYunwei [2023-12-21]
 */
public interface SseService {

    /**
     * SSE - 建立连接
     */
    SseEmitter connectionEstablishment(String sseId);

    /**
     * SSE - 推送数据
     */
    Boolean pushData(PushDatePo param);
}
