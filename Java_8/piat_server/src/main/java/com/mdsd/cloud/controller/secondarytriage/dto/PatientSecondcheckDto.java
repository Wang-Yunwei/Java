package com.mdsd.cloud.controller.secondarytriage.dto;

import com.mdsd.cloud.controller.secondarytriage.entity.ClinicVitalsignsEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description :
 * @author: wgy
 * @create: 2021-09-24 10:03
 **/

@Data
public class PatientSecondcheckDto extends ClinicVitalsignsEntity {

    private String name;

    private String gender;

    private String regid;

    private String createtime;

    /**
     * 等級
     */
    private String resetlevel;

    /**
     * 区域
     */
    private String resetarea;

    private String turnresult;

    private String checkstatus;

    /**
     * 等待时间
     */
    private String waitfortime;

    /**
     * 原分诊等级
     */
    private String Yturnresult;

    /**
     * 原分诊区域
     */
    private String Yresetlevel;

    private LocalDateTime triagetime;

    private LocalDateTime visittime;

    /**
     * 年龄
     */
    private String regage;

    /**
     * 分诊护士
     */
    private String nurseid;

    private String type;

    private String describe;

    private String departmentid;

}
