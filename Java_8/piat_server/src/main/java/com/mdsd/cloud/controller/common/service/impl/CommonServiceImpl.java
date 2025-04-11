package com.mdsd.cloud.controller.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.mdsd.cloud.controller.common.dto.GetAuthTokenPo;
import com.mdsd.cloud.controller.common.dto.GetBusinessTokenPo;
import com.mdsd.cloud.controller.common.dto.GetDoctorOrNurseByHospitalPo;
import com.mdsd.cloud.controller.common.service.CommonService;
import com.mdsd.cloud.feign.AuthServerFeign;
import com.mdsd.cloud.feign.DasServerFeign;
import com.mdsd.cloud.feign.dto.*;
import com.mdsd.cloud.response.exception.BusinessException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-07-13]
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    @NonNull
    DasServerFeign dasServerFeign;

    @NonNull
    AuthServerFeign authServerFeign;

    /**
     * 公共接口 - 院前急救
     */
    @Override
    public List<DasQueryRo> dasQuery(final com.mdsd.cloud.controller.common.dto.DasQueryPo dasQueryPo) {

        final List<DasQueryRo> result = Lists.newArrayList();
        //判断时间范围入参
        if (null == dasQueryPo.getStartTime() && null == dasQueryPo.getEndTime()) {
            final LocalDateTime now = LocalDateTime.now();
            dasQueryPo.setStartTime(LocalDateTime.of(now.getYear(), Month.JANUARY, 1, 0, 0, 0));
            dasQueryPo.setEndTime(LocalDateTime.of(now.getYear() + 1, Month.JANUARY, 1, 0, 0, 0));
        }
        //处理查询入参
        final DasQueryPo queryPo = new DasQueryPo().setBeginTime(dasQueryPo.getStartTime()).setEndTime(dasQueryPo.getEndTime()).setHospitalId(dasQueryPo.getHospitalId()).setName(dasQueryPo.getName());
        log.info("DAS::query - [InParameter]=> {}", JSON.toJSONString(queryPo));
        final Map<String, Object> query = this.dasServerFeign.dasQuery(queryPo);
        //处理通知信息
        Optional.ofNullable(query).filter(map -> null != map.get("data")).ifPresent(map -> {
            result.addAll(JSON.parseArray(JSON.toJSONString(map.get("data")), DasQueryRo.class).stream().peek(dto -> dto.setContent(null != dto.getName() ? String.format("急诊患者 %s 即将到院，请做好接诊准备！", dto.getName()) : "急诊患者 未知 即将到院，请做好接诊准备！")
            ).sorted(Comparator.comparing(DasQueryRo::getPushTime).reversed()).collect(Collectors.toList()));
        });
        return result;
    }

    /**
     * Auth - 获取业务Token
     */
    @Override
    public Map<String, Object> getBusinessToken(final GetBusinessTokenPo param) {

        if (Strings.isNullOrEmpty(param.getAuthJwtToken())) {
            final GetAuthTokenPo getAuthTokenPo = new GetAuthTokenPo();
            BeanUtils.copyProperties(param, getAuthTokenPo);
            //获取权限Token
            final Object authToken = this.getAuthToken(getAuthTokenPo);
            final JSONObject authTokenJson = JSON.parseObject(JSON.toJSONString(authToken));
            param.setAuthJwtToken(String.valueOf(authTokenJson.get("jwt_token")));
        }
        //获取业务Token
        final AuthGetBusinessTokenPo authGetBusinessTokenPo = new AuthGetBusinessTokenPo().setAuthJwtToken(param.getAuthJwtToken());
        log.info("Auth::getClientAccTokenByJwt - [InParameter]=> {}", JSON.toJSONString(authGetBusinessTokenPo));
        final Map<String, Object> businessTokenResult = this.authServerFeign.getBusinessToken(authGetBusinessTokenPo);
        if (!businessTokenResult.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(businessTokenResult.get("message")));
        }
        //获取LoginUserInfo
        final AuthGetLoginUserPo authGetLoginUserPo = new AuthGetLoginUserPo();
        log.info("Auth::getLoginUser - [InParameter]=> {}", JSON.toJSONString(authGetLoginUserPo));
        final Map<String, Object> loginUserResult = this.authServerFeign.getLoginUser(authGetLoginUserPo, param.getAuthJwtToken());
        if (!loginUserResult.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(loginUserResult.get("message")));
        }
        final HashMap<String, Object> result = Maps.newHashMap();
        result.put("businessToken", businessTokenResult.get("result"));
        result.put("loginUserInfo", loginUserResult.get("result"));
        return result;
    }

    /**
     * Auth - 获取AuthToken
     */
    @Override
    public Object getAuthToken(final GetAuthTokenPo param) {

        if (Strings.isNullOrEmpty(param.getUsername()) || Strings.isNullOrEmpty(param.getPassword())) {
            throw new BusinessException("用户名或密码为: NULL");
        }
        //获取AuthJwtToken
        log.info("Auth::access_token - [InParameter]=> {}", JSON.toJSONString(param));
        final Map<String, Object> authTokenResult = this.authServerFeign.getAuthToken(param.getGrantType(), param.getUsername(), param.getPassword(), param.getClientId(), param.getClientSecret());
        if (!authTokenResult.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(authTokenResult.get("message")));
        }
        return authTokenResult.get("result");
    }

    /**
     * Auth - 获取医院列表
     */
    @Override
    public Object getHospitals() {

        final AuthGetHospitalsPo authGetHospitalsPo = new AuthGetHospitalsPo();
        log.info("Auth::getHospitals - [InParameter]=> {}", JSON.toJSONString(authGetHospitalsPo));
        final Map<String, Object> hospitals = this.authServerFeign.getHospitals(authGetHospitalsPo);
        if (!hospitals.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(hospitals.get("message")));
        }
        return hospitals.get("result");
    }

    /**
     * Auth - 获取所有用户
     */
    @Override
    public Object getSomeUser(final String queryValue, final String businessJwtToken) {

        final AuthGetAllUserByDeptGroupPo authGetAllUserByDeptGroupPo = new AuthGetAllUserByDeptGroupPo();
        if (null != queryValue) {
            authGetAllUserByDeptGroupPo.setQueryValue(queryValue);
        }
        log.info("Auth::getAllUserByDeptGroup - [InParameter]=> {}", JSON.toJSONString(authGetAllUserByDeptGroupPo));
        final Map<String, Object> allUserByDeptGroup = this.authServerFeign.getAllUserByDeptGroup(authGetAllUserByDeptGroupPo, businessJwtToken);
        if (!allUserByDeptGroup.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(allUserByDeptGroup.get("message")));
        }
        return allUserByDeptGroup.get("result");
    }

    /**
     * Auth - 获取医院所有部门
     */
    @Override
    public Object getDepartments(final String hospitalId) {

        final AuthGetDepartmentsPo authGetDepartmentsPo = new AuthGetDepartmentsPo().setHospitalId(hospitalId);
        log.info("Auth::getAllUserByDeptGroup - [InParameter]=> {}", JSON.toJSONString(authGetDepartmentsPo));
        final Map<String, Object> departments = this.authServerFeign.getDepartments(authGetDepartmentsPo);
        if (!departments.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(departments.get("message")));
        }
        return departments.get("result");
    }

    /**
     * Auth - 根据医院ID获取医生Or护士
     */
    @Override
    public Object getDoctorOrNurseByHospital(final GetDoctorOrNurseByHospitalPo param) {

        final AuthGetUserByRoleSignPo authGetUserByRoleSignPo = new AuthGetUserByRoleSignPo();
        log.info("Auth::getUserByRoleSign - [InParameter]=> {}", JSON.toJSONString(authGetUserByRoleSignPo));
        BeanUtils.copyProperties(param, authGetUserByRoleSignPo);
        final Map<String, Object> userByRoleSign = this.authServerFeign.getUserByRoleSign(authGetUserByRoleSignPo, param.getAuthJwtToken());
        if (!userByRoleSign.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(userByRoleSign.get("message")));
        }
        return userByRoleSign.get("result");
    }
}
