package com.mdsd.cloud.controller.auxiliary.dto;

import com.mdsd.cloud.entity.MedPhepEstimateEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2021-12-01]
 */
@Getter
@Setter
@ApiModel(value = "辅助检查 - 评分_入参")
public class EstimateDto extends MedPhepEstimateEntity {

    /**
     * 评分细项
     */
    @ApiModelProperty(value = "评分细项")
    private List<EstimateItemDto> details;
}
