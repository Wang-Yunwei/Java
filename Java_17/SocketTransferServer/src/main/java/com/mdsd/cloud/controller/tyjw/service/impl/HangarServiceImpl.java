package com.mdsd.cloud.controller.tyjw.service.impl;

import com.mdsd.cloud.controller.tyjw.common.AbstractShareMethod;
import com.mdsd.cloud.controller.tyjw.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.tyjw.dto.RechargeDTO;
import com.mdsd.cloud.controller.tyjw.dto.UpdateAirportInp;
import com.mdsd.cloud.controller.tyjw.service.HangarService;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.utils.ParameterMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2024-07-12]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class HangarServiceImpl extends AbstractShareMethod implements HangarService {

    private final EApiFeign feign;

    /**
     * 机库列表
     */
    @Override
    public List<RechargeDTO> hangarList() {
        return handleAuth(() -> {
            ResponseTy<List<RechargeDTO>> result = feign.hangarList(auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 机库控制
     */
    @Override
    public String hangarControl(String hangarId, Integer instructId) {
        return handleAuth(() -> {
            ResponseTy<String> result = feign.hangarControl(hangarId, instructId, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 规划机库航线
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
     * 修改机库信息
     */
    @Override
    public String updateAirport(UpdateAirportInp param) {

        return handleAuth(() -> {
            ResponseTy<String> result = feign.updateHangar(param, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 获取舱外视频地址
     */
    @Override
    public String videoOut(String hangarId, String type) {

        return handleAuth(() -> {
            ResponseTy<String> result = feign.videoOut(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }

    /**
     * 获取舱内视频地址
     */
    @Override
    public String videoIn(String hangarId, String type) {

        return handleAuth(() -> {
            ResponseTy<String> result = feign.videoIn(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            return processResult(result);
        });
    }
}
