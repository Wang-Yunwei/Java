package com.mdsd.cloud.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author WangYunwei [2025-05-16]
 */
public class ExecShell {

    public static String exec(String cmd) {
        StringBuffer result = new StringBuffer();
        try {
            // 执行命令
            Process process = Runtime.getRuntime().exec("Linux".equals(System.getProperties().getProperty("os.name")) ? cmd : "cmd /c " + cmd);
            // 读取输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
