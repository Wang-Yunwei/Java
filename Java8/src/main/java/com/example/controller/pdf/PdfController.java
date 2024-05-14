package com.example.controller.pdf;

import com.example.controller.pdf.dto.FirstAidMedicalRecordsPo;
import com.example.controller.pdf.dto.TriageDetailPo;
import com.example.controller.pdf.dto.TriageTickertapePo;
import com.example.controller.pdf.dto.WristbandsPo;
import com.example.controller.pdf.service.PdfService;
import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WangYunwei [2022-01-26]
 */
@RestController
@Api(value = "WEB - PdfController", produces = MediaType.APPLICATION_JSON_VALUE, tags = "PDF")
public class PdfController {

    PdfService pdfService;

    public PdfController(PdfService pdfService) {

        this.pdfService = pdfService;
    }

    @ApiOperation(value = "PDF - 就诊凭条")
    @PostMapping(name = "就诊凭条", path = "/triageTickertape")
    public void triageTickertape(@RequestBody TriageTickertapePo triageTickertapePo, HttpServletResponse response) throws IOException {

        triageTickertapePo.setResponse(response);
        this.pdfService.triageTickertape(triageTickertapePo);
    }

    @ApiOperation(value = "PDF - 腕带")
    @PostMapping(name = "腕带", path = "/wristbands")
    public void wristbands(@RequestBody WristbandsPo wristbandsPo, HttpServletResponse response) throws IOException, WriterException {

        wristbandsPo.setResponse(response);
        this.pdfService.wristbands(wristbandsPo);
    }

    @ApiOperation(value = "PDF - 分诊明细")
    @PostMapping(name = "急诊明细", path = "/triageDetail")
    public void triageDetail(@RequestBody TriageDetailPo triageDetailPo, HttpServletResponse response) throws IOException {

        triageDetailPo.setResponse(response);
        this.pdfService.triageDetail(triageDetailPo);
    }

    @ApiOperation(value = "PDF - 急救病历 ")
    @PostMapping(name = "急救病历", path = "/firstAidMedicalRecords")
    public void firstAidMedicalRecords(@RequestBody FirstAidMedicalRecordsPo firstAidMedicalRecordsPo, HttpServletResponse response) throws IOException {

        firstAidMedicalRecordsPo.setResponse(response);
        this.pdfService.firstAidMedicalRecords(firstAidMedicalRecordsPo);
    }

    @ApiOperation(value = "PDF - 交接单")
    @GetMapping(name = "交接单", path = "/pdfDeliveryFrom/{patientId}")
    public void pdfDeliveryFrom(@PathVariable final String patientId, final HttpServletResponse response) throws IOException {

        this.pdfService.pdfDeliveryFrom(patientId, response);
    }

    @ApiOperation(value = "PDF - 知情同意书")
    @GetMapping(name = "知情同意书", path = "/pdfConsentBook/{patientId}")
    public void pdfConsentBook(@PathVariable final String patientId, final HttpServletResponse response) throws IOException {

        this.pdfService.pdfConsentBook(patientId, response);
    }
}
