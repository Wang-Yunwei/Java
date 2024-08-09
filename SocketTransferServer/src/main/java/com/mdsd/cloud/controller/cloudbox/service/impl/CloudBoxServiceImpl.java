package com.mdsd.cloud.controller.cloudbox.service.impl;

import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.service.AuthService;
import com.mdsd.cloud.controller.cloudbox.dto.*;
import com.mdsd.cloud.controller.cloudbox.service.CloudBoxService;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
@RequiredArgsConstructor
@Service
public class CloudBoxServiceImpl implements CloudBoxService {

    private final EApiFeign feign;

    private final AuthService authService;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    /**
     * 获取云盒列表
     */
    @Override
    public List<GetCloudBoxListOup> getCloudBoxList() {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<List<GetCloudBoxListOup>> result = feign.cloudBoxList(auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return getCloudBoxList();
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return getCloudBoxList();
        }
    }

    /**
     * 修改云盒设置
     */
    @Override
    public String update(UpdateCloudBoxInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.updateCloudBox(param, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return update(param);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return update(param);
        }
    }

    /**
     * 获取飞行历史
     */
    @Override
    public List<HistoryOup> history(HistoryInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<List<HistoryOup>> result = feign.history(param, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return history(param);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return history(param);
        }
    }

    /**
     * 修改推流地址
     */
    @Override
    public String updateLive(UpdateLiveInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.updateLive(param, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return updateLive(param);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return updateLive(param);
        }
    }

    /**
     * 开始直播推流
     */
    @Override
    public String openLive(String boxSn) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.openLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return openLive(boxSn);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return openLive(boxSn);
        }
    }

    /**
     * 结束直播推流
     */
    @Override
    public String closeLive(String boxSn) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.closeLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return closeLive(boxSn);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return closeLive(boxSn);
        }
    }

    /**
     * 获取直播地址
     */
    @Override
    public GetLiveAddressOup getLiveAddress(String boxSn) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<GetLiveAddressOup> result = feign.getLiveAddress(boxSn, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    if(StringUtils.isNoneBlank(result.getContent().getFlv())){
                        result.getContent().setFlv(result.getContent().getFlv().replace("222.92.145.250","192.168.0.222"));
                    }
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return getLiveAddress(boxSn);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return getLiveAddress(boxSn);
        }
    }

    /**
     * 获取任务照片
     */
    @Override
    public List<GetPhotosOup> getPhotos(GetPhotosInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<List<GetPhotosOup>> result = feign.getPhotos(param, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return getPhotos(param);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return getPhotos(param);
        }
    }
}
