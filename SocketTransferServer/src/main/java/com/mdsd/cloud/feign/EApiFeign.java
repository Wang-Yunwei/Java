package com.mdsd.cloud.feign;

import com.mdsd.cloud.controller.airport.dto.UpdateAirportInp;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.dto.GetTokenOup;
import com.mdsd.cloud.controller.cloudbox.dto.*;
import com.mdsd.cloud.response.ResponseTy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2024-07-11]
 */
@FeignClient(name = "cloud-box",url="http://${env.ip.socket_client}:${env.port.socket_client}/eapi")
public interface EApiFeign {

    @PostMapping(name="换取AccessToken(鉴权)",path = "/auth/getToken")
    ResponseTy<GetTokenOup> getToken(@RequestBody GetTokenInp param);

    @GetMapping(name="获取云盒列表",path = "/box/list")
    ResponseTy<List<GetCloudBoxListOup>> cloudBoxList(@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @PostMapping (name="修改云盒设置", path = "/box/update")
    ResponseTy update(@RequestBody UpdateCloudBoxInp param, @RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @PostMapping(name="获取飞行历史",path = "/box/history")
    ResponseTy<List<HistoryOup>> history(@RequestBody HistoryInp param,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="修改推流地址",path = "/update/live")
    ResponseTy updateLive(@RequestBody UpdateLiveInp param,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="开始直播推流",path = "/box/openlive/{boxSn}")
    ResponseTy openLive(@PathVariable String boxSn,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="结束直播推流",path = "/box/closelive/{boxSn}")
    ResponseTy closeLive(@PathVariable String boxSn,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="获取直播地址",path = "/box/pullUrl/{boxSn}")
    ResponseTy getLiveAddress(@PathVariable String boxSn,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="获取任务照片",path = "/box/photos")
    ResponseTy<List<GetPhotosOup>> getPhotos(@RequestBody GetPhotosInp param,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="机库列表",path = "/hangar/list")
    ResponseTy hangarList(@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="机库控制",path = "/hangar/{hangarId}/{instructId}")
    ResponseTy hangarControl(@PathVariable String hangarId,@PathVariable Integer instructId,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @PostMapping(name="规划机库航线",path = "/hangar/line")
    ResponseTy line(@RequestBody Map<String,String> param,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @PostMapping(name="修改机库信息",path = "/hangar/update")
    ResponseTy hangarUpdate(@RequestBody UpdateAirportInp param,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="获取舱外视频地址",path = "/video/out/{hangarId}/{type}")
    ResponseTy<String> videoOut(@PathVariable String hangarId,@PathVariable String type,@RequestHeader(value = "x-cid") Integer companyId,
                                @RequestHeader(value = "x-token") Integer accessToken);

    @GetMapping(name="获取舱内视频地址",path = "/video/in/{hangarId}/{type}")
    ResponseTy<String> videoIn(@PathVariable String hangarId,@PathVariable String type,@RequestHeader(value = "x-cid") Integer companyId, @RequestHeader(value = "x-token") Integer accessToken);

}