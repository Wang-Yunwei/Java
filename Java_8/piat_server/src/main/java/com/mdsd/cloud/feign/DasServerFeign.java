package com.mdsd.cloud.feign;

import com.mdsd.cloud.feign.dto.DasNotificationPo;
import com.mdsd.cloud.feign.dto.DasQueryPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author WangYunwei [2022-07-12]
 */
@Component
@FeignClient(name = "DasServer", url = "${mdsd.ip.server}:${mdsd.port.das}/das")
public interface DasServerFeign {

    @PostMapping(name = "通知", path = "/zk/notification")
    void dasNotification(@RequestBody DasNotificationPo dasNotificationPo);

    @PostMapping(name = "查询院前急救推送患者", path = "/phep/query")
    Map<String, Object> dasQuery(@RequestBody DasQueryPo dasQueryPo);
}
