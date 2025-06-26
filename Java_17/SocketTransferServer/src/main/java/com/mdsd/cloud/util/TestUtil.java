package com.mdsd.cloud.util;

import com.mdsd.cloud.controller.dji.dto.DjiProtoBuf;

/**
 * @author WangYunwei [2024-07-14]
 */
public class TestUtil {

    public static void main(String[] args) {

//        StringBuffer result = new StringBuffer();
//        try {
//            // 执行命令
//            Process process = Runtime.getRuntime().exec("cmd /c hostname");
//            // 读取输出
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                result.append(line);
//            }
//            int i = process.waitFor();
//            System.out.println("执行状态码:" + i);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Windows10".equals(result.toString()));


        System.out.println(DjiProtoBuf.ModuleEnum.M4_GIMBAL_MANAGER_VALUE);
    }
}
