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
public class HeartbeatInp {

    @Schema(description = "帧头", type = "uit16", maxLength = 2)
    private short frameHeader = 0x7479;

    @Schema(description = "数据长度", type = "uit16", maxLength = 2)
    private short dataLength = 0x09;

    @Schema(description = "指令编号", type = "uint8", maxLength = 1)
    private byte instructNum = 0x02;

    @Schema(description = "时间戳", type = "uint64", maxLength = 8)
    private long timestamp;
}
