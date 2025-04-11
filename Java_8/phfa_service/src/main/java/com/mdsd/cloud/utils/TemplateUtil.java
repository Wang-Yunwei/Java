package com.mdsd.cloud.utils;

import com.mdsd.cloud.entity.MedTemplateEntity;
import com.mdsd.cloud.response.exception.BusinessException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * hxn
 * 模板自定义生成
 */
public class TemplateUtil {

    /**
     * @param code 编码
     * @param path 二维码存放路劲
     */
    public static void imageBuilder(final String code, final String path,
                                    final List<MedTemplateEntity> templateEntities, final MedTemplateEntity templateEntity1) {

        final int width = templateEntity1.getImageWidth();
        final int height = templateEntity1.getImageHeight();
        //得到图片缓冲区
        FileInputStream fileInputStream = null;
        try {
            QRCodeUtil.createQRCode(code, path, 80, 80);
            final BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            final File file = new File(path);
            if (!file.exists()) {
                throw new BusinessException("二维码存放地址有误");
            }
            fileInputStream = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fileInputStream);
            //得到它的绘制环境(这张图片的笔)
            final Graphics2D g2 = (Graphics2D) bi.getGraphics();
            g2.fillRect(0, 0, width, height);
            //设置颜色
            g2.setColor(Color.WHITE);
            // 将图片大小设置为大约4cm*4cm 具体根据纸张大小设定
            g2.drawRect(0, 0, width - 1, height - 1);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (final MedTemplateEntity templateEntity : templateEntities) {
                if (templateEntity.getContent().equals("image")) {
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                    g2.drawImage(image, templateEntity.getXAxis(), templateEntity.getYAxis(), templateEntity.getImageWidth(), templateEntity.getImageHeight(), null);
                }
                //设置字体:字体、字号、大小
                g2.setFont(new Font(templateEntity.getTypeface(), templateEntity.getFontStyle(), templateEntity.getFontSize()));
                //设置背景颜色
                g2.setColor(Color.BLACK);
                if (templateEntity.getContentValue() != null) {
                    if (templateEntity.getContentValue().equals("emergencyDoctor") || templateEntity.getContentValue().equals("emergencyNurse")) {
                        if (templateEntity.getContent() != null && !templateEntity.getContent().equals("")) {
                            final URL url = new URL(templateEntity.getContent());
                            final InputStream is = url.openStream();
                            image = ImageIO.read(is);
                            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                            g2.drawImage(image, templateEntity.getXAxis(), templateEntity.getYAxis(), templateEntity.getImageWidth(), templateEntity.getImageHeight(), null);
                        }
                    } else if (templateEntity.getContentValue().equals("druge") || templateEntity.getContentValue().equals("accessoryExam")) {//用药信息
                        int y = templateEntity.getYAxis();
                        if (templateEntity.getContent() != null && !templateEntity.getContent().equals("")) {
                            final String[] drugs = templateEntity.getContent().split("\\|");
                            for (final String str :
                                    drugs) {
                                //向图片上写字符串
                                g2.drawString(str, templateEntity.getXAxis(), y);
                                y += 30;
                            }
                        }
                    } else {
                        //向图片上写字符串
                        g2.drawString(templateEntity.getContent(), templateEntity.getXAxis(), templateEntity.getYAxis());
                    }
                } else {
                    //向图片上写字符串
                    g2.drawString(templateEntity.getContent(), templateEntity.getXAxis(), templateEntity.getYAxis());
                }
            }
            g2.dispose();
            // 图片上传后的路径
            ImageIO.write(bi, "PNG", new FileOutputStream(path));
        } catch (final Exception e) {
            throw new BusinessException("生成图片错误: %s", e.getMessage());
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
