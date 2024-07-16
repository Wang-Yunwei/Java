package com.mdsd.cloud.controller.socket.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author WangYunwei [2024-07-14]
 */
public class HeartbeatOup {

    @Schema(description = "帧头", type = "uit16", maxLength = 2)
    private short frameHeader = 0x6A77;

    @Schema(description = "数据长度", type = "uit16", maxLength = 2)
    private short dataLength;

    @Schema(description = "指令编号", type = "uint8", maxLength = 1)
    private byte instructNum = 0x02;

    @Schema(description = "时间戳", type = "uint64", maxLength = 8)
    private long timestamp;
}
