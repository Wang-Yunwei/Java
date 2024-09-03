package com.mdsd.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author WangYunwei [2024-09-03]
 */
@FeignClient(name = "JIKUPI", url = "http://${env.ip.jikupi}:${env.port.jikupi}")
public interface HangarFeign {

    @GetMapping(name = "获取配置信息",path = "/json_get_config")
    Map<String, String> getConfig();

    @GetMapping(name = "重启 websocket 服务",path = "/json_reboot_websocket")
    Map<String, String> rebootWebsocket();

    @GetMapping(name = "获取机库挂载配置",path = "/json_get_config_info")
    Map<String, String> getMountInfo();

    @GetMapping(name = "获取机库状态",path = "/json_get_state")
    Map<String, String> getState();

    @GetMapping(name = "获取机场指令",path = "/getCommand")
    Map<String, String> getCommand();

    @GetMapping(name = "舱门 - 打开", path = "/opendoor")
    void doorOpen(@RequestParam String opendoorcomm);

    @GetMapping(name = "舱门 - 关闭",path = "/closedoor")
    void doorClose(@RequestParam String closedoorcomm);

    @GetMapping(name = "推杆 - 打开",path = "/openbar")
    Map<String, String> barOpen(@RequestParam String openbarcomm);

    @GetMapping(name = "推杆 - 关闭",path = "/closebar")
    Map<String, String> barClose(@RequestParam String closebarcomm);

    @GetMapping(name = "空调 - 打开",path = "/openair")
    Map<String, String> airOpen(@RequestParam String openaircomm);

    @GetMapping(name = "空调 - 关闭",path = "/closeair")
    Map<String, String> airClose(@RequestParam String closeaircomm);

    @GetMapping(name = "无人机 - 打开or关闭",path = "/uavcontroller")
    Map<String, String> uavOpen(@RequestParam String controller);

}
