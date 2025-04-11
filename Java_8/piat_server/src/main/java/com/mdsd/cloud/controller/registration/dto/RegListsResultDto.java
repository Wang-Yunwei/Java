package com.mdsd.cloud.controller.registration.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-09-16]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 列表_返参")
public class RegListsResultDto {

    @ExcelProperty(index = 0, value = "序号")
    @ApiModelProperty(value = "序号")
    private String serialNumber;

    @ExcelIgnore
    @ApiModelProperty(value = "分诊时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime triageTime;

    @ExcelProperty(index = 1, value = "日期")
    @ApiModelProperty(value = "日期")
    private String regDate;

    @ExcelProperty(index = 2, value = "日期")
    @ApiModelProperty(value = "时间")
    private String regTime;

    @ExcelIgnore
    @ApiModelProperty(value = "急诊编号")
    private String regId;

    @ExcelProperty(index = 3, value = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

    @ExcelProperty(index = 4, value = "性别")
    @ApiModelProperty(value = "性别")
    private String gender;

    @ExcelIgnore
    @ApiModelProperty(value = "就诊时年龄")
    private BigDecimal regAge;

    @ExcelProperty(index = 5, value = "年龄")
    @ApiModelProperty(value = "就诊时年龄(补充)")
    private String regAgeCaption;

    @ExcelProperty(index = 6, value = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String tel;

    @ExcelProperty(index = 7, value = "工作单位/家庭住址")
    @ApiModelProperty(value = "住址")
    private String address;

    @ExcelProperty(index = 8, value = "主诉")
    @ApiModelProperty(value = "患者主诉")
    private String subjective;

    @ExcelIgnore
    @ApiModelProperty(value = "腕带编号")
    private String tagNo;

    @ExcelProperty(index = 9, value = "诊断")
    @ApiModelProperty(value = "诊断")
    private String diagnoseDescribe;

    @ExcelProperty(index = 10, value = "来诊方式")
    @ApiModelProperty(value = "来院方式")
    private String arriveType;

    @ExcelIgnore
    @ApiModelProperty(value = "生命体征")
    private String mcvInfo;

    @ExcelProperty(index = 11, value = "T")
    @ApiModelProperty(value = "体温")
    private String temperature;

    @ExcelProperty(index = 12, value = "P")
    @ApiModelProperty(value = "脉搏(次/分钟)")
    private String plus;

    @ExcelProperty(index = 13, value = "R")
    @ApiModelProperty(value = "呼吸(次/分钟)")
    private String breath;

    @ExcelProperty(index = 14, value = "BP")
    @ApiModelProperty(value = "血压")
    private String bp;

    @ExcelProperty(index = 15, value = "意识")
    @ApiModelProperty(value = "意识")
    private String mind;

    @ExcelProperty(index = 16, value = "分诊级别")
    @ApiModelProperty(value = "分诊级别")
    private String resetLevel;

    @ExcelProperty(index = 17, value = "分诊护士")
    @ApiModelProperty(value = "分诊护士")
    private String nurseId;

    @ExcelProperty(index = 18, value = "就诊科室")
    @ApiModelProperty(value = "就诊科室")
    private String departmentId;

    @ExcelProperty(index = 19, value = "就诊医师")
    @ApiModelProperty(value = "就诊医师")
    private String checkDoctorId;

    @ExcelProperty(index = 20, value = "患者去向")
    @ApiModelProperty(value = "去向")
    private String turnResult;
}
