package com.mdsd.cloud.controller.common.service;

import com.mdsd.cloud.controller.common.dto.GetAuthTokenPo;
import com.mdsd.cloud.controller.common.dto.GetDoctorOrNurseByHospitalPo;
import com.mdsd.cloud.controller.common.dto.PushDateToSSEPo;
import com.mdsd.cloud.controller.common.dto.RedisSaveToListPo;
import com.mdsd.cloud.feign.dto.SendNoticePo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WangYunwei [2022-07-21]
 */
public interface CommonService {

    /**
     * Common - 获取UUID
     */
    Object getUUID(HttpServletRequest request);

    /**
     * SSE - 建立连接
     */
    SseEmitter connectSSE(String sseId);

    /**
     * SSE - 推送数据
     */
    Boolean pushDataToSSE(PushDateToSSEPo param);

    /**
     * Redis - SaveToList
     */
    Boolean redisSaveToList(RedisSaveToListPo param);

    /**
     * Auth - 获取权限Token
     */
    Object getAuthToken(GetAuthTokenPo param);

    /**
     * Auth - 获取医院列表
     */
    Object getHospitals();

    /**
     * Auth - 获取所有用户
     */
    Object getSomeUser(String queryValue, String BusinessJwtToken);

    /**
     * Auth - 获取医院所有部门
     */
    Object getDepartments(String hospitalId);

    /**
     * Auth - 根据医院ID获取医生Or护士
     */
    Object getDoctorOrNurseByHospital(GetDoctorOrNurseByHospitalPo param);

    /**
     * IM - 给某个用户或者一些用户发送实时通知
     */
    Boolean sendNotice(SendNoticePo param);

    /**
     * DAS - 推送数据
     */
    Boolean pushDataToDas(String missionId);
}
