package com.mdsd.cloud.controller.diagnosis.struct;

import com.mdsd.cloud.controller.diagnosis.vo.ConsentsSaveOrUpdateVo;
import com.mdsd.cloud.entity.MedPhepConsentsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huxiaoneng
 * @ClassName MedPhepConsentsMapsStruct
 * @Date 2021/11/12 17:08
 */
@Mapper(componentModel = "spring")
public interface MedPhepConsentsMapsStruct {

    MedPhepConsentsMapsStruct INSTANCE = Mappers.getMapper(MedPhepConsentsMapsStruct.class);

    //    @Mappings({@Mapping(source = "consentsSaveOrUpdateVo.patientId",target = "patientId")})
    MedPhepConsentsEntity toSaveOrUpdate(ConsentsSaveOrUpdateVo consentsSaveOrUpdateVo);

}
