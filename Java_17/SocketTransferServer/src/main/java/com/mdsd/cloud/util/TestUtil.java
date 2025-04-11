package com.mdsd.cloud.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdsd.cloud.controller.dji.dto.WayPointV2MissionSettingsDTO;
import com.mdsd.cloud.controller.tyjw.dto.PlanLineDataDTO;
import com.mdsd.cloud.controller.tyjw.dto.PointDataDTO;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author WangYunwei [2024-07-14]
 */
public class TestUtil {



    public static void main(String[] args) throws IOException {

        ObjectMapper obm = new ObjectMapper();

        PlanLineDataDTO planLineDataDTO = new PlanLineDataDTO();
        planLineDataDTO.setMaxSpeed(2.3f);
        planLineDataDTO.setAutoSpeed(3.2f);

        ArrayList<PointDataDTO> pointDataDTOS = new ArrayList<>();
        for(int i=0;i<3;i++){
            PointDataDTO pointDataDTO = new PointDataDTO();
            pointDataDTO.setLng(2d);
            pointDataDTO.setLat(3d);
            pointDataDTO.setHeight(4f);
            pointDataDTOS.add(pointDataDTO);
        }
        planLineDataDTO.setPoints(pointDataDTOS);

        WayPointV2MissionSettingsDTO airLine = DjiParameterMapping.getAirLine(planLineDataDTO);
        System.out.println(obm.writeValueAsString(airLine));

    }
}
