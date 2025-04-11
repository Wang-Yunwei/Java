package com.mdsd.cloud.utils;

import com.mdsd.cloud.controller.groupevents.entity.TemplateEntity;
import com.mdsd.cloud.response.exception.BusinessException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * hxn
 * 模板自定义生成
 */
public class TemplateUtil {
    /**
     * @param code     编码
     * @param path     二维码存放路劲
     * @param codeType 生成条码类型（1：二维码，2：条形码）
     */
    public static void imageBuilder(final String code, final String path, final String codeType,
                                    final List<TemplateEntity> templateEntities, final TemplateEntity templateEntity1) {

        final int width = templateEntity1.getImageWidth();
        final int height = templateEntity1.getImageHeight();
        //得到图片缓冲区
        FileInputStream fileInputStream = null;
        try {
            if (codeType.equals("1")) {
                QRCodeUtil.createQRCode(code, path, 80, 80);
            } else if (codeType.equals("2")) {
                BarcodeUtil.generateFile(code, path);
            }
            final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            final File file = new File(path);
            if (!file.exists()) {
                throw new BusinessException("二维码存放地址有误");
            }
            fileInputStream = new FileInputStream(file);
            final BufferedImage image = ImageIO.read(fileInputStream);
            //得到它的绘制环境(这张图片的笔)
            final Graphics2D g2 = (Graphics2D) bi.getGraphics();
            g2.fillRect(0, 0, width, height);
            //设置颜色
            g2.setColor(Color.WHITE);
            // 将图片大小设置为大约4cm*4cm 具体根据纸张大小设定
            g2.drawRect(0, 0, width - 1, height - 1);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (final TemplateEntity templateEntity : templateEntities) {
                if (templateEntity.getContent().equals("image")) {
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                    g2.drawImage(image, templateEntity.getXAxis(), templateEntity.getYAxis(), templateEntity.getImageWidth(), templateEntity.getImageHeight(), null);
                }
                //设置字体:字体、字号、大小
                g2.setFont(new Font(templateEntity.getTypeface(), templateEntity.getFontStyle(), templateEntity.getFontSize()));
                //设置背景颜色
                g2.setColor(Color.BLACK);
                //向图片上写字符串
                g2.drawString(templateEntity.getContent(), templateEntity.getXAxis(), templateEntity.getYAxis());
            }
            g2.dispose();
            // 图片上传后的路径
            ImageIO.write(bi, "PNG", new FileOutputStream(path));
        } catch (final Exception e) {
            throw new BusinessException(String.format("生成图片错误:%s", e));
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
