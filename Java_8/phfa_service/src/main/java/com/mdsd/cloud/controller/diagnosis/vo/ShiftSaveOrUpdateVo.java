package com.mdsd.cloud.controller.diagnosis.vo;

import com.mdsd.cloud.entity.MedPhepGeneralInspectionsEntity;
import com.mdsd.cloud.entity.MedPhepShiftRecordEntity;
import lombok.Data;

/**
 * @author huxiaoneng
 */
@Data
public class ShiftSaveOrUpdateVo {

    private MedPhepShiftRecordEntity medPhepShiftRecordEntity;

    private MedPhepGeneralInspectionsEntity medPhepGeneralInspectionsEntity;
}
