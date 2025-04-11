package com.mdsd.cloud.controller.other.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiaoneng.h
 * @date 2023/7/26 14:51
 */
@Data
public class AttachmentDto {
    /**
     * 附件表_主键ID
     */
    @ApiModelProperty(value = "附件表_主键ID(新增: 后端自处理)")
    private Long id;

    /**
     * 附件路径
     */
    @ApiModelProperty(value = "附件路径")
    private String attachPath;

    /**
     * 业务标识
     */
    @ApiModelProperty(value = "业务ID")
    private String businessId;

    /**
     * 说明
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 附件类别
     */
    @ApiModelProperty(value = "附件类别(0-心电图,1-现场照片,2-现场视频)")
    private Integer type;

}
