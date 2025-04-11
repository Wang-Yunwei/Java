package com.mdsd.cloud.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author huxiaoneng
 */
public class PngResponseUtil {

    /**
     * 生成数据流 PNG
     */
    public static void buildPngDocument(final String fileName, final HttpServletResponse response) {

        OutputStream out = null;
        try {
            if (fileName != null) {
                byte[] byteArray = null;
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName + " ");
                final InputStream input = new FileInputStream(new File(fileName));
                final int lenth = input.available();
                byteArray = new byte[lenth];
                input.read(byteArray);
                out = response.getOutputStream();
                out.write(byteArray);
                out.flush();
                out.close();
                input.close();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
