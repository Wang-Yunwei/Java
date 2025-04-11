package com.mdsd.cloud.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mdsd.cloud.constants.CommonConstants;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author wcq
 */
@Data
@TableName("MED_PHEP_TEMPLATE")
public class MedPhepTemplateEntity {

    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("TEMP_NAME")
    private String tempName;

    @TableField("TEMP_TYPE")
    private String tempType;

    @TableField("DISEASE_TYPE")
    private String diseaseType;

    @TableField("TEMP_CONTENT")
    private String tempContent;

    @TableField("SORT")
    private String sort;

    @TableField("CREATE_TIME")
    private java.sql.Date createTime;

    @TableField("CREATE_BY")
    private String createBy;

    @TableField("UPDATE_TIME")
    
    private LocalDateTime updateTime;

    @TableField("UPDATE_BY")
    private String updateBy;

    @TableField("STATUS")
    private String status;

}
