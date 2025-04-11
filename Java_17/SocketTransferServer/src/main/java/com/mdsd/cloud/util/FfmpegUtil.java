package com.mdsd.cloud.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author WangYunwei [2025-03-13]
 */
@Slf4j
public class FfmpegUtil {

    public static Process process;
    public static OutputStream outputStream;

    public static void startProcess() {
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-i", "pipe:0", // 从标准输入读取数据
                "-c", "copy", // 不重新编码，直接复制原始流
                "-f", "flv", // 设置输出格式为FLV
                "rtmp://192.168.0.221/live/livestream" // SRS服务器地址和流路径
        );
        pb.redirectErrorStream(true); // 合并错误输出到标准输出
        try {
            process = pb.start();
            // 获取子进程的 ProcessHandle 注册关闭钩子,在进程退出时执行
            process.toHandle().onExit().thenAccept(ph -> {
                // 在这里添加额外的清理逻辑
                log.info("FFmpeg Process Exit!");
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("FFmpeg Process 已启动,等待视频流传输");
        outputStream = process.getOutputStream();
    }
}
