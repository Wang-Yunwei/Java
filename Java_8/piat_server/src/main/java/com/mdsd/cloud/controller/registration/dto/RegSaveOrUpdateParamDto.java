package com.mdsd.cloud.controller.registration.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WangYunwei [2021-09-14]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 新增Or更新_入参")
public class RegSaveOrUpdateParamDto {

    @ApiModelProperty(value = "患者卡号信息表_主键ID(注: 新增操作时后端自处理)")
    private String infoId;

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    @ApiModelProperty(value = "创建人")
    private String createId;

    @ApiModelProperty(value = "创建时间")
    
    private LocalDateTime createTime;

    @ApiModelProperty(value = "腕带编号")
    private String tagNo;

    @ApiModelProperty(value = "分诊护士")
    private String nurseId;

    @ApiModelProperty(value = "急诊编号")
    private String regId;

    @ApiModelProperty(value = "病历号")
    private String patientId;

    @ApiModelProperty(value = "三无患者(0-否,1-是)")
    private String isNoname;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(0:女,1:男,2:未知)")
    private String gender;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "联系电话")
    @TableField(value = "TEL")
    private String tel;

    @ApiModelProperty(value = "证件类型")
    private String idcardType;

    @ApiModelProperty(value = "证件号码")
    private String identificationCard;

    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "年龄")
    private BigDecimal regAge;

    @ApiModelProperty(value = "年龄补充")
    private String regAgeCaption;

    @ApiModelProperty(value = "分诊时间")
    private LocalDateTime triageTime;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "住址")
    private String address;

    @ApiModelProperty(value = "备注")
    private String describe;

    @ApiModelProperty(value = "绿色通道")
    private String channel;

    @ApiModelProperty(value = "其他")
    private String other;

    @ApiModelProperty(value = "来院方式")
    private String arriveType;

    @ApiModelProperty(value = "患者主诉")
    private String subjective;

    @ApiModelProperty(value = "第二主诉")
    private String secondSubjective;

    @ApiModelProperty(value = "主诉判断依据主键编号")
    private String knowledgeId;

    @ApiModelProperty(value = "生、到、流")
    private DatasDto datas;

    @ApiModelProperty(value = "患者评分")
    private List<ScoreDto> scores;

    @ApiModelProperty(value = "分级")
    private GradeDto grade;

    @ApiModelProperty(value = "患者去向")
    private WhereaboutsDto whereabouts;

    @ApiModelProperty(value = "急救ID")
    private String phepPid;

    @ApiModelProperty(value = "院区")
    private String hospitalArer;
}
