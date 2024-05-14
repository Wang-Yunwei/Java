package com.example.controller.sse;

import com.example.controller.sse.dto.PushDateToSSEPo;
import com.example.controller.sse.service.SseService;
import com.example.response.ResponseDto;
import com.example.response.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author WangYunwei [2023-12-21]
 */
public class SseController {

    SseService sseService;

    public SseController(SseService sseService){
        this.sseService = sseService;
    }

    /**
     * SSE - 建立连接
     */
    @ApiOperation(value = "SSE - 建立连接")
    @GetMapping(value = "/connectSSE/{sseId}")
    public SseEmitter connectSSE(@PathVariable final String sseId) {

        return this.sseService.connectSSE(sseId);
    }

    @ApiOperation(value = "SSE - 发送数据")
    @PostMapping(value = "pushDataToSSE")
    public ResponseDto<Boolean> pushDataToSSE(@RequestBody PushDateToSSEPo param){

        return  ResponseUtil.wrapSuccess(sseService.pushDataToSSE(param));
    }
}
