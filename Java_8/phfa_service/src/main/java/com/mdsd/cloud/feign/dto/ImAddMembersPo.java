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
@ApiModel(value = "ImAddMembersPo对象", description = "IM - 添加成员_入参")
public class ImAddMembersPo {

    @ApiModelProperty(value = "群组ID")
    private String channelId;

    @ApiModelProperty(value = "用户ID列表")
    private List<String> userIds;
}
