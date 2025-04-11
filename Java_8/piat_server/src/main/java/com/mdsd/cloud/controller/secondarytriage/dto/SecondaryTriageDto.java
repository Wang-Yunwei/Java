package com.mdsd.cloud.controller.secondarytriage.dto;

import com.mdsd.cloud.controller.secondarytriage.entity.ClinicSecondcheckEntity;
import com.mdsd.cloud.controller.secondarytriage.entity.ClinicVitalsignsEntity;
import lombok.Data;

/**
 * @description :
 * @author: wgy
 * @create: 2021-09-23 15:41
 **/

@Data
public class SecondaryTriageDto {


    private ClinicVitalsignsEntity clinicVitalsignsDto;


    private ClinicSecondcheckEntity clinicSecondcheckDto;
}
