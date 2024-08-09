package com.mdsd.cloud.controller.cloudbox.service;

import com.mdsd.cloud.controller.airport.dto.TemplateInp;
import com.mdsd.cloud.controller.cloudbox.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangYunwei [2024-07-11]
 */
public interface CloudBoxService {

    /**
     * 获取云盒列表
     */
    List<GetCloudBoxListOup> getCloudBoxList();

    /**
     * 修改云盒设置
     */
    String update(UpdateCloudBoxInp param);

    /**
     * 获取飞行历史
     */
    List<HistoryOup> history(HistoryInp param);

    /**
     * 修改推流地址
     */
    String updateLive(UpdateLiveInp param);

    /**
     * 开始直播推流
     */
    String openLive(String boxSn);

    /**
     * 结束直播推流
     */
    String closeLive(String boxSn);

    /**
     * 获取直播地址
     */
    GetLiveAddressOup getLiveAddress(String boxSn);

    /**
     * 获取任务照片
     */
    List<GetPhotosOup> getPhotos(GetPhotosInp param);

    /**
     * 航线转换
     */
    String convert(MultipartFile file);

    /**
     * 立即执行航线模板任务
     */
    String template(TemplateInp param);
}
