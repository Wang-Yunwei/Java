package com.mdsd.cloud.controller.cloudbox.service.impl;

import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.cloudbox.dto.*;
import com.mdsd.cloud.controller.cloudbox.service.CloudBoxService;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
@Service
public class CloudBoxServiceImpl implements CloudBoxService {

    private final EApiFeign feign;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    public CloudBoxServiceImpl(EApiFeign feign) {

        this.feign = feign;
    }

    /**
     * 获取云盒列表
     */
    @Override
    public List<GetCloudBoxListOup> getCloudBoxList() {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<List<GetCloudBoxListOup>> result = feign.cloudBoxList(auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() == 0) {
                return result.getContent();
            } else {
                throw new RuntimeException("获取云盒列表失败!");
            }
        }
        return null;
    }

    /**
     * 修改云盒设置
     */
    @Override
    public void update(UpdateCloudBoxInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.updateCloudBox(param, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() != 0) {
                throw new RuntimeException("修改云盒设置失败!");
            }
        }
    }

    /**
     * 获取飞行历史
     */
    @Override
    public List<HistoryOup> history(HistoryInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<List<HistoryOup>> result = feign.history(param, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() == 0) {
                return result.getContent();
            } else {
                throw new RuntimeException("获取飞行历史失败!");
            }
        }
        return null;
    }

    /**
     * 修改推流地址
     */
    @Override
    public void updateLive(UpdateLiveInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.updateLive(param, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() != 0) {
                throw new RuntimeException("修改推流地址失败!");
            }
        }
    }

    /**
     * 开始直播推流
     */
    @Override
    public void openLive(String boxSn) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.openLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() != 0) {
                throw new RuntimeException("开始直播推流失败!");
            }
        }
    }

    /**
     * 结束直播推流
     */
    @Override
    public void closeLive(String boxSn) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.closeLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() != 0) {
                throw new RuntimeException("结束直播推流失败!");
            }
        }
    }

    /**
     * 获取直播地址
     */
    @Override
    public GetLiveAddressOup getLiveAddress(String boxSn) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<GetLiveAddressOup> result = feign.getLiveAddress(boxSn, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() == 0) {
                return result.getContent();
            } else {
                throw new RuntimeException("获取直播地址失败!");
            }
        }
        return null;
    }

    /**
     * 获取任务照片
     */
    @Override
    public List<GetPhotosOup> getPhotos(GetPhotosInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<List<GetPhotosOup>> result = feign.getPhotos(param, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() == 0) {
                return result.getContent();
            } else {
                throw new RuntimeException("获取任务照片失败!");
            }
        }
        return null;
    }
}
