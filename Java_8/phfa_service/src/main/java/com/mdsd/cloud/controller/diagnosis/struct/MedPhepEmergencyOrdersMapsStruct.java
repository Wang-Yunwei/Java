package com.mdsd.cloud.controller.diagnosis.struct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huxiaoneng
 * @ClassName MedPhepEmergencyOrdersMapsStruct
 * @Date 2021/11/16 9:23
 */
@Mapper
public interface MedPhepEmergencyOrdersMapsStruct {

    MedPhepEmergencyOrdersMapsStruct ORDERS = Mappers.getMapper(MedPhepEmergencyOrdersMapsStruct.class);

}
