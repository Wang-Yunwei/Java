package com.mdsd.cloud.controller.attachment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-04-13]
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(value = "AttSavePao对象", description = "附件 - 保存_入参")
public class AttSavePao {

    @ApiModelProperty("请求路径")
    private String requestPath;

    @ApiModelProperty("业务ID")
    private String businessId;

    @ApiModelProperty("文件类别(0-心电图,1-现场照片,2-现场视频,3-医生签名,4-护士签名)")
    private Integer fileType;

    @ApiModelProperty("备注")
    private String remark;
}
