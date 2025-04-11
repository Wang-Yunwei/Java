package com.mdsd.cloud.controller.pc.pdf.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WangYunwei [2022-06-20]
 */
public interface PdfService {

    /**
     * PDF - 院前医疗急救病历
     *
     * @param patientId 患者ID
     * @throws IOException
     */
    void pdfMedicalRecord(String patientId, HttpServletResponse response) throws IOException;

    /**
     * PDF - 交接单
     *
     * @param patientId 患者ID
     * @param response
     * @throws IOException
     */
    void pdfDeliveryFrom(final String patientId, final HttpServletResponse response) throws IOException;
}
