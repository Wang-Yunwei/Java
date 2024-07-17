package com.mdsd.cloud.controller.airport.service.impl;

import com.mdsd.cloud.controller.airport.dto.HangarListOup;
import com.mdsd.cloud.controller.airport.dto.PlanLineDTO;
import com.mdsd.cloud.controller.airport.dto.UpdateAirportInp;
import com.mdsd.cloud.controller.airport.service.AirportService;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * @author WangYunwei [2024-07-12]
 */
@Service
public class AirportServiceImpl implements AirportService {
    /**
     * 机库列表
     */
    @Override
    public HangarListOup hangarList() {

        return null;
    }

    /**
     * 机库控制
     */
    @Override
    public void hangarControl(String hangarId, Integer instructId) {

    }

    /**
     * 规划机库航线
     */
    @Override
    public void line(PlanLineDTO param) {

        Base64.getEncoder();
    }

    /**
     * 修改机库信息
     */
    @Override
    public void update(UpdateAirportInp param) {

    }

    /**
     * 获取舱外视频地址
     */
    @Override
    public String videoOut(String hangarId, String type) {

        return null;
    }

    /**
     * 获取舱内视频地址
     */
    @Override
    public String videoIn(String hangarId, String type) {

        return null;
    }
}
