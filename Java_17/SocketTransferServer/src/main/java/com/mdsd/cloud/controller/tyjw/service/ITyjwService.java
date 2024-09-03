package com.mdsd.cloud.controller.tyjw.service;

import com.mdsd.cloud.controller.tyjw.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangYunwei [2024-09-03]
 */
public interface ITyjwService {

    /**
     * 获取 AccessToken
     */
    void getToken();

    /**
     * 获取 AccessToken
     */
    GetTokenOup getToken(GetTokenInp param);

    /**
     * 云盒 - 获取云盒列表
     */
    List<GetCloudBoxListOup> getCloudBoxList();

    /**
     * 云盒 - 修改云盒设置
     */
    String updateCloudBox(UpdateCloudBoxInp param);

    /**
     * 云盒 - 获取飞行历史
     */
    List<HistoryOup> history(HistoryInp param);

    /**
     * 云盒 - 修改推流地址
     */
    String updateLive(UpdateLiveInp param);

    /**
     * 云盒 - 开始直播推流
     */
    String openLive(String boxSn);

    /**
     * 云盒 - 结束直播推流
     */
    String closeLive(String boxSn);

    /**
     * 云盒 - 获取直播地址
     */
    GetLiveAddressOup getLiveAddress(String boxSn);

    /**
     * 云盒 - 获取任务照片
     */
    List<GetPhotosOup> getPhotos(GetPhotosInp param);

    /**
     * 云盒 - 航线转换
     */
    String convert(MultipartFile file);

    /**
     * 云盒 - 立即执行航线模板任务
     */
    String template(TemplateInp param);

    /**
     * 机库 - 机库列表
     */
    List<RechargeDTO> hangarList();

    /**
     * 机库 - 机库控制
     */
    String hangarControl(String hangarId, Integer instructId);

    /**
     * 机库 - 规划机库航线
     */
    String line(PlanLineDataDTO param);

    /**
     * 机库 - 修改机库信息
     */
    String updateAirport(UpdateAirportInp param);

    /**
     * 获取舱外视频地址
     */
    String videoOut(String hangarId, String type);

    /**
     * 获取舱内视频地址
     */
    String videoIn(String hangarId, String type);
}
