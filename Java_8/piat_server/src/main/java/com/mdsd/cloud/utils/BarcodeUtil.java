package com.mdsd.cloud.utils;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 条形码工具类
 */
public class BarcodeUtil {

    /**
     * 生成文件
     */
    public static File generateFile(final String msg, final String path) {

        final File file = new File(path);
        try {
            generate(msg, new FileOutputStream(file));
        } catch (final FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * 生成字节
     */
    public static byte[] generate(final String msg) {

        final ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous);
        return ous.toByteArray();
    }

    /**
     * 生成到流
     */
    public static void generate(final String msg, final OutputStream ous) {

        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        final Code39Bean bean = new Code39Bean();

        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(1.0f / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setWideFactor(3);
        bean.doQuietZone(false);

        final String format = "image/png";
        try {

            // 输出到流
            final BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生成条形码
            bean.generateBarcode(canvas, msg);

            // 结束绘制
            canvas.finish();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}