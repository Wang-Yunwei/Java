package com.example.controller.netty;

import com.example.controller.netty.service.PushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYunwei [2021-12-20]
 */
@RestController
@Api(value = "WEB - PushController", tags = "后端推送消息", produces = MediaType.APPLICATION_JSON_VALUE)
public class PushController {

    PushService pushService;

    public PushController(final PushService pushService) {

        this.pushService = pushService;
    }

    @ApiOperation(value = "Netty - 推送给所有用户")
    @PostMapping(name = "推送消息给所有用户", path = "/pushAll")
    public void pushToAll(@RequestParam("msg") final String msg) {

        this.pushService.pushMsgToAll(msg);
    }

    @ApiOperation(value = "Netty - 推送消息给指定用户")
    @PostMapping(name = "推送消息给指定用户", path = "/pushOne")
    public void pushMsgToOne(@RequestParam("userId") final String userId, @RequestParam("msg") final String msg) {

        this.pushService.pushMsgToOne(userId, msg);
    }
}
