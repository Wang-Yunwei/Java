package com.mdsd.cloud.controller.dictionary.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2022-04-08]
 */
@Getter
@Setter
public class DdcAddBatchDto {

    /**
     * index: 代表第几列<br>
     * value: 表头数据,默认会写在excel的表头位置默认是第一行
     */
    @ExcelProperty(index = 0, value = "HOSPITAL_ID")
    private String hospitalId;

    @ExcelProperty(index = 1, value = "DICT_CODE")
    private String code;

    @ExcelProperty(index = 2, value = "DICT_NAME")
    private String name;

    @ExcelProperty(index = 3, value = "LABEL")
    private String itemname;

    @ExcelProperty(index = 4, value = "VALUE")
    private String itemcode;

    @ExcelProperty(index = 5, value = "REMARK")
    private String describe;

    @ExcelProperty(index = 6, value = "APP_ID")
    private String appid;

    @ExcelProperty(index = 7, value = "STATUS")
    private String status;
}
