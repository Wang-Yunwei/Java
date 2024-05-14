package com.example.controller.excel;

import com.example.controller.excel.dto.ExportExcelPo;
import com.example.controller.excel.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author WangYunwei [2022-02-07]
 */
@RestController
@Api(value = "WEB - ExcelController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "excel")
public class ExcelController {

    ExcelService excelService;

    public ExcelController(final ExcelService excelService) {

        this.excelService = excelService;
    }

    @ApiOperation(value = "Excel - 导出Excel")
    public void exportExcel(@RequestBody final ExportExcelPo exportExcelPo, final HttpServletResponse response) {

        exportExcelPo.setResponse(response);

    }
}
