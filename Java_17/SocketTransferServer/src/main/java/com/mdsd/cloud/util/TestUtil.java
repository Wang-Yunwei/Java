package com.mdsd.cloud.util;

import com.mdsd.cloud.controller.dji.dto.DjiProtoBuf;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * @author WangYunwei [2024-07-14]
 */
public class TestUtil {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

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


        // 加载图片
        String file = "D:\\Pictures\\Scr.jpg";
        Mat image = Imgcodecs.imread(file);

        if (image.empty()) {
            System.out.println("无法加载图片: " + file);
            return;
        }

        // 转换为灰度图像
        Mat grayImage = new Mat(image.size(), CvType.CV_8UC1);
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // 保存处理后的图片
        Imgcodecs.imwrite("output.jpg", grayImage);

        System.out.println("图片已成功转换为灰度并保存");
    }
}
