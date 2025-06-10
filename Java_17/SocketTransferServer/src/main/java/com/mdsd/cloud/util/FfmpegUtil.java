package com.mdsd.cloud.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author WangYunwei [2025-03-13]
 */
@Slf4j
public class FfmpegUtil {

    /**
     * @param streamPth 例如: rtmp://192.168.0.221/live/livestream
     */
    public static Process startProcess(String streamPth) {

        Process result = null;
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg",
                "-f","h264",
                "-probesize","32k",
                "-analyzeduration","32k",
                "-i", "pipe:0", // 从标准输入读取数据
                "-c", "copy", // 不重新编码，直接复制原始流
                "-flags", "low_delay",
                "-fflags", "nobuffer",
                "-flush_packets", "0",
                "-f", "flv", // 设置输出格式为FLV
                streamPth // SRS服务器地址和流路径
        );
        pb.redirectErrorStream(true); // 合并错误输出到标准输出
        try {
            result = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 获取子进程的 ProcessHandle 注册关闭钩子,在进程退出时执行
        result.toHandle().onExit().thenAccept(ph -> {
            // 在这里添加额外的清理逻辑
            log.info("视频流管道 {} 已关闭!",streamPth);
        });
        if(null != result){
            log.info("视频流管道 {} 已建立!",streamPth);
        }
        return result;
    }
}
