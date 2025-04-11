package com.mdsd.cloud.controller.cardnumbermanage.struct.entity;

import com.mdsd.cloud.controller.cardnumbermanage.dto.HisParamDto;
import com.mdsd.cloud.controller.cardnumbermanage.dto.RegitionRelationResultDto;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicCheckListEntity;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicRegitionRelationEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicCardInfoEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicCheckMasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/10/12 19:38
 */
@Mapper(componentModel = "spring")
public interface EntityConverter {

    /**
     * @param checkMasterEntity
     * @param hisParamDto
     * @return
     */
    @Mappings(value = {
            @Mapping(source = "checkMasterEntity.regId.", target = "regId"),
            @Mapping(source = "hisParamDto.hisNo.", target = "registerNo"),
            @Mapping(source = "hisParamDto.registTime.", target = "registerTime"),
            @Mapping(source = "hisParamDto.visitId.", target = "clinicCard"),
            @Mapping(source = "hisParamDto.registerType.", target = "cardType"),
            @Mapping(source = "hisParamDto.patientId.", target = "patientId"),
            @Mapping(source = "hisParamDto.deptCode.", target = "departmentId"),
            @Mapping(source = "hisParamDto.deptName.", target = "departmentName"),
            @Mapping(source = "hisParamDto.doctorId.", target = "doctorId"),
            @Mapping(source = "hisParamDto.doctorName.", target = "doctorName")
    })
    MedClinicRegitionRelationEntity regitionRelationEntityConverter(MedClinicCheckMasterEntity checkMasterEntity, HisParamDto hisParamDto);

    /**
     * 穿梭框功能copy使用
     *
     * @param checkMasterEntity
     * @param hisParamDto
     * @return
     */
    @Mappings(value = {
            @Mapping(source = "checkMasterEntity.regId.", target = "regId"),
            @Mapping(source = "hisParamDto.hisNo.", target = "registerNo"),
            @Mapping(source = "hisParamDto.registTime.", target = "registerTime"),
            @Mapping(source = "hisParamDto.visitId.", target = "clinicCard"),
            @Mapping(source = "hisParamDto.registerType.", target = "cardType"),
            @Mapping(source = "hisParamDto.patientId.", target = "patientId"),
            @Mapping(source = "hisParamDto.deptCode.", target = "departmentId"),
            @Mapping(source = "hisParamDto.deptName.", target = "departmentName"),
            @Mapping(source = "hisParamDto.doctorId.", target = "doctorId"),
            @Mapping(source = "hisParamDto.doctorName.", target = "doctorName")
    })
    MedClinicRegitionRelationEntity transfRegitionRelationEntityConverter(MedClinicCheckMasterEntity checkMasterEntity, RegitionRelationResultDto hisParamDto);

    /**
     * @param hisParamDto
     * @return
     */
    @Mappings(value = {
            @Mapping(source = "hisParamDto.visitId.", target = "cardId"),
            @Mapping(source = "hisParamDto.patientId.", target = "patientId"),
            @Mapping(source = "hisParamDto.name.", target = "name"),
            @Mapping(source = "hisParamDto.sex.", target = "gender"),
            @Mapping(source = "hisParamDto.registerType.", target = "cardType"),
            @Mapping(source = "hisParamDto.dateOfBirth.", target = "birthday"),
            @Mapping(source = "hisParamDto.idNo.", target = "identificationCard"),
            @Mapping(source = "hisParamDto.citizenCard.", target = "societyCard"),
            @Mapping(source = "hisParamDto.nation.", target = "nation"),
            @Mapping(source = "hisParamDto.mailAddress.", target = "address"),
            @Mapping(source = "hisParamDto.patJob.", target = "job"),
            @Mapping(source = "hisParamDto.phoneNumberBusiness.", target = "tel"),
            @Mapping(source = "hisParamDto.phoneNumberHome.", target = "mobilePhone"),
            @Mapping(source = "hisParamDto.nextOfKin.", target = "linkMan"),
            @Mapping(source = "hisParamDto.nextOfKinPhone.", target = "linkManTel")
    })
    MedClinicCardInfoEntity cardInfoEntityConverter(MedClinicCardInfoEntity cardInfoEntity, HisParamDto hisParamDto);

    /**
     * @param hisParamDto
     * @return
     */
    @Mappings(value = {
            @Mapping(source = "hisParamDto.visitId.", target = "cardId"),
            @Mapping(source = "hisParamDto.patientId.", target = "patientId"),
            @Mapping(source = "hisParamDto.name.", target = "name"),
            @Mapping(source = "hisParamDto.sex.", target = "gender"),
            @Mapping(source = "hisParamDto.registerType.", target = "cardType"),
            @Mapping(source = "hisParamDto.dateOfBirth.", target = "birthday"),
            @Mapping(source = "hisParamDto.idNo.", target = "identificationCard"),
            @Mapping(source = "hisParamDto.citizenCard.", target = "societyCard"),
            @Mapping(source = "hisParamDto.nation.", target = "nation"),
            @Mapping(source = "hisParamDto.mailAddress.", target = "address"),
            @Mapping(source = "hisParamDto.patJob.", target = "job"),
            @Mapping(source = "hisParamDto.phoneNumberBusiness.", target = "tel"),
            @Mapping(source = "hisParamDto.phoneNumberHome.", target = "mobilePhone"),
            @Mapping(source = "hisParamDto.nextOfKin.", target = "linkMan"),
            @Mapping(source = "hisParamDto.nextOfKinPhone.", target = "linkManTel")
    })
    MedClinicCardInfoEntity cardInfoEntityConverter(HisParamDto hisParamDto);

    /**
     * @param checkMasterEntity
     * @param hisParamDto
     * @param regitionRelationEntity
     * @return
     */
    @Mappings(value = {
            @Mapping(source = "checkMasterEntity.regId.", target = "regId"),
            @Mapping(source = "hisParamDto.patientId.", target = "patientId"),
            @Mapping(source = "regitionRelationEntity.id.", target = "registerNo"),
            @Mapping(source = "hisParamDto.createTime.", target = "createTime")
    })
    MedClinicCheckListEntity checkListEntityConverter(MedClinicCheckMasterEntity checkMasterEntity, HisParamDto hisParamDto,
                                                      MedClinicRegitionRelationEntity regitionRelationEntity);

}
