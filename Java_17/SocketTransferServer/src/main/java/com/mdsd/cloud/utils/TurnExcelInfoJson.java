package com.mdsd.cloud.utils;

import java.io.File;

/**
 * @author WangYunwei [2024-12-04]
 */
public class TurnExcelInfoJson {

    public static void main(String[] args) {
        String currentWorkingDir = System.getProperty("user.dir");
        File cwd = new File(currentWorkingDir);
        // 获取当前工作目录的根目录
        File rootDir = cwd.getParentFile();
        System.out.println("当前磁盘根目录: " + rootDir.getAbsolutePath());
    }
}
