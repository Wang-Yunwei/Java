package com.mdsd.cloud.controller.airport.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.airport.dto.HangarListOup;
import com.mdsd.cloud.controller.airport.dto.PlanLineDTO;
import com.mdsd.cloud.controller.airport.dto.UpdateAirportInp;
import com.mdsd.cloud.controller.airport.service.AirportService;
import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.service.AuthService;
import com.mdsd.cloud.controller.cloudbox.dto.GetCloudBoxListOup;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AirportServiceImpl implements AirportService {

    private final EApiFeign feign;

    private final AuthService authService;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    private final ObjectMapper om = new ObjectMapper();

    /**
     * 机库列表
     */
    @Override
    public HangarListOup hangarList() {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.hangarList(auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return null;
                case 201:
                    authService.getToken(new GetTokenInp());
                    return hangarList();
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return hangarList();
        }
    }

    /**
     * 机库控制
     */
    @Override
    public String hangarControl(String hangarId, Integer instructId) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.hangarControl(hangarId, instructId, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return hangarControl(hangarId, instructId);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return hangarControl(hangarId, instructId);
        }
    }

    /**
     * 规划机库航线
     */
    @Override
    public String line(PlanLineDTO param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            HashMap<String, String> map = new HashMap<>();
            String jsonStr = null;
            try {
                jsonStr = om.writeValueAsString(param);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            String bas = Base64.getEncoder().encodeToString(jsonStr.getBytes());
            map.put("lineData", bas);
            ResponseTy<String> result = feign.line(map, auth.getCompanyId(), auth.getAccessToken());

            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return line(param);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return line(param);
        }
    }

    /**
     * 修改机库信息
     */
    @Override
    public String update(UpdateAirportInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.updateHangar(param, auth.getCompanyId(), auth.getAccessToken());
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
     * 获取舱外视频地址
     */
    @Override
    public String videoOut(String hangarId, String type) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.videoOut(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return videoOut(hangarId,type);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return videoOut(hangarId,type);
        }
    }

    /**
     * 获取舱内视频地址
     */
    @Override
    public String videoIn(String hangarId, String type) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.videoIn(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            switch (result.getState()) {
                case 0:
                    return result.getContent();
                case 201:
                    authService.getToken(new GetTokenInp());
                    return videoIn(hangarId,type);
                default:
                    throw new RuntimeException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return videoIn(hangarId,type);
        }
    }
}
