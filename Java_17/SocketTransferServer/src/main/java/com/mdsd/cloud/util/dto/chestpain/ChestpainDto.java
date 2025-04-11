package com.mdsd.cloud.util.dto.chestpain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author WangYunwei [2024-12-13]
 */
@Getter
@Setter
public class ChestpainDto {

    @JsonProperty("Patient")
    private CPCPatien patient;
}
