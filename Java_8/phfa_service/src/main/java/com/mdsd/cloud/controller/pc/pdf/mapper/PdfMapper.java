package com.mdsd.cloud.controller.pc.pdf.mapper;

import com.mdsd.cloud.controller.pc.pdf.dto.PdfMedicalRecordDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WangYunwei [2022-06-23]
 */
@Mapper
public interface PdfMapper {

    /**
     * PDF - 院前医疗急救病历
     *
     * @param patientId 患者ID
     * @return PdfMedicalRecordDto
     */
    PdfMedicalRecordDto pdfMedicalRecord(String patientId);
}
