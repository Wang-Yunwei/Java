package com.mdsd.cloud.feign.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2022-12-16]
 */
@Getter
@Setter
@Accessors(chain = true)
public class GetHospitalInfoPo {

    private String clientSign = "phfa";

    private String clientSecret = "xiangmu3";

    private String hospitalSign;
}
