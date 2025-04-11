package com.mdsd.cloud.util;

import com.mdsd.cloud.controller.dji.dto.WayPointV2MissionSettingsDTO;
import com.mdsd.cloud.controller.tyjw.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.tyjw.dto.PointDataDTO;

import java.util.ArrayList;

/**
 * @author WangYunwei [2025-04-03]
 */
public class DjiParameterMapping {

    /**
     * 转换航线数据
     */
    public static WayPointV2MissionSettingsDTO getAirLine(PlanLineDataDTO dto) {
        WayPointV2MissionSettingsDTO settingsDTO = new WayPointV2MissionSettingsDTO();
        settingsDTO.setMaxFlightSpeed(dto.getMaxSpeed());
        settingsDTO.setAutoFlightSpeed(dto.getAutoSpeed());

        settingsDTO.setMissTotalLen((short) dto.getPoints().size());
        ArrayList<WayPointV2MissionSettingsDTO.WayPointV2Mission> mission = new ArrayList<>();
        for (PointDataDTO pointData : dto.getPoints()) {
            WayPointV2MissionSettingsDTO.WayPointV2Mission wayPointV2Mission = settingsDTO.new WayPointV2Mission();
            wayPointV2Mission.setLongitude(pointData.getLng());
            wayPointV2Mission.setLatitude(pointData.getLat());
            wayPointV2Mission.setRelativeHeight(pointData.getHeight());


            mission.add(wayPointV2Mission);
        }
        settingsDTO.setMission(mission);


        return settingsDTO;
    }
}
