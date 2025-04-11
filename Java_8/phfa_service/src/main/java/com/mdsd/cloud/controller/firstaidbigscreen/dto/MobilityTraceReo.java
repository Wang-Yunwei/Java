package com.mdsd.cloud.controller.firstaidbigscreen.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mdsd.cloud.entity.MedPhepMeasuresEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-04-06]
 */
@Getter
@Setter
@ApiModel(value = "急救公告大屏 - 移动轨迹_返参")
public class MobilityTraceReo {

    /**
     * 任务ID
     */
    @ApiModelProperty(value = "任务ID <MedPhepMissionsEntity::MISSION_ID>")
    private String missionId;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号 <MedPhepMissionsEntity::LICENSE_PLATE>")
    private String licensePlate;

    /**
     * 现场地址
     */
    @ApiModelProperty(value = "现场地址 <MedPhepMissionsEntity::DESTINATION_ADDRESS>")
    private String destinationAddress;

    /**
     * 接收任务时间
     */
    @ApiModelProperty(value = "接收任务时间 <MedPhepMissionsEntity::ACCEPT_MISSION_TIME>")
    private LocalDateTime acceptMissionTime;

    /**
     * 到达现场时间
     */
    @ApiModelProperty(value = "到达现场时间 <MedPhepMissionsEntity::ARRIVED_SCENE_TIME>")
    private LocalDateTime arrivedSceneTime;

    /**
     * 接触患者时间
     */
    @ApiModelProperty(value = "接触患者时间 <MedPhepMissionsEntity::TOUCH_PATIENT_TIME>")
    private LocalDateTime touchPatientTime;

    /**
     * 送往医院时间
     */
    @ApiModelProperty(value = "送往医院时间 <MedPhepMissionsEntity::LEFT_SCENE_TIME>")
    private LocalDateTime leftSceneTime;

    /**
     * 到达医院时间
     */
    @ApiModelProperty(value = "到达医院时间 <MedPhepMissionsEntity::ARRIVED_HOSPITAL_TIME>")
    private LocalDateTime arrivedHospitalTime;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态 <MedPhepMissionsEntity::STATUS>")
    private String missionStatus;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名 <MedPhepPatientsEntity::NAME>")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别 <MedPhepPatientsEntity::GENDER>")
    private String gender;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄 <MedPhepPatientsEntity::AGE>")
    private String age;

    /**
     * 年龄类别
     */
    @ApiModelProperty(value = "年龄类别 <MedPhepPatientsEntity::AGE_EXTEND>")
    private String ageExtend;

    /**
     * 患者ID
     */
    @ApiModelProperty(value = "患者ID <MedPhepMedicalRecordsEntity::PATIENT_ID>")
    private String patientId;

    /**
     * 送往医院ID
     */
    @ApiModelProperty(value = "送往医院ID <MedPhepMedicalRecordsEntity::TO_HOSPITAL>")
    private String toHospitalId;

    /**
     * 送往医院名称
     */
    @ApiModelProperty(value = "送往医院名称 <MedPhepMedicalRecordsEntity::TO_HOSPITAL_NAME>")
    private String toHospitalName;

    /**
     * 疾病类别
     */
    @ApiModelProperty(value = "疾病类别 <MedPhepMedicalRecordsEntity::DISEASE_CATEGORY>")
    private String diseaseCategory;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断 <MedPhepMedicalRecordsEntity::DIAGNOSIS>")
    private String diagnosis;

    /**
     * 急救措施
     */
    @ApiModelProperty(value = "急救措施 <MedPhepMedicalRecordsEntity::TREATMENT>")
    private String treatment;

    /**
     * 急救措施
     */
    private List<MedPhepMeasuresEntity> medPhepMeasuresEntities;

    /**
     * 呼救原因
     */
    @ApiModelProperty(value = "呼救原因 <MedPhepMedicalRecordsEntity::REASON_TYPE>")
    private String reasonType;

    /**
     * 接诊地址
     */
    @ApiModelProperty(value = "接诊地址 -> 现场救治地点 <MedPhepMedicalRecordsEntity::ADDRESS>")
    private String address;

    /**
     * 脉搏(次/分)
     */
    @ApiModelProperty(value = "脉搏(次/分) <MedPhepGeneralInspectionsEntity::PULSE>")
    private BigDecimal pulse;

    /**
     * 舒张压
     */
    @ApiModelProperty(value = "舒张压 <MedPhepGeneralInspectionsEntity::DBP>")
    private Float dbp;

    /**
     * 收缩压
     */
    @ApiModelProperty(value = "收缩压 <MedPhepGeneralInspectionsEntity::SBP>")
    private Float sbp;

    /**
     * 血氧
     */
    @ApiModelProperty(value = "血氧 <MedPhepGeneralInspectionsEntity::SPO2>")
    private String spo2;

    /**
     * 呼吸频率(次/分)
     */
    @ApiModelProperty(value = "呼吸频率(次/分) <MedPhepGeneralInspectionsEntity::RR>")
    private BigDecimal rr;

    /**
     * 出车分站
     */
    @ApiModelProperty(value = "出车分站 <MedPhepStationEntity::NAME>")
    private String dispatchVehicleSite;

    /**
     * 起点
     */
    @ApiModelProperty(value = "起点")
    private Map<String, BigDecimal> startPoint = Maps.newHashMap();

    /**
     * 终点
     */
    @ApiModelProperty(value = "终点")
    private Map<String, BigDecimal> endPoint = Maps.newHashMap();

    /**
     * 经过点
     */
    @ApiModelProperty(value = "经过点")
    private List<Map<String, BigDecimal>> throughPoint = Lists.newArrayList();
}
