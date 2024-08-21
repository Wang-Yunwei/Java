package com.example.controller.sse.service.impl;

import com.example.controller.sse.dto.PushDatePo;
import com.example.controller.sse.service.SseService;
import com.example.response.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2023-12-21]
 */
@Service
public class SseServiceImpl implements SseService {

    private static final Map<String, SseEmitter> sseMap = new ConcurrentHashMap();

    @Override
    public SseEmitter connectionEstablishment(String sseId) {

        final SseEmitter result = new SseEmitter(1000 * 60 * 60 * 24L);
        try {
            // 发送数据
            result.send(SseEmitter.event().data("SSE连接成功!"));
            sseMap.put(sseId, result);
        } catch (final IOException e) {
            result.completeWithError(e);
        }
        //超时回调触发
        result.onTimeout(() -> {
            throw new BusinessException("SSE 连接超时!");
        });
        //结束之后的回调触发
        result.onCompletion(() -> {
            // 执行完毕断开连接
            result.complete();
            throw new BusinessException("关闭 SSE 连接!");
        });
        return result;
    }

    @Override
    public Boolean pushData(PushDatePo param) {

        try {
            // 根据 sseId 获取 SseEmitter
            final SseEmitter sseEmitter = sseMap.get(param.getSseId());
            Optional.of(sseEmitter).orElseThrow(() -> new BusinessException(String.format("未找到 SSE_ID = %s 的连接", param.getSseId())));
            // 发送数据
            sseEmitter.send(param.getContent());
        } catch (final IOException e) {
            throw new BusinessException(String.format("推送数据失败: %s", e.getClass().getName()));
        }
        return Boolean.TRUE;
    }
}
