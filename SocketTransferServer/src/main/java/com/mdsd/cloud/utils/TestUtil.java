package com.mdsd.cloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author WangYunwei [2024-07-14]
 */
public class TestUtil {



    public static void main(String[] args) throws IOException {

        ObjectMapper om = new ObjectMapper();

        // 创建一个非池化的ByteBuf实例
        ByteBuf buffer = Unpooled.buffer();

        // 写入一个 byte,长度 1
        byte by = 0x11;
//        buffer.writeByte(by);

        // 写入一个 short,长度 2
        short sh = 288;
//        buffer.writeShort(sh);

        // 写入一个 int,长度 4
        int in = 388;
//        buffer.writeInt(in);

        // 写入一个 float,长度 4
        float fl = 288.8f;
//        buffer.writeFloat(fl);

        // 写入一个 double,长度 8
        double dou = 288.88d;
//        buffer.writeDouble(dou);

        // 写入一个 byte[]
        String str = "{\"sites\":{\"site\":[{\"id\":\"1\",\"name\":\"菜鸟教程\",\"url\":\"www.runoob.com\"},{\"id\":\"2\",\"name\":\"菜鸟工具\",\"url\":\"www.jyshare.com\"},{\"id\":\"3\",\"name\":\"Google\",\"url\":\"www.google.com\"}]}}";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes);
//        byte[] encode = Base64.getEncoder().encode(bytes);
//        buffer.writeBytes(encode);


        int bufLength = buffer.readableBytes(); // 获取 buffer 长度

        byte[] bytes1 = new byte[bytes.length];
//        byte[] bytes1 = new byte[encode.length];
        buffer.readBytes(bytes1);
        System.out.println(om.readValue(bytes1, Map.class));
//        System.out.println(new String(bytes1,StandardCharsets.UTF_8));
//        System.out.println(new String(Base64.getDecoder().decode(bytes1),StandardCharsets.UTF_8));

        int byteValue = 125;
        byte sssx = (byte) 0xD1;
        String hexValue = String.format("0x%02X", sssx);
        System.out.println(hexValue);
    }
}
