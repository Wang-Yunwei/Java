package com.mdsd.cloud.controller.cloudbox.service;

import com.mdsd.cloud.controller.cloudbox.dto.*;

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
    void update(UpdateInp param);

    /**
     * 获取飞行历史
     */
    List<HistoryOup> history(HistoryInp param);

    /**
     * 修改推流地址
     */
    void updateLive(UpdateLiveInp param);

    /**
     * 开始直播推流
     */
    void openLive(String boxSn);

    /**
     * 结束直播推流
     */
    void closeLive(String boxSn);

    /**
     * 获取直播地址
     */
    PullUrlOup pullUrl(String boxSn);

    /**
     * 获取任务照片
     */
    List<GetPhotosOup> getPhotos(GetPhotosInp param);
}
