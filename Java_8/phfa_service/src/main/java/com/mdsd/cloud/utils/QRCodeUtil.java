package com.mdsd.cloud.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具类
 */
public class QRCodeUtil {
    private static final int width = 300;// 默认二维码宽度

    private static final int height = 300;// 默认二维码高度

    private static final String format = "png";// 默认二维码文件格式

    private static final Map<EncodeHintType, Object> hints = new HashMap<>();// 二维码参数

    static {
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 字符编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        hints.put(EncodeHintType.MARGIN, 2);// 二维码与图片边距
    }

    /**
     * 返回一个 BufferedImage 对象
     *
     * @param content 二维码内容
     * @param width   宽
     * @param height  高
     */
    public static BufferedImage toBufferedImage(final String content, final int width, final int height) throws WriterException, IOException {

        final BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * 将二维码图片输出到一个流中
     *
     * @param content 二维码内容
     * @param stream  输出流
     * @param width   宽
     * @param height  高
     */
    public static void writeToStream(final String content, final OutputStream stream, final int width, final int height) throws WriterException, IOException {

        final BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
    }

    /**
     * 生成二维码图片文件
     *
     * @param content 二维码内容
     * @param path    文件保存路径
     * @param width   宽
     * @param height  高
     */
    public static void createQRCode(final String content, final String path, final int width, final int height) throws WriterException, IOException {

        final BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        final BitMatrix bitMatrix1 = updateBit(bitMatrix, 1);
        //toPath() 方法由 jdk1.7 及以上提供
        MatrixToImageWriter.writeToPath(bitMatrix1, format, new File(path).toPath());
    }

    private static BitMatrix updateBit(final BitMatrix bitMatrix, final int margin) {

        final int tempM = margin * 2;
        final int[] rec = bitMatrix.getEnclosingRectangle();
        final int resWidth = rec[2] + tempM;
        final int resHeight = rec[3] + tempM;
        final BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = margin; i < resWidth - margin; i++) {
            for (int j = margin; j < resHeight - margin; j++) {
                if (bitMatrix.get(i - margin + rec[0], j - margin + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }
}
