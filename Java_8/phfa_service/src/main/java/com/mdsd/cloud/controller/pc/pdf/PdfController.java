package com.mdsd.cloud.controller.pc.pdf;

import com.mdsd.cloud.controller.pc.pdf.service.PdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 打印PDF
 *
 * @author WangYunwei [2022-06-20]
 */
@RestController
@RequestMapping("/pc/PdfController/v1")
@Api(value = "WEB - 打印PDF", tags = "PC - Pdf", produces = MediaType.APPLICATION_JSON_VALUE)
public class PdfController {

    PdfService pdfService;

    public PdfController(final PdfService pdfService) {

        this.pdfService = pdfService;
    }

    /**
     * PDF - 院前医疗急救病历
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "PDF - 院前医疗急救病历")
    @GetMapping(name = "院前医疗急救病历", value = "/pdfMedicalRecord/{patientId}")
    public void pdfMedicalRecord(@PathVariable final String patientId, final HttpServletResponse response) throws IOException {

        this.pdfService.pdfMedicalRecord(patientId, response);
    }

    /**
     * PDF - 交接单
     *
     * @param patientId 患者ID
     */
    @ApiOperation(value = "PDF - 交接单")
    @GetMapping(name = "交接单", value = "/pdfDeliveryFrom/{patientId}")
    public void pdfDeliveryFrom(@PathVariable final String patientId, final HttpServletResponse response) throws IOException {

        this.pdfService.pdfDeliveryFrom(patientId, response);
    }
}
