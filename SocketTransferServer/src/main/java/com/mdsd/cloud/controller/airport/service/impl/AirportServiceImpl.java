package com.mdsd.cloud.controller.airport.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.airport.dto.HangarListOup;
import com.mdsd.cloud.controller.airport.dto.PlanLineDTO;
import com.mdsd.cloud.controller.airport.dto.UpdateAirportInp;
import com.mdsd.cloud.controller.airport.service.AirportService;
import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.ResponseTy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;

/**
 * @author WangYunwei [2024-07-12]
 */
@Slf4j
@Service
public class AirportServiceImpl implements AirportService {

    private final EApiFeign feign;

    private final AuthSingleton auth = AuthSingleton.getInstance();

    private final ObjectMapper om = new ObjectMapper();

    public AirportServiceImpl(EApiFeign feign) {

        this.feign = feign;
    }

    /**
     * 机库列表
     */
    @Override
    public HangarListOup hangarList() {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy result = feign.hangarList(auth.getCompanyId(), auth.getAccessToken());
            log.info("机库列表 >>> {}",result.getContent());
        }
        return null;
    }

    /**
     * 机库控制
     */
    @Override
    public void hangarControl(String hangarId, Integer instructId) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy result = feign.hangarControl(hangarId, instructId, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() != 0) {
                throw new RuntimeException("机库控制失败!");
            }
        }

    }

    /**
     * 规划机库航线
     */
    @Override
    public void line(PlanLineDTO param) throws JsonProcessingException {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            HashMap<String, String> map = new HashMap<>();
            String s = om.writeValueAsString(param);
            String bas = Base64.getEncoder().encodeToString(s.getBytes());
            map.put("lineData", bas);
            ResponseTy result = feign.line(map, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() != 0) {
                throw new RuntimeException("规划机库航线失败!");
            }
        }
    }

    /**
     * 修改机库信息
     */
    @Override
    public void update(UpdateAirportInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy result = feign.updateHangar(param, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() != 0) {
                throw new RuntimeException("修改机库信息失败!");
            }
        }
    }

    /**
     * 获取舱外视频地址
     */
    @Override
    public String videoOut(String hangarId, String type) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.videoOut(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() == 0) {
                return result.getContent();
            } else {
                throw new RuntimeException("获取舱外视频地址失败!");
            }
        }
        return null;
    }

    /**
     * 获取舱内视频地址
     */
    @Override
    public String videoIn(String hangarId, String type) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.videoIn(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            if (result.getState() == 0) {
                return result.getContent();
            } else {
                throw new RuntimeException("获取舱内视频地址失败!");
            }
        }
        return null;
    }
}
