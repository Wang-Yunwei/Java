package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2022-06-14]
 */
@Getter
@Setter
@ApiModel(value = "ImCreateRo对象", description = "IM - 创建群聊_返参")
public class ImCreateRo {

    @ApiModelProperty(value = "群组ID")
    private String channelId;

    @ApiModelProperty(value = "群组名称")
    private String name;

    @ApiModelProperty(value = "参与人员ID")
    private List<String> userIds;
}
