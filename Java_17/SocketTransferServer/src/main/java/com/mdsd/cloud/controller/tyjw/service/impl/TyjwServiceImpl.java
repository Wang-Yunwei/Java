package com.mdsd.cloud.controller.tyjw.service.impl;

import com.mdsd.cloud.controller.tyjw.common.AbstractShareMethod;
import com.mdsd.cloud.controller.tyjw.dto.*;
import com.mdsd.cloud.controller.tyjw.service.ITyjwService;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.utils.MD5HashGenerator;
import com.mdsd.cloud.utils.ParameterMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2024-09-03]
 */
@Slf4j
@Service
public class TyjwServiceImpl extends AbstractShareMethod implements ITyjwService {

    private final EApiFeign feign;

    public TyjwServiceImpl(EApiFeign feign) {
        this.feign = feign;
    }

    /**
     * 获取 AccessToken
     */
    @Override
    public void getToken() {
        getToken(new GetTokenInp());
    }

    /**
     * 获取 AccessToken
     */
    @Override
    public GetTokenOup getToken(GetTokenInp param) {

        log.info("获取 AccessToken");
        String str = param.getAccessKeyId() + param.getAccessKeySecret() + param.getTimeStamp();
        param.setEncryptStr(MD5HashGenerator.generateMD5(str));
        ResponseTy<GetTokenOup> result = feign.getToken(param);
        if (0 == result.getState()) {
            AuthSingleton.getInstance().setCompanyId(result.getContent().getCompanyId());
            AuthSingleton.getInstance().setAccessToken(result.getContent().getAccessToken());
            return result.getContent();
        } else {
            throw new BusinessException(String.valueOf(result.getState()), result.getMessage());
        }
    }

    /**
     * 云盒 - 获取云盒列表
     */
    @Override
    public List<GetCloudBoxListOup> getCloudBoxList() {

        return handleAuth(() ->{
            ResponseTy<List<GetCloudBoxListOup>> result = feign.cloudBoxList(auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 修改云盒设置
     */
    @Override
    public String updateCloudBox(UpdateCloudBoxInp param) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.updateCloudBox(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 获取飞行历史
     */
    @Override
    public List<HistoryOup> history(HistoryInp param) {

        return handleAuth(() ->{
            ResponseTy<List<HistoryOup>> result = feign.history(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 修改推流地址
     */
    @Override
    public String updateLive(UpdateLiveInp param) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.updateLive(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 开始直播推流
     */
    @Override
    public String openLive(String boxSn) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.openLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 结束直播推流
     */
    @Override
    public String closeLive(String boxSn) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.closeLive(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 获取直播地址
     */
    @Override
    public GetLiveAddressOup getLiveAddress(String boxSn) {

        return handleAuth(() ->{
            ResponseTy<GetLiveAddressOup> result = feign.getLiveAddress(boxSn, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 获取任务照片
     */
    @Override
    public List<GetPhotosOup> getPhotos(GetPhotosInp param) {

        return handleAuth(() ->{
            ResponseTy<List<GetPhotosOup>> result = feign.getPhotos(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 航线转换
     */
    @Override
    public String convert(MultipartFile file) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.convert(file, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 云盒 - 立即执行航线模板任务
     */
    @Override
    public String template(TemplateInp param) {

        return handleAuth(() ->{
            ResponseTy<String> result = feign.template(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 机库列表
     */
    @Override
    public List<RechargeDTO> hangarList() {
        return handleAuth(() -> {
            ResponseTy<List<RechargeDTO>> result = feign.hangarList(auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 机库控制
     */
    @Override
    public String hangarControl(String hangarId, Integer instructId) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.hangarControl(hangarId, instructId, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 规划机库航线
     */
    @Override
    public String line(PlanLineDataDTO param) {

        return handleAuth(() -> {
            Map<String, String> lineData = Map.of("lineData", Base64.getEncoder().encodeToString(ParameterMapping.getPlanLineData(param).toByteArray()));
            ResponseTy<String> result = feign.line(lineData, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 修改机库信息
     */
    @Override
    public String updateAirport(UpdateAirportInp param) {

        return handleAuth(() -> {
            ResponseTy<String> result = feign.updateHangar(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 获取舱外视频地址
     */
    @Override
    public String videoOut(String hangarId, String type) {

        return handleAuth(() -> {
            ResponseTy<String> result = feign.videoOut(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库 - 获取舱内视频地址
     */
    @Override
    public String videoIn(String hangarId, String type) {

        return handleAuth(() -> {
            ResponseTy<String> result = feign.videoIn(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }
}
