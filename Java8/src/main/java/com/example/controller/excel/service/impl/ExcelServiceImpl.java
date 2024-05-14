package com.example.controller.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.controller.excel.dto.ExportExcelPo;
import com.example.controller.excel.service.ExcelService;
import com.example.response.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author WangYunwei [2022-02-07]
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    /**
     * Excel - 导出Excel
     */
    @Override
    public void exportExcel(final ExportExcelPo exportExcelPo) {

        try {
            final ArrayList<String> arrayList = new ArrayList();
            exportExcelPo.getResponse().setContentType("application/vnd.ms-excel");
            exportExcelPo.getResponse().setCharacterEncoding("utf-8");
            final String fileName = URLEncoder.encode("急诊患者预检分诊登记表.xlsx", "UTF-8");
            exportExcelPo.getResponse().setHeader("Content-disposition", "attachment;filename=" + fileName);
            EasyExcel.write(exportExcelPo.getResponse().getOutputStream()).withTemplate(this.getClass().getClassLoader().getResourceAsStream("excel/regExportExcel.xlsx")).sheet().doFill(arrayList);
        } catch (final IOException e) {
            throw new BusinessException("Excel导出失败");
        }
    }
}
