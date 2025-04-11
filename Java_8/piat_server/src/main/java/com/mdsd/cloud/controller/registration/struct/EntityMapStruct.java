package com.mdsd.cloud.controller.registration.struct;

import com.mdsd.cloud.controller.registration.dto.ModSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.registration.dto.RegSaveOrUpdateParamDto;
import com.mdsd.cloud.controller.registration.dto.VitalSignsDto;
import com.mdsd.cloud.controller.registration.entity.MedClinicCardInfoEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicCheckMasterEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicModifyreasonEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicVitalsignsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author WangYunwei [2021-10-12]
 */
@Mapper(componentModel = "spring")
public interface EntityMapStruct {

    MedClinicCardInfoEntity toMedClinicCardInfoEntity(RegSaveOrUpdateParamDto regSaveOrUpdateParamDto);

    //    @Mapping(source = "xxx", target = "regId")/*就诊主表_主键ID(新增: 后端自处理)(自动映射)*/
//    @Mapping(source = "xxx", target = "phepPid")/*急救ID*/
    @Mapping(source = "datas.infectHistory.feverTime", target = "feverTime")/*发热时间*/
//    @Mapping(source = "xxx", target = "bedId")/*病床号*/
    @Mapping(source = "whereabouts.checkDoctorId", target = "checkDoctorId")/*就诊医生*/
    @Mapping(source = "whereabouts.departmentId", target = "departmentId")/*就诊科室*/
//    @Mapping(source = "xxx", target = "eventid")/*群伤事件ID*/
    @Mapping(source = "datas.arriveInfo.acceptAddress", target = "acceptAddress")/*120接诊地址*/
    @Mapping(source = "datas.arriveInfo.acceptTime", target = "acceptTime")/*120接诊时间*/
    @Mapping(source = "datas.arriveInfo.address", target = "address")/*发病地址*/
    @Mapping(source = "datas.arriveInfo.arriveTime", target = "arriveTime")/*到院时间*/
//    @Mapping(source = "xxx", target = "arriveType")/*来院方式(自动映射)*/
    @Mapping(source = "datas.arriveInfo.arrivewithAddress", target = "arrivewithAddress")/*陪送/110/120/相关人员地址*/
    @Mapping(source = "datas.arriveInfo.arrivewithName", target = "arrivewithName")/*陪送/110/120/相关人员姓名*/
    @Mapping(source = "datas.arriveInfo.arrivewithTel", target = "arrivewithTel")/*陪送/110/120/相关人员电话*/
//    @Mapping(source = "xxx", target = "channel")/*绿色通道(自动映射)*/
//    @Mapping(source = "xxx", target = "checkStatus")/*就诊状态*/
//    @Mapping(source = "xxx", target = "checkTogether")/*会诊情况*/
//    @Mapping(source = "xxx", target = "createId")/*创建人(自动映射)*/
//    @Mapping(source = "xxx", target = "createTime")/*创建时间(自动映射)*/
//    @Mapping(source = "xxx", target = "describe")/*备注(自动映射)*/
//    @Mapping(source = "xxx", target = "diagnoseDescribe")/*诊断*/
//    @Mapping(source = "xxx", target = "diagnoseIcd")/*诊断ICD*/
//    @Mapping(source = "xxx", target = "diagnoseIcdName")/*诊断ICD中文名称*/
    @Mapping(source = "datas.arriveInfo.diseaseTime", target = "diseaseTime")/*发病时间*/
    @Mapping(source = "datas.infectHistory.diseaseTouch", target = "diseaseTouch")/*死亡或者动物接触史*/
    @Mapping(source = "datas.infectHistory.epidemicArea", target = "epidemicArea")/*疫区旅游史*/
//    @Mapping(source = "xxx", target = "height")/*身高*/
//    @Mapping(source = "xxx", target = "hospitalArer")/*院区*/
    @Mapping(source = "datas.infectHistory.infectMsg", target = "infectMsg")/*流行病病史*/
//    @Mapping(source = "xxx", target = "infoId")/*卡号表主键编号*/
    @Mapping(source = "datas.infectHistory.isFever", target = "isFever")/*24小时体温超过38度*/
    @Mapping(source = "datas.infectHistory.isHvaeFeverHistory", target = "isHvaeFeverHistory")/*入院前三天有发热学史*/
//    @Mapping(source = "xxx", target = "isNoname")/*三无患者(0-否,1-是)(自动映射)*/
//    @Mapping(source = "xxx", target = "isUsed")/*是否有效(0-否,1-是)(无需映射)*/
//    @Mapping(source = "xxx", target = "knowledgeId")/*主诉判断依据主键编号(自动映射)*/
    @Mapping(source = "grade.syslevel", target = "syslevel")/*系统分级*/
//    @Mapping(source = "xxx", target = "other")/*其他(自动映射)*/
//    @Mapping(source = "xxx", target = "patientType")/*患者类别（成人、儿童）*/
//    @Mapping(source = "xxx", target = "regAge")/*就诊时年龄(自动映射)*/
//    @Mapping(source = "xxx", target = "regAgeCaption")/*就诊时年龄（补充）(自动映射)*/
//    @Mapping(source = "xxx", target = "regTime")/*就诊时间*/
//    @Mapping(source = "xxx", target = "nurseId")/*分诊护士(自动映射)*/
//    @Mapping(source = "xxx", target = "resetArea")/*护士分诊的区域*/
    @Mapping(source = "grade.resetLevel", target = "resetLevel")/*护士分诊的级别*/
//    @Mapping(source = "xxx", target = "secondSubjective")/*第二主诉(自动映射)*/
//    @Mapping(source = "xxx", target = "special")/*专科*/
//    @Mapping(source = "xxx", target = "subjective")/*患者主诉*/
//    @Mapping(source = "xxx", target = "tagNo")/*腕带编号*/
//    @Mapping(source = "xxx", target = "triage")/*分诊条号码*/
//    @Mapping(source = "xxx", target = "triageTime")/*分诊时间(自动映射)*/
    @Mapping(source = "whereabouts.turnResult", target = "turnResult")/*去向*/
    @Mapping(source = "createId", target = "updateId")/*更新人*/
    @Mapping(source = "createTime", target = "updateTime")/*更新时间*/
//    @Mapping(source = "xxx", target = "weight")/*体重*/
    MedClinicCheckMasterEntity toMedClinicCheckMasterEntity(RegSaveOrUpdateParamDto regSaveOrUpdateParamDto);

    MedClinicVitalsignsEntity toMedClinicVitalsignsEntity(VitalSignsDto vitalSignsDto);

    MedClinicModifyreasonEntity toMedClinicModifyreasonEntity(ModSaveOrUpdateParamDto modSaveOrUpdateParamDto);
}
