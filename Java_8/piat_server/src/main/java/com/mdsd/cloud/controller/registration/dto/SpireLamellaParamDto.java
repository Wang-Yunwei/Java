package com.mdsd.cloud.controller.registration.dto;

import com.mdsd.cloud.controller.groupevents.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2021-11-04]
 */
@Getter
@Setter
@ApiModel(value = "个人登记 - 腕带_入参")
public class SpireLamellaParamDto extends BaseDto {

    @ApiModelProperty(value = "搜索值")
    private String searchValue;

    @ApiModelProperty(value = "锚点(0-未绑定,1-已绑定,2-解除绑定)")
    private Short anchorPoint = 2;

    @ApiModelProperty(value = "需解除绑定的分诊ID")
    private List<String> regList;

//    @ApiModelProperty(value = "列表总数")
//    private long listTotal;

    /*@ApiModelProperty(value = "页码")
    private long pageNo = 1;

    @ApiModelProperty(value = "页长")
    private long pageSize = 10;*/
}
