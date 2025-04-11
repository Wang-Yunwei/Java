package com.mdsd.cloud.feign;

import com.mdsd.cloud.config.FeignHttpsConfig;
import com.mdsd.cloud.feign.dto.*;
import com.mdsd.cloud.response.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author WangYunwei [2022-06-14]
 */
@Component
@FeignClient(name = "ImServer", configuration = FeignHttpsConfig.class, url = "${mdsd.ip.im}:${mdsd.port.im}/api")
public interface ImServerFeign {

    @PostMapping(name = "创建群聊", value = "/Channel/create")
    ResponseMessage<ImCreateRo> imCreate(@RequestBody ImCreatePo param);

    @PostMapping(name = "给群发送消息", value = "/Channel/sendMessage")
    ResponseMessage<String> imSendMessage(@RequestBody ImSendMessagePo param);

    @PostMapping(name = "添加成员", value = "/Channel/addMembers")
    ResponseMessage<String> imAddMembers(@RequestBody ImAddMembersPo param);

    @PostMapping(name = "给某个用户或者一些用户发送实时通知", value = "/User/sendNotice")
    ResponseMessage<String> imSendNotice(@RequestBody SendNoticePo param);
}
