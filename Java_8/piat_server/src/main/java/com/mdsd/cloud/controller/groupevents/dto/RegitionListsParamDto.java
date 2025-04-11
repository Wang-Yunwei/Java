package com.mdsd.cloud.controller.groupevents.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */
@Data
public class RegitionListsParamDto extends BaseDto {

    /**
     * 分诊时间
     */
    private List<LocalDateTime> triageTime;

    /**
     * 分诊时间-开始(后端自处理)
     */
    private LocalDateTime triageTimeStart;

    /**
     * 分诊时间-结束(后端自处理)
     */
    private LocalDateTime triageTimeEnd;

    /**
     * 三无患者
     */
    private String isNoname;

    /**
     * 搜索值
     */
    private String searchValue;

    /**
     * 就诊科室
     */
    private String departmentId;

    /**
     * 性别
     */
    private String gender;

    /**
     * 诊断
     */
    private String diagnoseDescribe;

    /**
     * 分诊级别
     */
    private String resetLevel;

    /**
     * 转归区域
     */
    private String turnResult;

    /**
     * 绿色通道
     */
    private String channel;

    /**
     * 状态
     */
    private String checkStatus;

    /**
     * 主诉类别
     */
    private String categoryName;

}
