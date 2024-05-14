package com.example.controller.pdf.service.impl;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.example.controller.pdf.dto.FirstAidMedicalRecordsPo;
import com.example.controller.pdf.dto.TriageDetailPo;
import com.example.controller.pdf.dto.TriageTickertapePo;
import com.example.controller.pdf.dto.WristbandsPo;
import com.example.controller.pdf.service.PdfService;
import com.example.controller.pdf.utils.PdfUtil;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WangYunwei [2022-01-26]
 */
@Service
public class PdfServiceImpl implements PdfService {

    Gson gson = new Gson();

    /**
     * PDF - 就诊凭条
     */
    @Override
    public void triageTickertape(TriageTickertapePo triageTickertapePo) throws IOException {

        // 设置响应头，控制浏览器下载该文件
        triageTickertapePo.getResponse().setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode("急诊明细.pdf", "UTF-8"));
        PDDocument document = new PDDocument();//创建文档
        PDPage pdPage = new PDPage(PDRectangle.A6);//创建一个空白页,并设置纸张大小
        document.addPage(pdPage);//将空白页添加到文档中

        PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
        PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流
        /****************** HEAD ******************/
        PdfUtil.contentStream(pdPageContentStream, font, 16F, RenderingMode.FILL_STROKE, 20F, 75F, 375F, "xx省人民医院");
        PdfUtil.contentStream(pdPageContentStream, font, 12F, null, null, 175F, 375F, "急  四级");

        /****************** CENTRE ******************/
        PdfUtil.contentStream(pdPageContentStream, null, null, RenderingMode.FILL, null, 25F, 350F, "姓名:张三是老张");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 127F, 350F, "性别:未知");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 193F, 350F, "年龄:120岁");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 330F, "-----------------------------------------");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 310F, "T: ______ ℃");
//        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, Float.parseFloat(triageTickertapePo.getMap().get("width")), Float.parseFloat(triageTickertapePo.getMap().get("height")), "38.0");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 47F, 310F, "38.0");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 143F, 310F, "P: ______ 次/分");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 165F, 310F, "43");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 290F, "R: ______ 次/分");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 47F, 290F, "95");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 143F, 290F, "BP: ____________ mmHg");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 170F, 290F, "110.0/110.0");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 270F, "SPO2: ______ %");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 65F, 270F, "95.0");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 250F, "-----------------------------------------");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 230F, "就诊科室:急诊抢救区");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 210F, "分诊时间:2022年1月28日 15点44分");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 25F, 190F, "-----------------------------------------");
        PdfUtil.contentStream(pdPageContentStream, font, 14F, RenderingMode.FILL_STROKE, null, 25F, 170F, "请妥善保管此单据，用于就诊，挂号 !");

        //生成条形码
        Code128Writer writer = new Code128Writer();
        String contents = "20221281600";
        BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.CODE_128, 50, 25);

        //        final String filePath = System.getProperty("os.name").equals("Linux") ? String.format("\\%s.png",contents) : "\\qrcode.png";//判断系统环境选择路径
        String filePath = String.format("\\%s.png", contents);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
        pdPageContentStream.drawImage(PDImageXObject.createFromFile(filePath, document), 90F, 130F);

        pdPageContentStream.close();//关闭流
        document.save(triageTickertapePo.getResponse().getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
        new File(filePath).delete();//最后删除条形码图片
    }

    /**
     * PDF - 腕带
     */
    @Override
    public void wristbands(WristbandsPo wristbandsPo) throws IOException, WriterException {

        // 设置响应头，控制浏览器下载该文件
        wristbandsPo.getResponse().setHeader("content-disposition", "attachment;filename="
                + URLEncoder.encode("腕带.pdf", "UTF-8"));
        PDDocument document = new PDDocument();//创建文档
        PDPage pdPage = new PDPage(new PDRectangle(354.0F, 177.0F));//创建一个空白页,并设置纸张大小
        document.addPage(pdPage);//将空白页添加到文档中
        PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
        PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流

        //生成二维码
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String contents = "20221281600";
        BitMatrix bitMatrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 100, 100);

//        final String filePath = System.getProperty("os.name").equals("Linux") ? String.format("\\%s.png",contents) : "\\qrcode.png";//判断系统环境选择路径
        final String filePath = String.format("\\%s.png", contents);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", Paths.get(filePath));
        PDImageXObject image = PDImageXObject.createFromFile(filePath, document);
        pdPageContentStream.drawImage(image, 0F, 40F);
        pdPageContentStream.drawImage(image, 255, 40);

        PdfUtil.contentStream(pdPageContentStream, font, 10F, RenderingMode.FILL, null, 150F, 134F, "xx人民医院");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 90F, 114F, "姓名:张三是老张");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 170F, 114F, "性别:男");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 224F, 114F, "年龄:120岁");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 90F, 87F, "病历号:202201290001");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 200F, 87F, "级别:三级");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 90F, 60F, "就诊科室:急诊内科");
        PdfUtil.contentStream(pdPageContentStream, font, 13F, RenderingMode.FILL_CLIP, null, 216F, 60F, "抢13");

        pdPageContentStream.close();//关闭流
        document.save(wristbandsPo.getResponse().getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
        new File(filePath).delete();//最后删除二维码图片
    }

    /**
     * PDF - 分诊明细
     */
    @Override
    public void triageDetail(TriageDetailPo triageDetailPo) throws IOException {
        // 设置响应头，控制浏览器下载该文件
        triageDetailPo.getResponse().setHeader("content-disposition", String.format("attachment;filename=%s", URLEncoder.encode("急诊明细.pdf", "UTF-8")));
        PDDocument document = new PDDocument();//创建文档
        PDPage pdPage = new PDPage(PDRectangle.A4);//创建一个空白页,并设置纸张大小
        document.addPage(pdPage);//将空白页添加到文档中
        PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
        PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流

        String title = "麦迪市第一人民医院";
        Map<String, Float> map = PdfUtil.centerCoordinate(pdPage, font, 20F, 30F, title);
        PdfUtil.contentStream(pdPageContentStream, font, 20F, RenderingMode.STROKE, 20F, map.get("x"), map.get("y"), title);
        title = "急诊病人诊疗流程记录单";
        map = PdfUtil.centerCoordinate(pdPage, font, 20F, 60F, title);
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, map.get("x"), map.get("y"), title);

        //第三行
        Float width = 30F;
        Float height = pdPage.getMediaBox().getHeight() - 110;
        PdfUtil.contentStream(pdPageContentStream, font, 12F, RenderingMode.FILL, null, width, height, "姓名:__________________");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 172F, height, "性别:_____");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 235F, height, "年龄:_____");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 299F, height, "病历号:_____________");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 422F, height, "急诊编号:_______________");
        height = height + 1;
        PdfUtil.contentStream(pdPageContentStream, font, 11F, null, null, 61F, height, "金良林");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 204F, height, "男");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 267F, height, "33");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 341F, height, "14466147");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 478F, height, "202009080235");
        //第四行
        height = pdPage.getMediaBox().getHeight() - 135;
        PdfUtil.contentStream(pdPageContentStream, font, 12F, null, null, width, height, "日期:__________________");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 172F, height, "床号:_____");
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, 235F, height, "诊断:__________________________________________________");
        height = height + 1;
        PdfUtil.contentStream(pdPageContentStream, font, 11F, null, null, 61F, height, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        PdfUtil.contentStream(pdPageContentStream, font, 11F, null, null, 204F, height, "抢-7");
        PdfUtil.contentStream(pdPageContentStream, font, 11F, null, null, 267F, height, "头痛");

        //表格
        height = pdPage.getMediaBox().getHeight() - 145;
        List<String> list = new ArrayList<String>() {{
            this.add("时间");
            this.add("诊疗内容");
            this.add("操作员工");
        }};
        PdfUtil.frameLine(pdPageContentStream, font, height, list, 0);

        list = new ArrayList<String>() {{
            this.add("分诊");
            this.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
            this.add("分诊登记");
            this.add("刘敏");
        }};
        PdfUtil.frameLine(pdPageContentStream, font, height - 25, list, 1);

        pdPageContentStream.close();//关闭流
        document.save(triageDetailPo.getResponse().getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
    }

    /**
     * PDF - 急救病历
     */
    @Override
    public void firstAidMedicalRecords(FirstAidMedicalRecordsPo firstAidMedicalRecordsPo) throws IOException {

        // 设置响应头，控制浏览器下载该文件
        firstAidMedicalRecordsPo.getResponse().setHeader("content-disposition", String.format("attachment;filename=%s", URLEncoder.encode("急救病历.pdf", "UTF-8")));
        PDDocument document = new PDDocument();//创建文档
        PDPage pdPage = new PDPage(PDRectangle.A4);//创建一个空白页,并设置纸张大小
        document.addPage(pdPage);//将空白页添加到文档中
        PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
        PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流
        //第一行
        String title = "院前医疗急救病历";
        Map<String, Float> map = PdfUtil.centerCoordinate(pdPage, font, 20F, 30F, title);
        PdfUtil.contentStream(pdPageContentStream, font, 20F, RenderingMode.STROKE, 20F, map.get("x"), map.get("y"), title);
        //第二行 - 首
        float xAxis = 30;//totalWidth:595.27563
        float yAxis = pdPage.getMediaBox().getHeight() - 90;//totalHeight:841.8898
        PdfUtil.contentStream(pdPageContentStream, font, 12F, RenderingMode.FILL, null, xAxis, yAxis, "分站: 科技城医院");
        //第二行 - 尾
        String str2 = "病历编号: 202206080001";
        float str2XAxis = pdPage.getMediaBox().getWidth() - xAxis - font.getStringWidth(str2) / 1000 * 12;
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, str2XAxis, yAxis, str2);
        //第二行 - 中
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, (str2XAxis - 30) / 2, yAxis, "病种类别: 理化中毒");
        //表格边框线
        PdfUtil.borderLine(pdPage, pdPageContentStream, xAxis, yAxis, 20F);
        //表格内部线 - 初始值
        float formWidth = pdPage.getMediaBox().getWidth() - xAxis * 2;//表单宽度
        float lineX = (pdPage.getMediaBox().getWidth() - (xAxis * 2)) / 9;//每列x轴
        float oneLineX = xAxis + lineX;//第一列x轴
        float lineY = (yAxis - 5 - 20) / 19;//每行y轴
        float oneLineY = yAxis - 5 - lineY;//第一行y轴
        float rowSpacing = yAxis - 5;//距离上一行间隔
        //表格内部线 - 竖线计算
        List<Float> centerLineXList = Lists.newArrayList(oneLineX);//格中线2,x轴记录
        List<Float> sList = Lists.newArrayList(30F);
        for (int i = 0; i < 9; i++) {
            //上格
            switch (i) {
                case 0:
                    //贯穿线
                    pdPageContentStream.moveTo(oneLineX, rowSpacing);
                    pdPageContentStream.lineTo(oneLineX, 20);
                    pdPageContentStream.stroke();
                    break;
                case 1:
                    //格中线1
                    pdPageContentStream.moveTo(oneLineX, 20 + lineY * 11);
                    pdPageContentStream.lineTo(oneLineX, 20 + lineY * 7);
                    pdPageContentStream.stroke();
                    centerLineXList.add(oneLineX);
                    //格中线2
                    float centerLineX = (pdPage.getMediaBox().getWidth() - xAxis - oneLineX) / 5;
                    float oneCenterLineX = oneLineX + centerLineX;
                    for (int j = 0; j < 5; j++) {
                        pdPageContentStream.moveTo(oneCenterLineX, 20 + lineY * 10);
                        pdPageContentStream.lineTo(oneCenterLineX, 20 + lineY * 9);
                        pdPageContentStream.stroke();
                        centerLineXList.add(oneCenterLineX);
                        oneCenterLineX = oneCenterLineX + centerLineX;
                    }
                    //底格竖线
                    pdPageContentStream.moveTo(oneLineX, 20 + lineY);
                    pdPageContentStream.lineTo(oneLineX, 20);
                    pdPageContentStream.stroke();
                    break;
                case 2:
                case 3:
                    pdPageContentStream.moveTo(oneLineX, rowSpacing);
                    pdPageContentStream.lineTo(oneLineX, rowSpacing - lineY * 3);
                    pdPageContentStream.stroke();
                case 4:
                    //底格竖线
                    pdPageContentStream.moveTo(oneLineX, 20 + lineY);
                    pdPageContentStream.lineTo(oneLineX, 20);
                    pdPageContentStream.stroke();
                    break;
                case 5:
                case 6:
                    pdPageContentStream.moveTo(oneLineX, rowSpacing);
                    pdPageContentStream.lineTo(oneLineX, rowSpacing - lineY * 4);
                    pdPageContentStream.stroke();
                    break;
            }
            sList.add(oneLineX);//竖线的x轴数据
            oneLineX = oneLineX + lineX;
        }
        //表格内部线 - 横线计算
        List<Float> hList = Lists.newArrayList(yAxis - 5);
        for (int i = 0; i < 20; i++) {
            switch (i) {
                case 8:
                case 9:
                    pdPageContentStream.moveTo(xAxis + formWidth / 9, oneLineY);
                    pdPageContentStream.lineTo(pdPage.getMediaBox().getWidth() - xAxis, oneLineY);
                    pdPageContentStream.stroke();
                    break;
                case 10:
                    break;
                default:
                    pdPageContentStream.moveTo(xAxis, oneLineY);
                    pdPageContentStream.lineTo(pdPage.getMediaBox().getWidth() - xAxis, oneLineY);
                    pdPageContentStream.stroke();
                    break;
            }
            hList.add(oneLineY);//横线的y轴数据
            oneLineY = oneLineY - lineY;
        }
        //定义填充数据的二维数组,若空则填充空
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String[][] str = new String[][]{
                {"接令时间", format, "skip", "出车时间", format, "skip", "到达现场", format, "skip"},
                {"离开现场", format, "skip", "到院时间", format, "skip", "完成时间", format, "skip"},
                {"姓 名", "李明行", "skip", "性 别", "男", "skip", "年 龄", "21岁", "skip"},
                {"接诊地址", "通安碧桂园门口 农业银行 附近", "skip", "skip", "skip", "skip", "联系电话", "15993001622", "skip"},
                {"主 诉", "酒后上腹痛半小时", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"现病史", "半小时前无明显诱因出现，上腹都疼痛，无呕吐，无呕血，无腹泻。言语清楚", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"既往史", "体健", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"过敏史", "无", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"体格检查", "生命体征", "T: 未检查 P: 79次/分 R: 16次/分 BP: 94/68mmHg SPO2:98% 神志: 清晰", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "皮肤粘膜", "正常", "瞳孔", "正常", "对光反射", "正常", "skip", "skip"},
                {"skip", "辅助检查", "血糖: 6.2mmol/L 心电图: 未采集 TI评估: 中到重度伤 GCS评估: 昏迷;胸痛评估: 持续性胸痛/胸闷 Killip评估: Ⅱ级 轻度至中度心力衰竭;FAST评估: 未见异常;POCT检查: 未检查;其它: 无", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"初步诊断", "酒精性胃炎", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情判断", "急症", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"急救处置", "ECG、血压监测、脉氧监护、仰卧、测血糖", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"药物治疗", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"出诊结果", "送往医院", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情转归", "无变化", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"出诊医生", "邵明永", "出诊护士", "张晶晶", "送达医院", "苏州科技城医院", "skip", "skip", "skip"}
        };
        //测试数据填充
        for (int i = 0; i < str.length; i++) {//行循环
            for (int j = 0; j < str[i].length; j++) {//列循环
                //判断后一条数据是否略过,并计算表格宽度
                int skip = j;
                float cellWidth = sList.get(j + 1) - sList.get(j);
                for (int k = j + 1; k < str[i].length; k++) {//判断后一个填充数据是否跳过
                    if (!str[i][k].equals("skip")) {
                        break;//跳出k循环
                    } else {
                        skip = k;
                        cellWidth = sList.get(k + 1) - sList.get(j);
                    }
                }
                //确定x轴位置,并计算数据宽度
                float strWidth = font.getStringWidth(str[i][j]) / 1000 * 12;
                float strX = sList.get(j) + 2;//数据的x轴位置
                //判断是否需要换行
                if (strWidth > cellWidth) {
                    if (i == 10) {
                        //重新计算数据填充的Y轴
                        String[] split = str[i][j].split(";");
                        float strY = hList.get(i);
                        float strY10 = (hList.get(i) - hList.get(i + 2)) / split.length - 1;
                        for (int l = 0; l < split.length; l++) {
                            strY = strY - strY10;
                            PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, strY, split[l]);
                        }

                    } else {
                        //数据大于表格宽度换行填充
                        int strLength = str[i][j].length() * 2 / 3;
                        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, (hList.get(i) - hList.get(i + 1)) / 2 + hList.get(i + 1), str[i][j].substring(0, strLength));
                        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, hList.get(i + 1) + (hList.get(i) - hList.get(i + 1)) / 8, str[i][j].substring(strLength));
                    }
                } else {
                    //直接填充
                    float strY = (hList.get(i) - hList.get(i + 1)) / 2 + hList.get(i + 1) - font.getHeight(12) * 4;//数据的y轴位置
                    if ((i == 8 || i == 9 || i == 10 || i == 11) && j == 0) {
                        switch (i) {
                            case 8:
                                //重新计算y轴
                                strY = (hList.get(i) - hList.get(i + 4)) / 2 + hList.get(i + 4);
                                PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, strY, str[i][j].equals("skip") ? "" : str[i][j]);
                                break;
                        }
                    } else if (i == 9 && j != 0) {
                        strX = centerLineXList.get(j - 1) + 2;
                        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, strY, str[i][j].equals("skip") ? "" : str[i][j]);
                    } else if (i == 10 && j == 1) {
                        //重新计算y轴
                        strY = (hList.get(i) - hList.get(i + 2)) / 2 + hList.get(i + 2);
                        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, strY, str[i][j].equals("skip") ? "" : str[i][j]);
                    } else {
                        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, strY, str[i][j].equals("skip") ? "" : str[i][j]);
                    }
                }
                j = skip;
            }
        }
        pdPageContentStream.close();//关闭流
        document.save(firstAidMedicalRecordsPo.getResponse().getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
    }

    /**
     * PDF - 交接单
     */
    @Override
    public void pdfDeliveryFrom(String patientId, HttpServletResponse response) throws IOException {

        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", String.format("attachment;filename=%s", URLEncoder.encode("交接记录.pdf", "UTF-8")));
        final PDDocument document = new PDDocument();//创建文档
        final PDPage pdPage = new PDPage(PDRectangle.A4);//创建一个空白页,并设置纸张大小
        document.addPage(pdPage);//将空白页添加到文档中
        final PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
        final PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流
        //第一行
        final String title = "院前-院内交接记录";
        final Map<String, Float> map = PdfUtil.centerCoordinate(pdPage, font, 20F, 30F, title);
        PdfUtil.contentStream(pdPageContentStream, font, 20F, RenderingMode.STROKE, 20F, map.get("x"), map.get("y"), title);
        //第二行 - 首
        final float xAxis = 30;//totalWidth:595.27563
        final float yAxis = pdPage.getMediaBox().getHeight() - 90;//totalHeight:841.8898
        PdfUtil.contentStream(pdPageContentStream, font, 12F, RenderingMode.FILL, null, xAxis, yAxis, "出车分站: 科技城医院");
        //第二行 - 尾
        final String str2 = "病历编号: 202206080001";
        final float str2XAxis = pdPage.getMediaBox().getWidth() - xAxis - font.getStringWidth(str2) / 1000 * 12;
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, str2XAxis, yAxis, str2);
        //第二行 - 中
        PdfUtil.contentStream(pdPageContentStream, null, null, null, null, (str2XAxis - 30) / 2, yAxis, "急救车: 苏E571BT");
        //表格边框线
        final Float bottomY = 280F;
        PdfUtil.borderLine(pdPage, pdPageContentStream, xAxis, yAxis, bottomY);
        //表格内部线 - 初始值
        final float lineY = (yAxis - 5 - bottomY) / 15;//每行Y轴
        float oneLineY = yAxis - 5 - lineY;//第一行Y轴
        //表格内部线 - 竖线计算
        final float lineX = (pdPage.getMediaBox().getWidth() - (xAxis * 2)) / 9;//每列x轴
        float oneLineX = xAxis + lineX;//第一列x轴
        final float rowSpacing = yAxis - 5;//距离上一行间隔
        final List<Float> centerLineXList = Lists.newArrayList(oneLineX);//格中线2,x轴记录
        final List<Float> sList = Lists.newArrayList(xAxis);//竖线X轴集合
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    //贯穿线
                    pdPageContentStream.moveTo(oneLineX, rowSpacing);
                    pdPageContentStream.lineTo(oneLineX, bottomY);
                    pdPageContentStream.stroke();
                    break;
                case 1:
                    pdPageContentStream.moveTo(oneLineX, rowSpacing - lineY * 3);
                    pdPageContentStream.lineTo(oneLineX, rowSpacing - lineY * 7);
                    pdPageContentStream.stroke();
                    //从新计算5行3列至6列的竖线
                    centerLineXList.add(oneLineX);
                    final float centerLineX = (pdPage.getMediaBox().getWidth() - xAxis - oneLineX) / 5;//每列的X轴
                    float oneCenterLineX = oneLineX + centerLineX;//第一列的X轴
                    for (int j = 0; j < 4; j++) {
                        pdPageContentStream.moveTo(oneCenterLineX, rowSpacing - lineY * 4);
                        pdPageContentStream.lineTo(oneCenterLineX, rowSpacing - lineY * 5);
                        pdPageContentStream.stroke();
                        centerLineXList.add(oneCenterLineX);
                        oneCenterLineX = oneCenterLineX + centerLineX;
                    }
                    centerLineXList.add(pdPage.getMediaBox().getWidth() - xAxis);//右边线的X轴
                    break;
                case 5:
                    pdPageContentStream.moveTo(oneLineX, rowSpacing);
                    pdPageContentStream.lineTo(oneLineX, rowSpacing - lineY);
                    pdPageContentStream.stroke();
                case 4:
                    pdPageContentStream.moveTo(oneLineX, bottomY + lineY * 3);
                    pdPageContentStream.lineTo(oneLineX, bottomY);
                    pdPageContentStream.stroke();
                    break;
                case 7:
                    break;
                default:
                    pdPageContentStream.moveTo(oneLineX, rowSpacing);
                    pdPageContentStream.lineTo(oneLineX, rowSpacing - lineY);
                    pdPageContentStream.stroke();
                    break;
            }
            sList.add(oneLineX);
            oneLineX = oneLineX + lineX;
        }
        sList.add(pdPage.getMediaBox().getWidth() - xAxis);//右边线的X轴
        //表格内部线 - 横线计算
        final List<Float> hList = Lists.newArrayList(yAxis - 5);//横线线Y轴集合
        for (int i = 0; i < 14; i++) {
            switch (i) {
                case 3:
                case 4:
                    pdPageContentStream.moveTo(xAxis + lineX, oneLineY);
                    pdPageContentStream.lineTo(pdPage.getMediaBox().getWidth() - xAxis, oneLineY);
                    pdPageContentStream.stroke();
                    break;
                case 5:
                    break;
                default:
                    pdPageContentStream.moveTo(xAxis, oneLineY);
                    pdPageContentStream.lineTo(pdPage.getMediaBox().getWidth() - xAxis, oneLineY);
                    pdPageContentStream.stroke();
                    break;
            }
            hList.add(oneLineY);
            oneLineY = oneLineY - lineY;
        }
        hList.add(bottomY);//下边线的Y轴
        System.out.println("sList:" +  gson.toJson(sList) );
        System.out.println("hList:" + gson.toJson(hList) );
        System.out.println("centerLineXList:" + gson.toJson(centerLineXList));
        //数据填充
        final String[][] str = new String[][]{
                {"姓 名", "李明行", "skip", "性 别", "男", "skip", "年 龄", "21岁", "skip"},
                {"接诊地址", "通安碧桂园门口 农业银行 附近", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"患者主诉", "半小时前无明显诱因出现，上腹都疼痛，无呕吐，无呕血，无腹泻，言语清楚", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"体格检查", "生命体征", "T: 未检查 P: 79次/分 R: 16次/分 BP: 94/68mmHg SPO2:98% 神志: 清晰", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "皮肤粘膜", "正常", "瞳孔", "正常", "对光反射", "正常", "skip", "skip"},
                {"skip", "辅助检查", "血糖: 6.2mmol/L 心电图: 未采集 TI评估: 中到重度伤 GCS评估: 昏迷;胸痛评估: 持续性胸痛/胸闷 Killip评估: Ⅱ级 轻度至中度心力衰竭;FAST评估: 未见异常;POCT检查: 未检查;其它: 无", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"初步诊断", "酒精性胃炎", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情判断", "急症", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"急救处置", "ECG、血压监测、脉氧监护、仰卧、测血糖", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"药物治疗", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"其它", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情转归", "无变化", "skip", "skip", "skip", "交接时间", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), "skip", "skip"},
                {"交接医院", "苏州科技城医院", "skip", "skip", "skip", "交接科室", "急诊科", "skip", "skip"},
                {"出诊医护", "邵明永", "skip", "skip", "skip", "急诊医护", "张晶晶", "skip", "skip"},
        };
        for (int i = 0; i < str.length; i++) {//行循环
            for (int j = 0; j < str[i].length; j++) {//列循环
                //判断后一条数据是否略过,并计算表格宽度
                int skip = j;
                float cellWidth = sList.get(j + 1) - sList.get(j);
                for (int k = j + 1; k < str[i].length; k++) {//判断后一个填充数据是否跳过
                    if (!str[i][k].equals("skip")) {
                        break;//跳出k循环
                    } else {
                        skip = k;
                        cellWidth = sList.get(k + 1) - sList.get(j);
                    }
                }
                //确定x轴位置,并计算数据宽度
                final float strWidth = font.getStringWidth(str[i][j]) / 1000 * 12;
                float strX = sList.get(j) + 2;//数据的x轴位置
                //判断是否需要换行
                if (strWidth > cellWidth) {
                    if (i == 5) {
                        //重新计算数据填充的Y轴
                        final String[] split = str[i][j].split(";");
                        float strY = hList.get(i);
                        final float strY10 = (hList.get(i) - hList.get(i + 2)) / split.length - 1;
                        for (int l = 0; l < split.length; l++) {
                            strY = strY - strY10;
                            PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, strY, split[l]);
                        }
                    }
                } else {
                    float strY = (hList.get(i) - hList.get(i + 1)) / 2 + hList.get(i + 1) - font.getHeight(12) * 4;//数据的y轴位置
                    if (i == 3 && j == 0) {
                        //重新计算Y轴
                        strY = (hList.get(i) - hList.get(i + 4)) / 2 + hList.get(i + 4);
                    } else if (i == 4 && j != 0) {
                        //重新计算X轴
                        strX = centerLineXList.get(j - 1) + 2;
                    } else if (i == 5 && j == 1) {
                        //重新计算y轴
                        strY = (hList.get(i) - hList.get(i + 2)) / 2 + hList.get(i + 2);
                    }
                    PdfUtil.contentStream(pdPageContentStream, null, null, null, null, strX, strY, str[i][j].equals("skip") ? "" : str[i][j]);
                }
                j = skip;
            }
        }

        pdPageContentStream.close();//关闭流
        document.save(response.getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
    }

    /**
     * PDF - 知情同意书
     */
    @Override
    public void pdfConsentBook(String patientId, HttpServletResponse response) throws IOException {

        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", String.format("attachment;filename=%s", URLEncoder.encode("知情同意书.pdf", "UTF-8")));
        final PDDocument document = new PDDocument();//创建文档
        final PDPage pdPage = new PDPage(new PDRectangle(210 * (1 / (10 * 2.54f) * 72), 148 * (1 / (10 * 2.54f) * 72)));//创建一个空白页,并设置纸张大小
        document.addPage(pdPage);//将空白页添加到文档中
        final PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
        final PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流

        //第一行
        final String title = "患者知情同意书";
        final Map<String, Float> map = PdfUtil.centerCoordinate(pdPage, font, 20F, 10F, title);
        PdfUtil.contentStream(pdPageContentStream, font, 20F, RenderingMode.STROKE, 20F, map.get("x"), map.get("y"), title);

        //第二行

        pdPageContentStream.close();//关闭流
        document.save(response.getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
    }
}
