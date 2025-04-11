package com.mdsd.cloud.controller.registration.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2021-10-25]
 */
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GetScoreDetailsResultDto {

    private String type;

    private List<ValueDto> value;
}
