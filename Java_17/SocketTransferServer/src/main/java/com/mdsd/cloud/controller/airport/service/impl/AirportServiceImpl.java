package com.mdsd.cloud.controller.airport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.airport.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.airport.dto.RechargeDTO;
import com.mdsd.cloud.controller.airport.dto.UpdateAirportInp;
import com.mdsd.cloud.controller.airport.service.AirportService;
import com.mdsd.cloud.controller.auth.dto.AuthSingleton;
import com.mdsd.cloud.controller.auth.dto.GetTokenInp;
import com.mdsd.cloud.controller.auth.service.AuthService;
import com.mdsd.cloud.controller.transfer.dto.TyjwProtoBuf;
import com.mdsd.cloud.enums.StateEnum;
import com.mdsd.cloud.feign.EApiFeign;
import com.mdsd.cloud.response.BusinessException;
import com.mdsd.cloud.response.ResponseTy;
import com.mdsd.cloud.utils.ParameterMapping;
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
    public List<RechargeDTO> hangarList() {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<List<RechargeDTO>> result = feign.hangarList(auth.getCompanyId(), auth.getAccessToken());
            StateEnum stateEnum = StateEnum.getStateEnum(result.getState());
            if (null != stateEnum) {
                return switch (stateEnum) {
                    case STATE_0 -> result.getContent();
                    case STATE_102 -> throw new BusinessException(stateEnum.getDescription());
                    case STATE_201 -> {
                        authService.getToken(new GetTokenInp());
                        yield hangarList();
                    }
                };
            } else {
                throw new BusinessException(result.toString());
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
            StateEnum stateEnum = StateEnum.getStateEnum(result.getState());
            if (null != stateEnum) {
                return switch (stateEnum) {
                    case STATE_0 -> result.getContent();
                    case STATE_201 -> {
                        authService.getToken(new GetTokenInp());
                        yield hangarControl(hangarId, instructId);
                    }
                    default -> throw new BusinessException(stateEnum.getDescription());
                };
            } else {
                throw new BusinessException(result.toString());
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
    public String line(PlanLineDataDTO param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            HashMap<String, String> map = new HashMap<>();
            // 将数据映射到ProtoBuf
            map.put("lineData", Base64.getEncoder().encodeToString(ParameterMapping.getPlanLineData(param).toByteArray()));
            ResponseTy<String> result = feign.line(map, auth.getCompanyId(), auth.getAccessToken());
            StateEnum stateEnum = StateEnum.getStateEnum(result.getState());
            if (null != stateEnum) {
                return switch (stateEnum) {
                    case STATE_0 -> result.getContent();
                    case STATE_201 -> {
                        authService.getToken(new GetTokenInp());
                        yield line(param);
                    }
                    default -> throw new BusinessException(stateEnum.getDescription());
                };
            } else {
                throw new BusinessException(result.toString());
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
    public String updateAirport(UpdateAirportInp param) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.updateHangar(param, auth.getCompanyId(), auth.getAccessToken());
            StateEnum stateEnum = StateEnum.getStateEnum(result.getState());
            if (null != stateEnum) {
                return switch (stateEnum) {
                    case STATE_0 -> result.getContent();
                    case STATE_201 -> {
                        authService.getToken(new GetTokenInp());
                        yield updateAirport(param);
                    }
                    default -> throw new BusinessException(stateEnum.getDescription());
                };
            } else {
                throw new BusinessException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return updateAirport(param);
        }
    }

    /**
     * 获取舱外视频地址
     */
    @Override
    public String videoOut(String hangarId, String type) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.videoOut(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            StateEnum stateEnum = StateEnum.getStateEnum(result.getState());
            if (null != stateEnum) {
                return switch (stateEnum) {
                    case STATE_0 -> result.getContent();
                    case STATE_201 -> {
                        authService.getToken(new GetTokenInp());
                        yield videoOut(hangarId, type);
                    }
                    default -> throw new BusinessException(stateEnum.getDescription());
                };
            } else {
                throw new BusinessException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return videoOut(hangarId, type);
        }
    }

    /**
     * 获取舱内视频地址
     */
    @Override
    public String videoIn(String hangarId, String type) {

        if (null != auth.getCompanyId() && StringUtils.isNoneBlank(auth.getAccessToken())) {
            ResponseTy<String> result = feign.videoIn(hangarId, type, auth.getCompanyId(), auth.getAccessToken());
            StateEnum stateEnum = StateEnum.getStateEnum(result.getState());
            if (null != stateEnum) {
                return switch (stateEnum) {
                    case STATE_0 -> result.getContent();
                    case STATE_201 -> {
                        authService.getToken(new GetTokenInp());
                        yield videoIn(hangarId, type);
                    }
                    default -> throw new BusinessException(stateEnum.getDescription());
                };
            } else {
                throw new BusinessException(result.toString());
            }
        } else {
            authService.getToken(new GetTokenInp());
            return videoIn(hangarId, type);
        }
    }
}
