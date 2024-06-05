package com.example.controller.sse.service.impl;

import com.example.controller.sse.dto.PushDatePo;
import com.example.controller.sse.service.SseService;
import com.example.response.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangYunwei [2023-12-21]
 */
@Service
public class SseServiceImpl implements SseService {

    private static final Map<String, SseEmitter> sseMap = new ConcurrentHashMap();

    /**
     * send(): 发送数据
     * complete(): 表示执行完毕，会断开连接
     * onTimeout(): 超时回调触发
     * onCompletion(): 结束之后的回调触发
     */
    @Override
    public SseEmitter connectSSE(String sseId) {

        final SseEmitter result = new SseEmitter(1000 * 60 * 60 * 24L);
        try {
            result.send(SseEmitter.event().data("SSE连接成功!"));//send():发送数据
            sseMap.put(sseId, result);//将SseEmitter放入Map中
        } catch (final IOException e) {
            result.completeWithError(e);
        }
        //超时回调触发
        result.onTimeout(() -> {
            throw new BusinessException("SSE 连接超时!");
        });
        //结束之后的回调触发
        result.onCompletion(() -> {
            throw new BusinessException("关闭 SSE 连接!");
        });
        return result;
    }

    @Override
    public Boolean pushData(PushDatePo param) {

        try {
            //根据sseId获取SseEmitter
            final SseEmitter sseEmitter = sseMap.get(param.getSseId());
            //发送数据
            sseEmitter.send(param.getContent());
        } catch (final NullPointerException e) {
            throw new BusinessException(String.format("未找到SSE连接: %s", e.getClass().getName()));
        } catch (final IOException e) {
            throw new BusinessException(String.format("发送数据失败: %s", e.getClass().getName()));
        }
        return Boolean.TRUE;
    }
}
