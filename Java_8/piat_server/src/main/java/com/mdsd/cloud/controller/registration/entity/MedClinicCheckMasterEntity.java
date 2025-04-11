package com.mdsd.cloud.controller.registration.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author WangYunwei [2021-09-15]
 */
@Getter
@Setter
@TableName(value = "MED_CLINIC_CHECK_MASTER")
public class MedClinicCheckMasterEntity extends Model {

    /**
     * 就诊主表_主键ID
     */
    @ApiModelProperty(value = "就诊主表_主键ID(新增: 后端自处理)")
    @TableId(value = "REG_ID", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private String regId;

    /**
     * 发热时间
     */
    @ApiModelProperty(value = "发热时间")
    @TableField(value = "FEVER_TIME")
    private LocalDateTime feverTime;

    /**
     * 病床号
     */
    @ApiModelProperty(value = "病床号")
    @TableField(value = "BED_ID")
    private String bedId;

    /**
     * 急救ID
     */
    @ApiModelProperty(value = "急救ID")
    @TableField(value = "PHEP_PID")
    private String phepPid;

    /**
     * 就诊医生
     */
    @ApiModelProperty(value = "就诊医生")
    @TableField(value = "CHECK_DOCTOR_ID")
    private String checkDoctorId;

    /**
     * 就诊科室
     */
    @ApiModelProperty(value = "就诊科室")
    @TableField(value = "DEPARTMENT_ID")
    private String departmentId;

    /**
     * 群伤事件ID
     */
    @ApiModelProperty(value = "群伤事件ID")
    @TableField(value = "EVENTID")
    private String eventid;

    /**
     * 120接诊地址
     */
    @ApiModelProperty(value = "120接诊地址")
    @TableField(value = "ACCEPT_ADDRESS")
    private String acceptAddress;

    /**
     * 120接诊时间
     */
    @ApiModelProperty(value = "120接诊时间")
    @TableField(value = "ACCEPT_TIME")
    private LocalDateTime acceptTime;

    /**
     * 发病地址
     */
    @ApiModelProperty(value = "发病地址")
    @TableField(value = "ADDRESS")
    private String address;

    /**
     * 到院时间
     */
    @ApiModelProperty(value = "到院时间")
    @TableField(value = "ARRIVE_TIME")
    private LocalDateTime arriveTime;

    /**
     * 到院方式
     */
    @ApiModelProperty(value = "来院方式")
    @TableField(value = "ARRIVE_TYPE")
    private String arriveType;

    /**
     * 陪送/110/120/相关人员地址
     */
    @ApiModelProperty(value = "陪送/110/120/相关人员地址")
    @TableField(value = "ARRIVEWITH_ADDRESS")
    private String arrivewithAddress;

    /**
     * 陪送/110/120/相关人员姓名
     */
    @ApiModelProperty(value = "陪送/110/120/相关人员姓名")
    @TableField(value = "ARRIVEWITH_NAME")
    private String arrivewithName;

    /**
     * 陪送/110/120/相关人员电话
     */
    @ApiModelProperty(value = "陪送/110/120/相关人员电话")
    @TableField(value = "ARRIVEWITH_TEL")
    private String arrivewithTel;

    /**
     * 绿色通道
     */
    @ApiModelProperty(value = "绿色通道")
    @TableField(value = "CHANNEL")
    private String channel;

    /**
     * 就诊状态
     */
    @ApiModelProperty(value = "就诊状态")
    @TableField(value = "CHECK_STATUS")
    private String checkStatus;

    /**
     * 会诊情况
     */
    @ApiModelProperty(value = "会诊情况")
    @TableField(value = "CHECK_TOGETHER")
    private String checkTogether;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(value = "CREATE_ID")
    private String createId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATE_TIME")
    private LocalDateTime createTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "DESCRIBE")
    private String describe;

    /**
     * 诊断
     */
    @ApiModelProperty(value = "诊断")
    @TableField(value = "DIAGNOSE_DESCRIBE")
    private String diagnoseDescribe;

    /**
     * 诊断ICD
     */
    @ApiModelProperty(value = "诊断ICD")
    @TableField(value = "DIAGNOSE_ICD")
    private String diagnoseIcd;

    /**
     * 诊断ICD中文名称
     */
    @ApiModelProperty(value = "诊断ICD中文名称")
    @TableField(value = "DIAGNOSE_ICD_NAME")
    private String diagnoseIcdName;

    /**
     * 发病时间
     */
    @ApiModelProperty(value = "发病时间")
    @TableField(value = "DISEASE_TIME")
    private LocalDateTime diseaseTime;

    /**
     * 死亡或者动物接触史
     */
    @ApiModelProperty(value = "死亡或者动物接触史")
    @TableField(value = "DISEASE_TOUCH")
    private String diseaseTouch;

    /**
     * 疫区旅游史
     */
    @ApiModelProperty(value = "疫区旅游史")
    @TableField(value = "EPIDEMIC_AREA")
    private String epidemicArea;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    @TableField(value = "HEIGHT")
    private Short height;

    /**
     * 院区
     */
    @ApiModelProperty(value = "院区")
    @TableField(value = "HOSPITAL_ARER")
    private String hospitalArer;

    /**
     * 流行病病史
     */
    @ApiModelProperty(value = "流行病病史")
    @TableField(value = "INFECT_MSG")
    private String infectMsg;

    /**
     * 卡号表主键编号
     */
    @ApiModelProperty(value = "卡号表主键编号")
    @TableField(value = "INFO_ID")
    private String infoId;

    /**
     * 24小时体温超过38度
     */
    @ApiModelProperty(value = "24小时体温超过38度")
    @TableField(value = "IS_FEVER")
    private String isFever;

    /**
     * 入院前三天有发热学史
     */
    @ApiModelProperty(value = "入院前三天有发热学史")
    @TableField(value = "IS_HVAE_FEVER_HISTORY")
    private String isHvaeFeverHistory;

    /**
     * 三无人员
     */
    @ApiModelProperty(value = "三无患者(0-否,1-是)")
    @TableField(value = "IS_NONAME")
    private String isNoname;

    /**
     * 是否有效(0-否,1-是)
     */
    @ApiModelProperty(value = "是否有效(0-否,1-是)")
    @TableField(value = "IS_USED")
    private String isUsed;

    /**
     * 主诉判断依据主键编号
     */
    @ApiModelProperty(value = "主诉判断依据主键编号")
    @TableField(value = "KNOWLEDGE_ID")
    private String knowledgeId;

    /**
     * 系统分级
     */
    @ApiModelProperty(value = "系统分级")
    @TableField(value = "SYSLEVEL")
    private String syslevel;

    /**
     * 其它
     */
    @ApiModelProperty(value = "其他")
    @TableField(value = "OTHER")
    private String other;

    /**
     * 患者类别（成人、儿童）
     */
    @ApiModelProperty(value = "患者类别（成人、儿童）")
    @TableField(value = "PATIENT_TYPE")
    private String patientType;

    /**
     * 就诊时年龄
     */
    @ApiModelProperty(value = "就诊时年龄")
    @TableField(value = "REG_AGE")
    private BigDecimal regAge;

    /**
     * 就诊时年龄（补充）
     */
    @ApiModelProperty(value = "就诊时年龄（补充）")
    @TableField(value = "REG_AGE_CAPTION")
    private String regAgeCaption;

    /**
     * 就诊时间
     */
    @ApiModelProperty(value = "就诊时间")
    @TableField(value = "REG_TIME")
    private LocalDateTime regTime;

    /**
     * 分诊护士
     */
    @ApiModelProperty(value = "分诊护士")
    @TableField(value = "NURSE_ID")
    private String nurseId;

    /**
     * 护士分诊的区域
     */
    @ApiModelProperty(value = "护士分诊的区域")
    @TableField(value = "RESET_AREA")
    private String resetArea;

    /**
     * 护士分诊的级别
     */
    @ApiModelProperty(value = "护士分诊的级别")
    @TableField(value = "RESET_LEVEL")
    private String resetLevel;

    /**
     * 第二主诉
     */
    @ApiModelProperty(value = "第二主诉")
    @TableField(value = "SECOND_SUBJECTIVE")
    private String secondSubjective;

    /**
     * 专科
     */
    @ApiModelProperty(value = "专科")
    @TableField(value = "SPECIAL")
    private String special;

    /**
     * 患者主诉
     */
    @ApiModelProperty(value = "患者主诉")
    @TableField(value = "SUBJECTIVE")
    private String subjective;

    /**
     * 腕带编号
     */
    @ApiModelProperty(value = "腕带编号")
    @TableField(value = "TAG_NO")
    private String tagNo;

    /**
     * 分诊条号码
     */
    @ApiModelProperty(value = "分诊条号码")
    @TableField(value = "TRIAGE")
    private String triage;

    /**
     * 分诊时间
     */
    @ApiModelProperty(value = "分诊时间")
    @TableField(value = "TRIAGE_TIME")
    private LocalDateTime triageTime;

    /**
     * 去向
     */
    @ApiModelProperty(value = "去向")
    @TableField(value = "TURN_RESULT")
    private String turnResult;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @TableField(value = "UPDATE_ID")
    private String updateId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 体重
     */
    @ApiModelProperty(value = "体重")
    @TableField(value = "WEIGHT")
    private Short weight;

    @Override
    public Serializable pkVal() {

        return this.regId;
    }
}
