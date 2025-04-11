package com.mdsd.cloud.feign.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author WangYunwei [2022-06-14]
 */
@Getter
@Setter
@Accessors(chain = true)//Lombok链式赋值注解
@ApiModel(value = "ImCreatePo对象", description = "IM - 创建群聊_入参")
public class ImCreatePo {

    @ApiModelProperty(value = "创建群的用户ID")
    private String createUserId;

    @ApiModelProperty(value = "管理员ID")
    private String adminUserId;

    @ApiModelProperty(value = "用户ID列表")
    private List<String> userIds;

    @ApiModelProperty(value = "群名称")
    private String channelName;
}
