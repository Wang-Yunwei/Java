package com.mdsd.cloud.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author WangYunwei [2024-07-14]
 */
public class TestUtil {

    public static void main(String[] args) {

        // 创建一个非池化的ByteBuf实例
        ByteBuf buffer = Unpooled.buffer();

        // 写入一个字节
        buffer.writeByte((byte) 0x01);

        // 再次写入一个字节
        buffer.writeByte((byte) 0x02);

        buffer.writeInt(50);

        // 假设我们想查看写入的数据
        // 注意：ByteBuf没有直接的toString()方法来显示所有字节，但我们可以手动遍历
        for (int i = 0; i < buffer.readableBytes(); i++) {
            byte b = buffer.getByte(i); // 这里为了示例，我们实际读取了数据，但在实际场景中，你可能只是想查看而不修改readerIndex
            System.out.printf("Byte %d: 0x%02X%n", i, b);
        }

        Integer ab = 0xD1;

        System.out.println(ab == 0xD1);
    }
}