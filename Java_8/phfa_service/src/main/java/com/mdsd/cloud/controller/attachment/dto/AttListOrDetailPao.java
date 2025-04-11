package com.mdsd.cloud.controller.attachment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-04-11]
 */
@Getter
@Setter
@ApiModel(value = "AttListOrDetailPao对象", description = "附件 - 列表or详情_入参")
public class AttListOrDetailPao {

    /**
     * 附件ID(注: 此字段有值则默认查详情)
     */
    @ApiModelProperty(value = "附件ID(注: 此字段有值则默认查详情)")
    private Long attId;

    /**
     * 业务ID
     */
    @ApiModelProperty(value = "业务ID")
    private String businessId;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String fileName;
}
