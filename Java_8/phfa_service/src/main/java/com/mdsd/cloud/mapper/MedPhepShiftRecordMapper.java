package com.mdsd.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.diagnosis.vo.PrintShiftRecordDetailVo;
import com.mdsd.cloud.entity.MedPhepShiftRecordEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author huxiaoneng
 * @ClassName MedPhepShiftRecordMapper 急救交接单
 * @Date 2021/11/12 15:02
 */
@Mapper
public interface MedPhepShiftRecordMapper extends BaseMapper<MedPhepShiftRecordEntity> {

    PrintShiftRecordDetailVo queryShiftRecord(String patientId);

    /**
     * 急救交接单 - 打印交接记录
     *
     * @param patientId 患者ID
     * @return Map<String, Object>
     */
    Map<String, Object> printDeliveryRecord(String patientId);
}
