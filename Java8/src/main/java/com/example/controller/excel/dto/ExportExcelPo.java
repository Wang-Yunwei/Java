package com.example.controller.excel.dto;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author WangYunwei [2022-02-07]
 */
@Getter
@Setter
public class ExportExcelPo {

    private HttpServletResponse response;
}
