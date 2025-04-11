package com.mdsd.cloud.controller.triage.struct;

import com.mdsd.cloud.controller.triage.dto.TriSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.triage.entity.MedClinicTriageHelpEntity;
import org.mapstruct.Mapper;

/**
 * @author WangYunwei [2021-10-26]
 */
@Mapper(componentModel = "spring")
public interface TriageStruct {

    MedClinicTriageHelpEntity toMedClinicTriageHelpEntity(TriSaveOrUpdateParamDto triSaveOrUpdateParamDto);
}
