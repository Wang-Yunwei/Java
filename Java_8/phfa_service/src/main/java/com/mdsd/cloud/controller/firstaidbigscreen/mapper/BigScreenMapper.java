package com.mdsd.cloud.controller.firstaidbigscreen.mapper;

import com.mdsd.cloud.controller.firstaidbigscreen.dto.MobilityTraceReo;
import com.mdsd.cloud.controller.firstaidbigscreen.dto.PatientInfoListDto;
import com.mdsd.cloud.controller.firstaidbigscreen.dto.PatientList24hPo;
import com.mdsd.cloud.controller.firstaidbigscreen.dto.TimeAxisReo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangYunwei [2022-04-06]
 */
@Mapper
public interface BigScreenMapper {

    /**
     * 急救公告大屏 - 24h内急救患者列表
     */
    List<PatientInfoListDto> patientList24h(PatientList24hPo patientList24hPo);

    /**
     * 急救公告大屏 - 时间轴
     */
    List<TimeAxisReo> timeAxis(String hospitalArea);

    /**
     * 急救公告大屏 - 移动轨迹
     */
    List<MobilityTraceReo> mobilityTrace(@Param("list") List<String> missionIds, @Param("hospitalId") String hospitalId);
}
