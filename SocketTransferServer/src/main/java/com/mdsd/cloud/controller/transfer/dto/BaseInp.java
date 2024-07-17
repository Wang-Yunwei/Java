package com.mdsd.cloud.controller.transfer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseInp {

    @Schema(description = "帧头", type = "uit16", maxLength = 2)
    private short frameHeader = 0x7479;

    @Schema(description = "数据长度", type = "uit16", maxLength = 2)
    private short dataLength;

    @Schema(description = "云盒SN号", type = "byte[]", maxLength = 15)
    private byte[] boxSn;

    @Schema(description = "指令编号", type = "uint8", maxLength = 1)
    private short instructNum = 0xD1;

    @Schema(description = "加密标志 (0x01-加密,0x00-未加密)", type = "uint8", maxLength = 1)
    private byte isEncrypt;

    @Schema(description = "动作编号", type = "uint8", maxLength = 1)
    private byte actionNum;

    @Schema(description = "航线数据", type = "byte[]")
    private byte[] data;
}
