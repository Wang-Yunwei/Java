package com.mdsd.cloud.controller.registration.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2021-10-26]
 */
@Getter
@Setter
public class GcsDto {

    private String type;

    private List<GetScoreDetailsResultDto> value;
}
