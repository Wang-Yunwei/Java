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

    @Schema(description = "用户编号 (鉴权接口返回的 companyId)", type = "uint32", maxLength = 4)
    private int userNum;

    @Schema(description = "鉴权接口返回的accessToken", type = "byte[]")
    private byte[] accessToken;

    @Schema(description = "时间戳", type = "uint64", maxLength = 8)
    private long timestamp;

    @Schema(description = "数据", type = "byte[]")
    private byte[] data;
}
