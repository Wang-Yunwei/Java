package com.mdsd.cloud.util.dto.trauma;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-12-09]
 */
@Getter
@Setter
public class TraumaDto {

    @JsonProperty("Regist")
    private TCRegister regist;

//    @JsonProperty("PhepInfo")
//    private TCPhep phepInfo;
//
//    @JsonProperty("EmergencyInfo")
//    private TCEmergency emergencyInfo;
//
//    @JsonProperty("RecoveryInfo")
//    private TCRecovery recoveryInfo;
//
//    @JsonProperty("OperationInfo")
//    private TCOperation operationInfo;
//
//    @JsonProperty("IcuInfo")
//    private TCIcu icuInfo;
//
//    @JsonProperty("RescureInfo")
//    private TCRescuer rescureInfo;
//
//    @JsonProperty("TcTransferInfo")
//    private TCTransfer tcTransferInfo;
}
