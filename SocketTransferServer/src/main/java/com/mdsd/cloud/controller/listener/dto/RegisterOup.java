package com.mdsd.cloud.controller.listener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-14]
 */
@Getter
@Setter
@Accessors(chain = true)
public class RegisterOup {

    @Schema(description = "帧头", type = "uit16", maxLength = 2)
    private short frameHeader = 0x6A77;

    @Schema(description = "数据长度", type = "uit16", maxLength = 2)
    private short dataLength;

    @Schema(description = "指令编号", type = "uint8", maxLength = 1)
    private byte instructNum = 0x01;

    @Schema(description = "是否成功", type = "uint8", maxLength = 1)
    private byte isSuccess;
    
}
