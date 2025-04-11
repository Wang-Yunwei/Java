package com.mdsd.cloud.controller.registration.dto;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2021-09-16]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 列表_入参")
public class RegListsParamDto extends BaseDto {

    @ApiModelProperty(value = "医院ID")
    private String hospitalId;

    @ApiModelProperty(value = "分诊时间")
    private List<String> triageTime;

    @ApiModelProperty(value = "分诊时间-开始(后端自处理)")
    private LocalDateTime triageTimeStart;

    @ApiModelProperty(value = "分诊时间-结束(后端自处理)")
    private LocalDateTime triageTimeEnd;

    @ApiModelProperty(value = "三无患者")
    private String isNoname;

    @ApiModelProperty(value = "院前急救(0-否,1-是)")
    private Byte isFirstAid;

    @ApiModelProperty(value = "搜索值")
    private String searchValue;

    @ApiModelProperty(value = "就诊科室")
    private String departmentId;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "诊断")
    private String diagnoseDescribe;

    @ApiModelProperty(value = "分诊级别")
    private String resetLevel;

    @ApiModelProperty(value = "转归区域")
    private String turnResult;

    @ApiModelProperty(value = "绿色通道")
    private String greenChannel;

    @ApiModelProperty(value = "状态")
    private String isUsed;

    @ApiModelProperty(value = "主诉类别")
    private String categoryName;

    @ApiModelProperty(value = "排序")
    private Map<String, String> sortBy;

   /* @ApiModelProperty(value = "页码")
    private long pageNo;

    @ApiModelProperty(value = "页长")
    private long pageSize;*/
}
