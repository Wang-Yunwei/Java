package com.mdsd.cloud.controller.cloudbox.service.impl;

import com.mdsd.cloud.controller.cloudbox.dto.*;
import com.mdsd.cloud.controller.cloudbox.service.CloudBoxService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
@Service
public class CloudBoxServiceImpl implements CloudBoxService {

    /**
     * 获取云盒列表
     */
    @Override
    public List<GetCloudBoxListOup> getCloudBoxList() {

        return null;
    }

    /**
     * 修改云盒设置
     */
    @Override
    public void update(UpdateInp param) {

    }

    /**
     * 获取飞行历史
     */
    @Override
    public List<HistoryOup> history(HistoryInp param) {

        return null;
    }

    /**
     * 修改推流地址
     */
    @Override
    public void updateLive(UpdateLiveInp param) {

    }

    /**
     * 开始直播推流
     */
    @Override
    public void openLive(String boxSn) {

    }

    /**
     * 结束直播推流
     */
    @Override
    public void closeLive(String boxSn) {

    }

    /**
     * 获取直播地址
     */
    @Override
    public PullUrlOup pullUrl(String boxSn) {

        return null;
    }

    /**
     * 获取任务照片
     */
    @Override
    public List<GetPhotosOup> getPhotos(GetPhotosInp param) {

        return null;
    }
}
