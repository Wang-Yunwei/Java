package com.mdsd.cloud.controller.cardnumbermanage.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/10/11 17:02
 */
@Getter
@Setter
public class ParamListDto implements Serializable {

    private HisParamDto hisParamDto;

    private CardInfoParamDto cardInfoParamDto;

    private List<RegitionRelationResultDto> regitionRelationResultDtosLeft;

    private List<RegitionRelationResultDto> regitionRelationResultDtosRight;
}
