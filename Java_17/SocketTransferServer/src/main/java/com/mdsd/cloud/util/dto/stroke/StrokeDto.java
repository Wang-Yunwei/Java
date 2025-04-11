package com.mdsd.cloud.util.dto.stroke;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-12-11]
 */
@Getter
@Setter
public class StrokeDto {

    @JsonProperty("Patient")
    private STPatient patient;
}
