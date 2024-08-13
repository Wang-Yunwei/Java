package com.mdsd.cloud.controller.transfer;

import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * @author WangYunwei [2024-08-13]
 */
@FunctionalInterface
public interface TransferFunction {

    void argHandle(ByteBuf arg1, String[] arg2, Map<String,String> arg3);
}
