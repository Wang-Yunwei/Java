package com.mdsd.cloud.controller.airport.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mdsd.cloud.controller.airport.dto.HangarListOup;
import com.mdsd.cloud.controller.airport.dto.PlanLineDTO;
import com.mdsd.cloud.controller.airport.dto.UpdateAirportInp;

/**
 * @author WangYunwei [2024-07-12]
 */
public interface AirportService {

    /**
     * 机库列表
     */
    HangarListOup hangarList();

    /**
     * 机库控制
     */
    void hangarControl(String hangarId, Integer instructId);

    /**
     * 规划机库航线
     */
    void line(PlanLineDTO param) throws JsonProcessingException;

    /**
     * 修改机库信息
     */
    void update(UpdateAirportInp param);

    /**
     * 获取舱外视频地址
     */
    String videoOut(String hangarId, String type);

    /**
     * 获取舱内视频地址
     */
    String videoIn(String hangarId, String type);
}
