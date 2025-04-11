package com.mdsd.cloud.controller.diagnosis.struct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huxiaoneng
 * @ClassName MedPhepEpidemiologicalMapsStruct
 * @Date 2021/11/15 16:24
 */
@Mapper
public interface MedPhepEpidemiologicalMapsStruct {

    MedPhepEpidemiologicalMapsStruct EPIDEMIOLOGCAL = Mappers.getMapper(MedPhepEpidemiologicalMapsStruct.class);

}
