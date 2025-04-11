package com.mdsd.cloud.controller.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.common.dto.GetAuthTokenPo;
import com.mdsd.cloud.controller.common.dto.GetDoctorOrNurseByHospitalPo;
import com.mdsd.cloud.controller.common.dto.PushDateToSSEPo;
import com.mdsd.cloud.controller.common.dto.RedisSaveToListPo;
import com.mdsd.cloud.controller.common.mapper.CommonMapper;
import com.mdsd.cloud.controller.common.service.CommonService;
import com.mdsd.cloud.entity.MedPhepMedicalRecordsEntity;
import com.mdsd.cloud.entity.MedPhepNoticesEntity;
import com.mdsd.cloud.entity.MedPhepPatientsEntity;
import com.mdsd.cloud.enumeration.CommonEnum;
import com.mdsd.cloud.feign.AuthServerFeign;
import com.mdsd.cloud.feign.DasServerFeign;
import com.mdsd.cloud.feign.ImServerFeign;
import com.mdsd.cloud.feign.dto.*;
import com.mdsd.cloud.mapper.MedPhepMedicalRecordsMapper;
import com.mdsd.cloud.mapper.MedPhepMissionsMapper;
import com.mdsd.cloud.mapper.MedPhepPatientsMapper;
import com.mdsd.cloud.response.ResponseMessage;
import com.mdsd.cloud.response.exception.BusinessException;
import com.mdsd.cloud.utils.PrimaryKeyUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2022-07-21]
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    @NonNull
    CommonMapper commonMapper;

    @NonNull
    MedPhepMissionsMapper medPhepMissionsMapper;//任务表

    @NonNull
    MedPhepMedicalRecordsMapper medPhepMedicalRecordsMapper;//病历表

    @NonNull
    MedPhepPatientsMapper medPhepPatientsMapper;//患者

    @NonNull
    ImServerFeign imServerFeign;

    @NonNull
    DasServerFeign dasServerFeign;

    @NonNull
    AuthServerFeign authServerFeign;

    @NonNull
    RedisTemplate<String, Object> redisTemplate;

    private static final Map<String, SseEmitter> sseMap = new ConcurrentHashMap<>();

    /**
     * Common - 获取UUID
     */
    @Override
    public Object getUUID(final HttpServletRequest request) {

        if (request.getParameterNames().hasMoreElements()) {
            final String number = request.getParameter("number");
            final int integer = Integer.parseInt(number);
            if (integer > 0) {
                return PrimaryKeyUtil.getUUID(integer);
            }
        }
        return PrimaryKeyUtil.getUUID(null);
    }

    /**
     * SSE - 建立连接
     *
     * @deprecated send(): 发送数据
     * complete(): 表示执行完毕，会断开连接
     * onTimeout(): 超时回调触发
     * onCompletion(): 结束之后的回调触发
     */
    @Override
    public SseEmitter connectSSE(final String sseId) {

        final SseEmitter result = new SseEmitter(1000 * 60 * 60 * 24L);
        try {
            result.send(SseEmitter.event().data("SSE连接成功!"));//send():发送数据
            sseMap.put(sseId, result);//将SseEmitter放入Map中
        } catch (final IOException e) {
            result.completeWithError(e);
        }
        //超时回调触发
        result.onTimeout(() -> {
            throw new BusinessException("SSE连接超时!");
        });
        //结束之后的回调触发
        result.onCompletion(() -> {
            throw new BusinessException("关闭SSE连接!");
        });
        return result;
    }

    /**
     * SSE - 推送数据
     */
    @Override
    public Boolean pushDataToSSE(final PushDateToSSEPo param) {

        try {
            //根据sseId获取SseEmitter
            final SseEmitter sseEmitter = sseMap.get(param.getSseId());
            //发送数据
            sseEmitter.send(param.getContent());
        } catch (final NullPointerException e) {
            throw new BusinessException(String.format("未找到SSE连接: %s", e.getClass().getName()));
        } catch (final IOException e) {
            throw new BusinessException(String.format("发送数据失败: %s", e.getClass().getName()));
        }
        return Boolean.TRUE;
    }

    /**
     * Redis - SaveToList
     */
    @Override
    public Boolean redisSaveToList(final RedisSaveToListPo param) {

        Boolean result = Boolean.FALSE;
        //拼接Redis的Key
        final String redisKey = String.format(CommonConstants.PREFIX, param.getModuleName(), param.getKey());
        //向集合最右边添加元素
        final Long aLong = this.redisTemplate.opsForList().rightPush(redisKey, param.getValue());
        //行动轨迹24小时后失效
        if (null != aLong && aLong > 0) {
            result = this.redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
        }
        return result;
    }

    /**
     * Auth - 获取AuthToken
     */
    @Override
    public Object getAuthToken(final GetAuthTokenPo param) {

        if(StringUtils.isEmpty(param.getAuthJwtToken())){
            if (Strings.isNullOrEmpty(param.getUsername()) || Strings.isNullOrEmpty(param.getPassword())) {
                throw new BusinessException("用户名或密码为: NULL");
            }
            //获取AuthJwtToken
            log.info("Auth::access_token >>> {}", JSON.toJSONString(param));
            final Map<String, Object> authTokenResult = this.authServerFeign.getAuthToken(param.getGrantType(), param.getUsername(), param.getPassword(), param.getClientId(), param.getClientSecret());
            log.info("Auth::access_token <<< {}", authTokenResult);
            if (!authTokenResult.get("status").equals(200)) {
                throw new BusinessException(String.valueOf(authTokenResult.get("message")));
            }
            return authTokenResult.get("result");
        }else{
            //获取业务Token
            final AuthGetBusinessTokenPo authGetBusinessTokenPo = new AuthGetBusinessTokenPo().setAuthJwtToken(param.getAuthJwtToken());
            log.info("Auth::getClientAccTokenByJwt >>> {}", JSON.toJSONString(authGetBusinessTokenPo));
            final Map<String, Object> businessTokenResult = this.authServerFeign.getBusinessToken(authGetBusinessTokenPo);
            log.info("Auth::getClientAccTokenByJwt <<< {}", businessTokenResult);
            if (!businessTokenResult.get("status").equals(200)) {
                throw new BusinessException(String.valueOf(businessTokenResult.get("message")));
            }
            //获取LoginUserInfo
            final AuthGetLoginUserPo authGetLoginUserPo = new AuthGetLoginUserPo();
            log.info("Auth::getLoginUser >>> {}", JSON.toJSONString(authGetLoginUserPo));
            final Map<String, Object> loginUserResult = this.authServerFeign.getLoginUser(authGetLoginUserPo, param.getAuthJwtToken());
            log.info("Auth::getLoginUser <<< {}", loginUserResult);
            if (!loginUserResult.get("status").equals(200)) {
                throw new BusinessException(String.valueOf(loginUserResult.get("message")));
            }
            // 将用户信息放入缓存,指定登录信息过期时间45分钟
            Object businessToken = businessTokenResult.get("result");
            Assert.notNull(businessToken,"BusinessToken为: NULL");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode businessTokenJsonNode = objectMapper.readTree(objectMapper.writeValueAsString(businessToken));
                String jwtTokenKey = DigestUtils.md5Hex(businessTokenJsonNode.get("jwt_token").textValue());
                redisTemplate.opsForValue().set("loginUser:"+jwtTokenKey,businessTokenJsonNode.get("username").textValue(),CommonConstants.REDIS_TIME_OUT,TimeUnit.MINUTES);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            // 组装返回数据
            final HashMap<@Nullable String, @Nullable Object> result = Maps.newHashMap();
            result.put("businessToken", businessToken);
            result.put("loginUserInfo", loginUserResult.get("result"));
            return result;
        }
    }

    /**
     * Auth - 获取医院列表
     */
    @Override
    public Object getHospitals() {

        final AuthGetHospitalsPo authGetHospitalsPo = new AuthGetHospitalsPo();
        log.info("Auth::getHospitals >>> {}", JSON.toJSONString(authGetHospitalsPo));
        final Map<String, Object> hospitals = this.authServerFeign.getHospitals(authGetHospitalsPo);
        log.info("Auth::getHospitals <<< {}", hospitals);
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
        log.info("Auth::getAllUserByDeptGroup >>> {}", JSON.toJSONString(authGetAllUserByDeptGroupPo));
        final Map<String, Object> allUserByDeptGroup = this.authServerFeign.getAllUserByDeptGroup(authGetAllUserByDeptGroupPo,
                businessJwtToken);
        log.info("Auth::getAllUserByDeptGroup <<< {}", allUserByDeptGroup);
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
        log.info("Auth::getAllUserByDeptGroup >>> {}", JSON.toJSONString(authGetDepartmentsPo));
        final Map<String, Object> departments = this.authServerFeign.getDepartments(authGetDepartmentsPo);
        log.info("Auth::getAllUserByDeptGroup <<< {}", departments);
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
        log.info("Auth::getUserByRoleSign >>> {}", JSON.toJSONString(authGetUserByRoleSignPo));
        BeanUtils.copyProperties(param, authGetUserByRoleSignPo);
        final Map<String, Object> userByRoleSign = this.authServerFeign.getUserByRoleSign(authGetUserByRoleSignPo, param.getAuthJwtToken());
        log.info("Auth::getUserByRoleSign <<< {}", userByRoleSign);
        if (!userByRoleSign.get("status").equals(200)) {
            throw new BusinessException(String.valueOf(userByRoleSign.get("message")));
        }
        return userByRoleSign.get("result");
    }

    /**
     * IM - 给某个用户或者一些用户发送实时通知
     */
    @Override
    public Boolean sendNotice(final SendNoticePo param) {

        //判断任务ID是否存在
        if (null != param.getMissionId()) {
            final MedPhepNoticesEntity medPhepNoticesEntity = new MedPhepNoticesEntity().setMessageId(param.getMissionId()).setFromUserId(JSON.toJSONString(param.getUserIds())).setMessageContent(param.getMessage()).setNoticeTime(LocalDateTime.now());
            medPhepNoticesEntity.insert();
        }
        //发送通知
        log.info("IM::SendNotice >>> {}", JSON.toJSONString(param));
        final ResponseMessage<?> result;
        try {
            result = this.imServerFeign.imSendNotice(param);
            log.info("IM::SendNotice <<< {}", JSON.toJSONString(result));
            if ("0".equals(result.getCode())) {
                throw new BusinessException(result.getMessage());
            }
        } catch (final Exception e) {
            throw new BusinessException(String.format("IM::create 接口异常,%s", e.getMessage()));
        }
        return Boolean.TRUE;
    }

    /**
     * DAS - 推送数据
     */
    @Override
    public Boolean pushDataToDas(final String missionId) {

        final String toHospitalId = this.commonMapper.pushDataToDas(missionId);
        Assert.notNull(toHospitalId, "送往医院为: NULL");
        boolean result = Boolean.FALSE;
        //推送数据到DAS
        final DasNotificationPo dasNotificationPo = new DasNotificationPo()
                .setMissionId(missionId)
                .setHospitalId(toHospitalId)
                .setSysId(CommonEnum.PHEP.getKey())
                .setEventId(CommonEnum.NOTICE_EVENT.getKey());
        log.info("DAS::Notification >>> {}", JSON.toJSONString(dasNotificationPo));
        Map<String, Object> dasNotification = this.dasServerFeign.dasNotification(dasNotificationPo);
        log.info("DAS::Notification <<< {}", dasNotification);
        // 当推送成功后再记录到数据库
        if(dasNotification.get("success") instanceof Boolean){
            //告知时间记录到数据库
            final MedPhepPatientsEntity medPhepPatientsEntity =
                    this.medPhepPatientsMapper.selectOne(Wrappers.<MedPhepPatientsEntity>lambdaQuery().eq(MedPhepPatientsEntity::getMissionId, missionId));
            final MedPhepNoticesEntity medPhepNoticesEntity = new MedPhepNoticesEntity().setMissionId(missionId).setNoticeTime(LocalDateTime.now()).setPatientId(medPhepPatientsEntity.getPatientId());
            result = medPhepNoticesEntity.insert();
            if(result){
                MedPhepMedicalRecordsEntity medicalRecordsEntity = this.medPhepMedicalRecordsMapper.selectOne(Wrappers.<MedPhepMedicalRecordsEntity>lambdaUpdate().eq(MedPhepMedicalRecordsEntity::getPatientId, medPhepNoticesEntity.getPatientId()));
                if (!Objects.isNull(medicalRecordsEntity)) {
                    medicalRecordsEntity.setNotifyEmergencyTime(medPhepNoticesEntity.getNoticeTime());
                    medicalRecordsEntity.setIsNotifyEmergency(1);
                    result = this.medPhepMedicalRecordsMapper.update(medicalRecordsEntity,Wrappers.<MedPhepMedicalRecordsEntity>lambdaUpdate()
                            .eq(MedPhepMedicalRecordsEntity::getPatientId, medicalRecordsEntity.getPatientId())) > 0;
                }
            }
        }
        return result;
    }
}
