package com.mdsd.cloud.controller.diagnosis.struct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huxiaoneng
 * @ClassName MedPhepMedicalRecordsMapsStruct
 * @Date 2021/11/15 11:32
 */
@Mapper
public interface MedPhepMedicalRecordsMapsStruct {

    MedPhepMedicalRecordsMapsStruct RECORDS = Mappers.getMapper(MedPhepMedicalRecordsMapsStruct.class);

}
