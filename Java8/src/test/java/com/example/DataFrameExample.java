package com.example;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author WangYunwei [2024-07-05]
 */
public class DataFrameExample {
    // 假设的数据
    private static final short FRAME_HEADER = 0x7479; // 帧头
    private static final String CLOUD_BOX_SN = "123456789012345"; // 云盒编号,确保长度不超过15
    private static final short COMMAND_ID = 0xD1; // 指令编号
    private static final byte ENCRYPTION_FLAG = 0x00; // 加密标志,假设未加密
    private static final byte ACTION_ID = 0x11; // 动作编号
    private static final float FLY_HEIGHT = 10.5f; // 起飞高度

    public static void main(String[] args) {
        // 构建数据帧
        byte[] dataFrame = buildDataFrame(FLY_HEIGHT);

        // 打印数据帧（以十六进制形式）
        printDataFrame(dataFrame);
    }

    private static byte[] buildDataFrame(float flyHeight) {
        // 计算数据长度（不包括帧头和自身）
        int dataLength = 2 + 2 + 15 + 1 + 1 + 1 + 4; // 云盒SN号 + 指令编号 + 加密标志 + 动作编号 + 飞起高度

        // 使用ByteBuffer来构建数据帧
        ByteBuffer buffer = ByteBuffer.allocate(dataLength); // 分配足够的空间给帧头和数据
        buffer.order(ByteOrder.BIG_ENDIAN); // 使用大端序

        // 写入帧头
        buffer.putShort(FRAME_HEADER);

        // 写入数据长度
        buffer.putShort((short) dataLength);

        // 写入云盒SN号
        byte[] snBytes = CLOUD_BOX_SN.getBytes();
        buffer.put(snBytes, 0, Math.min(snBytes.length, 15)); // 确保不超过15字节

        // 写入指令编号、加密标志和动作编号
        buffer.put((byte)(COMMAND_ID & 0xFF));
        System.out.println("COMMAND_ID: "+ COMMAND_ID);
        System.out.println(COMMAND_ID & 0xFF);

        buffer.put(ENCRYPTION_FLAG);
        buffer.put(ACTION_ID);
        System.out.println(ACTION_ID);

        // 写入飞起高度（转换为字节序列）
        int flyHeightBits = Float.floatToIntBits(flyHeight);
        buffer.putInt(flyHeightBits);

        System.out.println(buffer);


        // 翻转缓冲区（如果需要，取决于你如何使用ByteBuffer）
        // buffer.flip();

        // 返回构建好的数据帧
        return buffer.array();
    }

    private static void printDataFrame(byte[] dataFrame) {
        StringBuilder sb = new StringBuilder();
        sb.append("Data Frame (Hex): ");
        for (byte b : dataFrame) {
            sb.append(String.format("%02X ", b));
        }
        System.out.println(sb.toString().trim());
    }
}
