package com.mdsd.cloud.controller.diagnosis.vo;

import com.mdsd.cloud.entity.*;
import lombok.Data;

import java.util.List;

/**
 * @author huxiaoneng
 */
@Data
public class ShiftRecordDetailVo {

    private List<MedPhepMeasuresEntity> measuresEntities;

    private MedPhepMedicalRecordsEntity medPhepMedicalRecordsEntity;

    private List<MedPhepEstimateEntity> estimateEntityList;

    private int ecgCount;

    private List<MedPhepGeneralInspectionsEntity> medPhepGeneralInspectionsEntity;

    private MedPhepShiftRecordEntity medPhepShiftRecordEntity;

}
