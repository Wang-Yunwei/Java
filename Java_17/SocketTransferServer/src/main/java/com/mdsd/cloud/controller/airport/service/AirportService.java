package com.mdsd.cloud.controller.airport.service;

import com.mdsd.cloud.controller.airport.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.airport.dto.RechargeDTO;
import com.mdsd.cloud.controller.airport.dto.UpdateAirportInp;

import java.util.List;

/**
 * @author WangYunwei [2024-07-12]
 */
public interface AirportService {

    /**
     * 机库列表
     */
    List<RechargeDTO> hangarList();

    /**
     * 机库控制
     */
    String hangarControl(String hangarId, Integer instructId);

    /**
     * 规划机库航线
     */
    String line(PlanLineDataDTO param);

    /**
     * 修改机库信息
     */
    String updateAirport(UpdateAirportInp param);

    /**
     * 获取舱外视频地址
     */
    String videoOut(String hangarId, String type);

    /**
     * 获取舱内视频地址
     */
    String videoIn(String hangarId, String type);
}