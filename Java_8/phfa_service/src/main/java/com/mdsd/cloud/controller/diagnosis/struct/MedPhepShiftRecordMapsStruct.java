package com.mdsd.cloud.controller.diagnosis.struct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huxiaoneng
 * @ClassName MedPhepShiftRecordMapsStruct
 * @Date 2021/11/15 16:45
 */
@Mapper
public interface MedPhepShiftRecordMapsStruct {

    MedPhepShiftRecordMapsStruct RECORDS = Mappers.getMapper(MedPhepShiftRecordMapsStruct.class);

}
