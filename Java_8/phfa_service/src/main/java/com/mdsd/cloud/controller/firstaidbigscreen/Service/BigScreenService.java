package com.mdsd.cloud.controller.firstaidbigscreen.Service;

import com.mdsd.cloud.controller.firstaidbigscreen.dto.*;

import java.util.List;

/**
 * @author WangYunwei [2022-04-06]
 */
public interface BigScreenService {

    /**
     * 急救公告大屏 - 24h内急救患者列表
     */
    PatientList24hReo patientList24h(PatientList24hPo patientList24hPo);

    /**
     * 急救公告大屏 - 时间轴
     */
    List<TimeAxisReo> timeAxis(String hospitalArea);

    /**
     * 急救公告大屏 - 移动轨迹
     */
    List<MobilityTraceReo> mobilityTrace(MobilityTracePo param);
}
