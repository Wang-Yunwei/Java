package com.mdsd.cloud.controller.common.service;

import com.mdsd.cloud.controller.common.dto.DasQueryPo;
import com.mdsd.cloud.controller.common.dto.GetAuthTokenPo;
import com.mdsd.cloud.controller.common.dto.GetBusinessTokenPo;
import com.mdsd.cloud.controller.common.dto.GetDoctorOrNurseByHospitalPo;
import com.mdsd.cloud.feign.dto.DasQueryRo;

import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-07-13]
 */
public interface CommonService {

    /**
     * 公共接口 - 院前急救
     *
     * @param dasQueryPo 入参
     * @return List<QueryRo>
     */
    List<DasQueryRo> dasQuery(DasQueryPo dasQueryPo);

    /**
     * Auth - 获取业务Token
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getBusinessToken(GetBusinessTokenPo param);

    /**
     * Auth - 获取权限Token
     *
     * @return Map<String, Object>
     */
    Object getAuthToken(GetAuthTokenPo param);

    /**
     * Auth - 获取医院列表
     *
     * @return Object
     */
    Object getHospitals();

    /**
     * Auth - 获取所有用户
     *
     * @param queryValue       模糊查询值
     * @param BusinessJwtToken 业务Token
     * @return Object
     */
    Object getSomeUser(String queryValue, String BusinessJwtToken);

    /**
     * Auth - 获取医院所有部门
     *
     * @param hospitalId 医院ID
     * @return Object
     */
    Object getDepartments(String hospitalId);

    /**
     * Auth - 根据医院ID获取医生Or护士
     *
     * @return Object
     */
    Object getDoctorOrNurseByHospital(GetDoctorOrNurseByHospitalPo param);
}
