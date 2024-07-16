package com.mdsd.cloud.controller.socket.dto;

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
public class RegisterInp {

    @Schema(description = "帧头", type = "uit16", maxLength = 2)
    private short frameHeader = 0x7479;

    @Schema(description = "数据长度", type = "uit16", maxLength = 2)
    private int dataLength;

    @Schema(description = "指令编号", type = "uint8", maxLength = 1)
    private byte instructNum = 0x01;

    @Schema(description = "用户编号 (鉴权接口返回的 companyId)", type = "uint32", maxLength = 4)
    private int userNum;

    @Schema(description = "鉴权接口返回的accessToken", type = "byte[]")
    private byte[] accessToken;

}
