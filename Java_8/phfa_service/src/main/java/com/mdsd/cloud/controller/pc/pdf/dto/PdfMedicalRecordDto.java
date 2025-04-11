package com.mdsd.cloud.controller.pc.pdf.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-06-21]
 */
@Getter
@Setter
@ApiModel(value = "PdfMedicalRecordDto对象", description = "PDF - 院前医疗急救病历")
public class PdfMedicalRecordDto {

    @ApiModelProperty(value = "患者ID<MedPhepPatientsEntity::PATIENT_ID>")
    private String patientId;

    @ApiModelProperty(value = "患者姓名<MedPhepPatientsEntity::NAME>")
    private String name;

    @ApiModelProperty(value = "性别<MedPhepPatientsEntity::GENDER>")
    private String gender;

    @ApiModelProperty(value = "年龄<MedPhepPatientsEntity::AGE>")
    private String age;

    @ApiModelProperty(value = "年龄拓展<MedPhepPatientsEntity::AGE_EXTEND>")
    private String ageExtend;

    @ApiModelProperty(value = "病历ID<MedPhepMedicalRecordsEntity::RECORD_ID> -> 病历编号")
    private String recordId;

    @ApiModelProperty(value = "疾病类别<MedPhepMedicalRecordsEntity::DISEASE_CATEGORY> -> 病种类别")
    private String diseaseCategory;

    @ApiModelProperty(value = "现场救治地点<MedPhepMedicalRecordsEntity::ADDRESS> -> 接诊地址")
    private String address;

    @ApiModelProperty(value = "联系电话<MedPhepMedicalRecordsEntity::CONTACT_NUMBER>")
    private String contactNumber;

    @ApiModelProperty(value = "患者主诉<MedPhepMedicalRecordsEntity::CHIEF_COMPLAINT>")
    private String chiefComplaint;

    @ApiModelProperty(value = "现病史<MedPhepMedicalRecordsEntity::PRESENT_ILLNESS>")
    private String presentIllness;

    @ApiModelProperty(value = "既往史<MedPhepMedicalRecordsEntity::PAST_HISTORY>")
    private String pastHistory;

    @ApiModelProperty(value = "过敏史<MedPhepMedicalRecordsEntity::ALLERGIC_HISTORY>")
    private String allergicHistory;

    @ApiModelProperty(value = "初步诊断<MedPhepMedicalRecordsEntity::DIAGNOSIS>")
    private String diagnosis;

    @ApiModelProperty(value = "病情判断<MedPhepMedicalRecordsEntity::CONDITION_GRADE>")
    private String conditionGrade;

    @ApiModelProperty(value = "治疗措施<MedPhepMedicalRecordsEntity::TREATMENT> -> 急救处置")
    private String treatment;

    @ApiModelProperty(value = "出诊结果<MedPhepMedicalRecordsEntity::TREATMENT_RESULT>")
    private String treatmentResult;

    @ApiModelProperty(value = "病情转归<MedPhepMedicalRecordsEntity::RESCUE_CHANGE_RESULT>")
    private String rescueChangeResult;

    @ApiModelProperty(value = "医院名称<MedPhepMedicalRecordsEntity::TO_HOSPITAL_NAME> -> 送达医院")
    private String toHospitalName;

    @ApiModelProperty(value = "血糖(mmol/L)<MedPhepMedicalRecordsEntity::BLOODGLUCOSE>")
    private Float bloodglucose;

    @ApiModelProperty(value = "其它检查<MedPhepMedicalRecordsEntity::OTHER_CHECK>")
    private String otherCheck;

    @ApiModelProperty(value = "任务ID<MedPhepMissionsEntity::MISSION_ID>")
    private String missionId;

    @ApiModelProperty(value = "出车分站<MedPhepMissionsEntity::DISPATCH_VEHICLE_SITE> -> 分站")
    private String dispatchVehicleSite;

    @ApiModelProperty(value = "接收任务时间<MedPhepMissionsEntity::ACCEPT_MISSION_TIME> -> 接令时间")
    private String acceptMissionTime;

    @ApiModelProperty(value = "出车时间<MedPhepMissionsEntity::DISPATCH_VEHICLE_TIME>")
    private String dispatchVehicleTime;

    @ApiModelProperty(value = "到达现场时间<MedPhepMissionsEntity::ARRIVED_SCENE_TIME>")
    private String arrivedSceneTime;

    @ApiModelProperty(value = "离开现场时间<MedPhepMissionsEntity::LEFT_SCENE_TIME>")
    private String leftSceneTime;

    @ApiModelProperty(value = "到达医院时间<MedPhepMissionsEntity::ARRIVED_HOSPITAL_TIME>")
    private String arrivedHospitalTime;

    @ApiModelProperty(value = "完成时间<MedPhepMissionsEntity::COMPLETE_TIME>")
    private String completeTime;

    @ApiModelProperty(value = "车载医生<MedPhepMissionsEntity::DOCTOR_NAME> -> 出诊医生")
    private String doctorName;

    @ApiModelProperty(value = "车载护士<MedPhepMissionsEntity::NURSE_NAME> -> 出诊护士")
    private String nurseName;

    @ApiModelProperty(value = "车牌号<MedPhepMissionsEntity::LICENSE_PLATE>")
    private String licensePlate;

    @ApiModelProperty(value = "交接单ID<MED_PHEP_SHIFT_RECORD::SHIFT_RECORD_ID>")
    private String shiftRecordId;

    @ApiModelProperty(value = "急救医生<MED_PHEP_SHIFT_RECORD::DOCTOR>")
    private String doctor;

    @ApiModelProperty(value = "急救护士<MED_PHEP_SHIFT_RECORD::NURSE>")
    private String nurse;

    @ApiModelProperty(value = "出诊医生<MED_PHEP_SHIFT_RECORD::EMERGENCY_DOCTOR>")
    private String emergencyDoctor;

    @ApiModelProperty(value = "出诊护士<MED_PHEP_SHIFT_RECORD::EMERGENCY_NURSE>")
    private String emergencyNurse;

    @ApiModelProperty(value = "其他<MED_PHEP_SHIFT_RECORD::OTHER_INFO>")
    private String otherInfo;

    @ApiModelProperty(value = "交接时间<MED_PHEP_SHIFT_RECORD::SHIFT_TIME>")
    private String shiftTime;

    @ApiModelProperty(value = "交接科室<MED_PHEP_SHIFT_RECORD::DEPT>")
    private String dept;

    @ApiModelProperty(value = "生命体征数据")
    private String vitalSigns;

    private String ti;

    private String gcs;

    private String chestPain;

    private String killip;

    private String fast;

    @ApiModelProperty(value = "心电图")
    private String isEcg;

    @ApiModelProperty(value = "POCT检查")
    private String isPoct;

    @ApiModelProperty(value = "皮肤粘膜")
    private String skin;

    @ApiModelProperty(value = "瞳孔")
    private String pupilResponse;

    @ApiModelProperty(value = "对光反射")
    private String lightReflection;

    @ApiModelProperty(value = "辅助检查")
    private String assistantExamination;
}
