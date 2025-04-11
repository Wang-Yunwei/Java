package com.mdsd.cloud.controller.pc.pdf.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author WangYunwei [2022-05-19]
 */
@Component
public class PdfUtil {

    /**
     * @param pdPageContentStream 内容流
     * @param font                字体
     * @param fontSize            字体大小
     * @param renderingMode       字体粗细
     * @param leading             行间距
     * @param x                   输出内容x轴
     * @param y                   输出内容y轴
     * @param str                 输出内容
     */
    public static void contentStream(final PDPageContentStream pdPageContentStream, final PDType0Font font, final float fontSize, final RenderingMode renderingMode, final float leading, final float x, final float y, @NonNull final String str) throws IOException {

        pdPageContentStream.beginText();//开始文本
        if (0 != fontSize) {
            pdPageContentStream.setFont(font, fontSize);//设置文本字体
        }
        if (0 != leading) {
            pdPageContentStream.setLeading(leading);//行间距
        }
        if (Objects.nonNull(renderingMode)) {
            pdPageContentStream.setRenderingMode(renderingMode);//可设置字体粗细
        }
        pdPageContentStream.newLineAtOffset(x, y);//设置文本的位置
        pdPageContentStream.showText(str);//内容
        pdPageContentStream.endText();//结束文本
    }

    /**
     * @param pdPage    页面
     * @param font      字体
     * @param fontSize  字体大小
     * @param marginTop 顶部边距
     * @param str       内容
     * @return Map<String, Float> ——> map.get("x");map.get("y")
     */
    public static Map<String, Float> centerCoordinate(final PDPage pdPage, final PDType0Font font, final float fontSize, final float marginTop, final String str) throws IOException {

        final Map<String, Float> result = new HashMap<>();
        if (StringUtils.isNotBlank(str)) {
            final float titleWidth = font.getStringWidth(str) / 1000 * fontSize;
            final float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
            result.put("x", (pdPage.getMediaBox().getWidth() - titleWidth) / 2);
            result.put("y", pdPage.getMediaBox().getHeight() - marginTop - titleHeight);
        }
        return result;
    }

    /**
     * @param pdPageContentStream 内容输出流
     * @param font                字体
     * @param height              高
     * @param list                输出内容列表
     * @param sign                标记(0-表头;1-四列内容;2-三列内容)
     */
    public static void frameLine(final PDPageContentStream pdPageContentStream, final PDType0Font font, final float height, final List<String> list, final Integer sign) throws IOException {

        //上横线
        pdPageContentStream.moveTo(30F, height);
        pdPageContentStream.lineTo(565F, height);
        pdPageContentStream.stroke();
        //第一竖线
        final float verticalLineOne = 30F;
        pdPageContentStream.moveTo(verticalLineOne, height);
        pdPageContentStream.lineTo(verticalLineOne, height - 25);
        pdPageContentStream.stroke();
        //第二竖线
        final float verticalLineTwo = 130F;
        pdPageContentStream.moveTo(verticalLineTwo, height);
        pdPageContentStream.lineTo(verticalLineTwo, height - 25);
        pdPageContentStream.stroke();
        //第三竖线
        final float verticalLineThree = 235F;
        pdPageContentStream.moveTo(verticalLineThree, height);
        pdPageContentStream.lineTo(verticalLineThree, height - 25);
        pdPageContentStream.stroke();
        //第四竖线
        final float verticalLineFour = 490F;
        pdPageContentStream.moveTo(verticalLineFour, height);
        pdPageContentStream.lineTo(verticalLineFour, height - 25);
        pdPageContentStream.stroke();
        //第五竖线
        final float verticalLineFive = 565F;
        pdPageContentStream.moveTo(verticalLineFive, height);
        pdPageContentStream.lineTo(verticalLineFive, height - 25);
        pdPageContentStream.stroke();
        //下横线
        pdPageContentStream.moveTo(30F, height - 25);
        pdPageContentStream.lineTo(565F, height - 25);
        pdPageContentStream.stroke();

        final float heightTxt = height - 20;

        if (!CollectionUtils.isEmpty(list)) {
            switch (sign) {
                case 0:
                    PdfUtil.contentStream(pdPageContentStream, font, 12F, null, 0, (verticalLineThree - verticalLineTwo) / 2 + verticalLineTwo - font.getStringWidth(list.get(0)) / 1000 * 12 / 2, heightTxt, list.get(0));
                    PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, (verticalLineFour - verticalLineThree) / 2 + verticalLineThree - font.getStringWidth(list.get(1)) / 1000 * 12 / 2, heightTxt, list.get(1));
                    PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, (verticalLineFive - verticalLineFour) / 2 + verticalLineFour - font.getStringWidth(list.get(2)) / 1000 * 12 / 2, heightTxt, list.get(2));
                    break;
                case 1:
                    PdfUtil.contentStream(pdPageContentStream, font, 10F, null, 0, (verticalLineTwo - verticalLineOne) / 2 + verticalLineOne - font.getStringWidth(list.get(0)) / 1000 * 10 / 2, heightTxt, list.get(0));
                    PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, (verticalLineThree - verticalLineTwo) / 2 + verticalLineTwo - font.getStringWidth(list.get(1)) / 1000 * 10 / 2, heightTxt, list.get(1));
                    PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, verticalLineThree + 2, heightTxt, list.get(2));
                    PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, (verticalLineFive - verticalLineFour) / 2 + verticalLineFour - font.getStringWidth(list.get(3)) / 1000 * 10 / 2, heightTxt, list.get(3));
                    break;
                case 2:
                    break;
            }
        }
    }

    /**
     * 表格边框线
     *
     * @param pdPage              页面
     * @param pdPageContentStream 内容输出流
     * @param x                   x轴
     * @param y                   y轴
     * @param bottomY             底部Y轴
     */
    public static void borderLine(final PDPage pdPage, final PDPageContentStream pdPageContentStream, final float x, final float y, final float bottomY) throws IOException {

        //上边框线
        pdPageContentStream.moveTo(x, y - 5);
        pdPageContentStream.lineTo(pdPage.getMediaBox().getWidth() - x, y - 5);
        pdPageContentStream.stroke();
        //下边框线
        pdPageContentStream.moveTo(x, bottomY);
        pdPageContentStream.lineTo(pdPage.getMediaBox().getWidth() - x, bottomY);
        pdPageContentStream.stroke();
        //左边框线
        pdPageContentStream.moveTo(x, y - 5);
        pdPageContentStream.lineTo(x, bottomY);
        pdPageContentStream.stroke();
        //右边框线
        pdPageContentStream.moveTo(pdPage.getMediaBox().getWidth() - x, y - 5);
        pdPageContentStream.lineTo(pdPage.getMediaBox().getWidth() - x, bottomY);
        pdPageContentStream.stroke();
    }
}
