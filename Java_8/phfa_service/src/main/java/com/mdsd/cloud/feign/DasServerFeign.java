package com.mdsd.cloud.feign;

import com.mdsd.cloud.feign.dto.DasNotificationPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author WangYunwei [2022-07-25]
 */
@Component
@FeignClient(name = "DasServer", url = "${mdsd.ip.dasServer}:${mdsd.port.das}/das")
public interface DasServerFeign {

    @PostMapping(name = "急救通知", value = "/phep/notification")
    Map<String, Object> dasNotification(@RequestBody DasNotificationPo param);
}
