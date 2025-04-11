package com.mdsd.cloud.controller.diagnosis.vo;

import lombok.Data;

@Data
public class DictionaryParamVo {

    /**
     * 医院ID
     */
    private String hospitalId;

    /**
     * 字典名称(注:支持模糊)
     */
    private String name;
}
