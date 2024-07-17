package com.mdsd.cloud.utils;

import com.mdsd.cloud.controller.transfer.enums.InstructEnum;
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
        buffer.writeByte(0xD1);

        int ab = 0xD1;

        // 假设我们想查看写入的数据
        // 注意：ByteBuf没有直接的toString()方法来显示所有字节，但我们可以手动遍历
        for (int i = 0; i < buffer.readableBytes(); i++) {
            byte b = buffer.getByte(i); // 这里为了示例，我们实际读取了数据，但在实际场景中，你可能只是想查看而不修改readerIndex
            System.out.printf("Byte %d: 0x%02X%n", i, b);

            System.out.println(b);
            System.out.println(ab);
            System.out.println(ab == (b & 0xFF));
        }

        byte ac = (byte) 0xD1;

        System.out.println(ab == ac);

        InstructEnum anEnum = InstructEnum.getEnum(0xD1, 0x36);

        switch (anEnum) {
            case 对焦 -> System.out.printf("对焦 %d",anEnum.getInstruct());
            case 上避障设置 -> System.out.printf("上避障 %d",anEnum.getInstruct());
        }
    }
}
