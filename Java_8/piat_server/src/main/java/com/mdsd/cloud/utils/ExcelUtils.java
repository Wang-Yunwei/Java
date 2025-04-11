package com.mdsd.cloud.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author hxn
 * @version 1.0
 * @date 2021/9/16 10:22
 */
public class ExcelUtils {

    private final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);
    /**
     * 浏览器下载excel
     * @param fileName
     * @param wb
     * @param response
     */

    public static void buildExcelDocument(String fileName, Workbook wb,HttpServletResponse response){
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "utf-8"));
            response.flushBuffer();
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HSSFRow getRow(HSSFSheet sheetModel, int index){
        HSSFRow row = null;
        if(sheetModel.getRow(index) == null) {
            row = sheetModel.createRow(index);
        }else{
            row = sheetModel.getRow(index);
        }
        return row;
    }

    public static void setCellValue(HSSFRow row, int index, String value) {
        if(row.getCell(index) == null) {
            row.createCell(index).setCellValue(value);
        }else{
            row.getCell(index).setCellValue(value);
        }
    }
    public static void setCellValue(HSSFRow row, int index, double value) {
        if(row.getCell(index) == null) {
            row.createCell(index).setCellValue(value);
        }else{
            row.getCell(index).setCellValue(value);
        }
    }
    public static void setCellValue(HSSFRow row, int index, int value) {
        if(row.getCell(index) == null) {
            row.createCell(index).setCellValue(value);
        }else{
            row.getCell(index).setCellValue(value);
        }
    }

    public static void setCellValue(HSSFRow row, int index, Date value) {
        if(row.getCell(index) == null) {
            row.createCell(index).setCellValue(value);
        }else{
            row.getCell(index).setCellValue(value);
        }
    }

    /**
     * @param filepath excel文件路径
     * @param pngPath 图片路径
     * @param cellPoint 自定义的对象，指定要插入图片的坐标(x, y)
     */
    public static void whiteImg(String filepath, String pngPath, @NotNull CellPoint cellPoint) {
        ByteArrayOutputStream byteArrayOut;
        BufferedImage bufferImg;
        FileInputStream fs;
        HSSFWorkbook wb;
        HSSFSheet sheetModel;
        FileOutputStream fout;
        try {
            fs = new FileInputStream(filepath);
            //读取excel模板
            wb = new HSSFWorkbook(fs);
            //读取了模板内所有sheet内容
            sheetModel = wb.getSheetAt(0);
            byteArrayOut= new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File(pngPath));
            ImageIO.write(bufferImg, pngPath.substring(pngPath.lastIndexOf(".") + 1), byteArrayOut);//起点坐标

            int x1 =cellPoint.getRow1();
            int y1 =cellPoint.getCol1();//终点坐标

            int x2 =cellPoint.getRow2();
            int y2 =cellPoint.getCol2();
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheetModel.createDrawingPatriarch();
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) y1, x1, (short) y2, x2);
            anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            fout = new FileOutputStream(filepath);
            wb.write(fout);
            fout.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
