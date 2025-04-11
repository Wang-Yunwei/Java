package com.mdsd.cloud.util.dto.hrn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-12-13]
 */
@Getter
@Setter
public class HrnDto {

    @JsonProperty("Patient")
    private HRNPatient patient;
}
