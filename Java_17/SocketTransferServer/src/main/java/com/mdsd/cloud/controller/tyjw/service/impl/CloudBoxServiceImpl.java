package com.mdsd.cloud.controller.tyjw.service.impl;

import com.mdsd.cloud.controller.tyjw.common.AbstractShareMethod;
import com.mdsd.cloud.controller.tyjw.dto.*;
import com.mdsd.cloud.controller.tyjw.service.CloudBoxService;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
@RequiredArgsConstructor
@Service
public class CloudBoxServiceImpl extends AbstractShareMethod implements CloudBoxService {

    private final EApiFeign feign;

    /**
     * 获取云盒列表
     */
    @Override
    public List<GetCloudBoxListOup> getCloudBoxList() {

        return handleAuth(() ->{
            ResponseTy<List<GetCloudBoxListOup>> result = feign.cloudBoxList(auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 修改云盒设置
     */
    @Override
    public String updateCloudBox(UpdateCloudBoxInp param) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.updateCloudBox(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 获取飞行历史
     */
    @Override
    public List<HistoryOup> history(HistoryInp param) {

        return handleAuth(() ->{
            ResponseTy<List<HistoryOup>> result = feign.history(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 修改推流地址
     */
    @Override
    public String updateLive(UpdateLiveInp param) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.updateLive(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 开始直播推流
     */
    @Override
    public String openLive(String boxSn) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.openLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 结束直播推流
     */
    @Override
    public String closeLive(String boxSn) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.closeLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 获取直播地址
     */
    @Override
    public GetLiveAddressOup getLiveAddress(String boxSn) {

        return handleAuth(() ->{
            ResponseTy<GetLiveAddressOup> result = feign.getLiveAddress(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 获取任务照片
     */
    @Override
    public List<GetPhotosOup> getPhotos(GetPhotosInp param) {

        return handleAuth(() ->{
            ResponseTy<List<GetPhotosOup>> result = feign.getPhotos(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 航线转换
     */
    @Override
    public String convert(MultipartFile file) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.convert(file, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 立即执行航线模板任务
     */
    @Override
    public String template(TemplateInp param) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.template(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }
}
