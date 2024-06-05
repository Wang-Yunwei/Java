package com.example.controller.sse;

import com.example.controller.sse.dto.PushDatePo;
import com.example.controller.sse.service.SseService;
import com.example.response.ResponseDto;
import com.example.response.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author WangYunwei [2023-12-21]
 */
@RestController
@RequestMapping("sse")
public class SseController {

    SseService sseService;

    public SseController(SseService sseService){
        this.sseService = sseService;
    }

    /**
     * SSE - 建立连接
     */
    @ApiOperation(value = "SSE - 建立连接")
    @GetMapping(name= "建立连接",path = "/connectionEstablishment/{sseId}")
    public SseEmitter connectionEstablishment(@PathVariable final String sseId) {

        return this.sseService.connectSSE(sseId);
    }

    @ApiOperation(value = "SSE - 推送数据")
    @PostMapping(name="推送数据",path = "pushData")
    public ResponseDto<Boolean> pushData(@RequestBody PushDatePo param){

        return  ResponseUtil.wrapSuccess(sseService.pushData(param));
    }
}
