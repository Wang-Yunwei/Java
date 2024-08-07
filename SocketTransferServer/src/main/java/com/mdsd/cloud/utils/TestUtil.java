package com.mdsd.cloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author WangYunwei [2024-07-14]
 */
public class TestUtil {

    public static void main(String[] args) throws IOException {

        ObjectMapper om = new ObjectMapper();

        Path path = Paths.get("D:\\software\\Tencent\\WXWork\\WXWork\\1688850855241706\\Cache\\File\\2024-08\\recorder.mp3");
        byte[] bytes = Files.readAllBytes(path);

        String s = ByteUtil.bytesToStringUTF8(bytes);
        System.out.println(s);


    }
}
