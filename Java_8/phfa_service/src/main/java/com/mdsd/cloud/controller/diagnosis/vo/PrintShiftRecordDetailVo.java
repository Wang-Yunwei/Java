package com.mdsd.cloud.controller.diagnosis.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author huxiaoneng
 */
@Data
public class PrintShiftRecordDetailVo {

    /**
     * 病历ID
     */
    @ApiModelProperty(value = "病历ID")
    private String recordId;

    /**
     * 交接时间
     */
    @ApiModelProperty(value = "交接时间")

    private LocalDateTime shiftTime;

    /**
     * 出车分站
     */
    @ApiModelProperty(value = "出车分站")
    private String dispatchVehicleSite;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    private String licensePlate;

    /**
     * 交接医院
     */
    @ApiModelProperty(value = "交接医院")
    private String toHospital;

    /**
     * 交接科室
     */
    @ApiModelProperty(value = "交接科室")
    private String dept;

    /**
     * 现场地址
     */
    @ApiModelProperty(value = "现场地址")
    private String destinationAddress;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄")
    private int age;

    /**
     * 年龄拓展
     */
    @ApiModelProperty(value = "年龄拓展")
    private String ageExtend;

    /**
     * 患者信息
     */
    @ApiModelProperty(value = "患者信息")
    private String patientInformation;

    /**
     * 患者主诉
     */
    @ApiModelProperty(value = "患者主诉")
    private String chiefComplaint;

    /**
     * 初步诊断
     */
    @ApiModelProperty(value = "初步诊断")
    private String diagnosis;

    /**
     * 病情判断
     */
    @ApiModelProperty(value = "病情判断")
    private String conditionGrade;

    /**
     * 血糖（mmol/L）
     */
    @ApiModelProperty(value = "血糖")
    private Float bloodglucose;

    /**
     * 其他
     */
    @ApiModelProperty(value = "其他")
    private String otherInfo;

    /**
     * 出诊医生
     */
    @ApiModelProperty(value = "出诊医生")
    private String emergencyDoctor;

    /**
     * 接诊护士
     */
    @ApiModelProperty(value = "接诊护士")
    private String emergencyNurse;

    /**
     * 急救措施
     */
    @ApiModelProperty(value = "急救措施")
    private String treatment;

    /**
     * 用药信息
     */
    @ApiModelProperty(value = "用药信息")
    private String druge;

    /**
     * 其他用药
     */
    @ApiModelProperty(value = "其他用药")
    private String otherDruge;

    /**
     * 辅助检查
     */
    @ApiModelProperty(value = "辅助检查")
    private String accessoryExam;

}
