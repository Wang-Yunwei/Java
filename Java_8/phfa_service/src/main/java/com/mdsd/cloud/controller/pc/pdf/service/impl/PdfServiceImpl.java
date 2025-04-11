package com.mdsd.cloud.controller.pc.pdf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.mdsd.cloud.controller.attachment.entity.MedPhepAttachmentEntity;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.pc.pdf.dto.PdfMedicalRecordDto;
import com.mdsd.cloud.controller.pc.pdf.mapper.PdfMapper;
import com.mdsd.cloud.controller.pc.pdf.service.PdfService;
import com.mdsd.cloud.controller.pc.pdf.utils.PdfUtil;
import com.mdsd.cloud.entity.MedPhepEstimateEntity;
import com.mdsd.cloud.entity.MedPhepGeneralInspectionsEntity;
import com.mdsd.cloud.entity.MedPhepPoctEntity;
import com.mdsd.cloud.entity.MedPhepStationEntity;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author WangYunwei [2022-06-20]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PdfServiceImpl implements PdfService {

    @NonNull
    PdfMapper pdfMapper;

    @NonNull
    DataDictionaryService dataDictionaryService;//数据字典

    /**
     * PDF - 院前医疗急救病历
     */
    @Override
    public void pdfMedicalRecord(final String patientId, final HttpServletResponse response) throws IOException {

        Assert.notNull(patientId, "患者ID为NULL");
        //获取数据
        final PdfMedicalRecordDto data = this.getData(patientId);
        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", String.format("attachment;filename=%s", URLEncoder.encode("急救病历.pdf", "UTF-8")));
        final PDDocument document = new PDDocument();//创建文档
        final PDPage pdPage = new PDPage(PDRectangle.A4);//创建一个空白页,并设置纸张大小
        document.addPage(pdPage);//将空白页添加到文档中
        final PDType0Font font = PDType0Font.load(document, new ClassPathResource("font/simsun.ttf").getInputStream());
        final PDPageContentStream pdPageContentStream = new PDPageContentStream(document, pdPage);//创建内容流
        //第一行
        final String title = "院前医疗急救病历";
        final Map<String, Float> map = PdfUtil.centerCoordinate(pdPage, font, 20F, 30F, title);
        PdfUtil.contentStream(pdPageContentStream, font, 20F, RenderingMode.STROKE, 20F, map.get("x"), map.get("y"), title);
        //第二行 - 首
        final float xAxis = 30;//totalWidth:595.27563
        final float yAxis = pdPage.getMediaBox().getHeight() - 90;//totalHeight:841.8898
        final String dispatchVehicleSite = this.handlerString(data.getDispatchVehicleSite(), null, "");
        PdfUtil.contentStream(pdPageContentStream, font, 12F, RenderingMode.FILL, 0, xAxis, yAxis, String.format("分站: %s", dispatchVehicleSite));
        //第二行 - 尾
        final String recordId = String.format("病历编号: %s", this.handlerString(data.getRecordId(), null, ""));
        final float str2XAxis = pdPage.getMediaBox().getWidth() - xAxis - font.getStringWidth(recordId) / 1000 * 12;
        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, str2XAxis, yAxis, recordId);
        //第二行 - 中
        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, (str2XAxis - 30) / 2, yAxis, String.format("病种类别: %s", data.getDiseaseCategory()));
        //表格边框线
        PdfUtil.borderLine(pdPage, pdPageContentStream, xAxis, yAxis, 20F);
        //表格内部线 - 初始值
        final float formWidth = pdPage.getMediaBox().getWidth() - xAxis * 2;//表单宽度
        final float lineX = (pdPage.getMediaBox().getWidth() - (xAxis * 2)) / 9;//每列x轴
        float oneLineX = xAxis + lineX;//第一列x轴
        final float lineY = (yAxis - 5 - 20) / 19;//每行y轴
        float oneLineY = yAxis - 5 - lineY;//第一行y轴
        final float rowSpacing = yAxis - 5;//距离上一行间隔
        //表格内部线 - 竖线计算
        final List<Float> centerLineXList = Lists.newArrayList(oneLineX);//格中线2,x轴记录
        final List<Float> sList = Lists.newArrayList(30F);
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
                    final float centerLineX = (pdPage.getMediaBox().getWidth() - xAxis - oneLineX) / 5;
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
        final List<Float> hList = Lists.newArrayList(yAxis - 5);
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
        final String[][] str = new String[][]{
                {"接令时间", this.handlerString(data.getAcceptMissionTime(), null, ""), "skip", "出车时间", this.handlerString(data.getDispatchVehicleTime(), null, ""), "skip", "到达现场", this.handlerString(data.getArrivedSceneTime(), null, ""), "skip"},
                {"离开现场", this.handlerString(data.getLeftSceneTime(), null, ""), "skip", "到院时间", this.handlerString(data.getArrivedHospitalTime(), null, ""), "skip", "完成时间", this.handlerString(data.getCompleteTime(), null, ""), "skip"},
                {"姓 名", this.handlerString(data.getName(), null, ""), "skip", "性 别", this.handlerString(data.getGender(), null, ""), "skip", "年 龄", this.handlerString(data.getAge(), null, ""), "skip"},
                {"接诊地址", this.handlerString(data.getAddress(), null, ""), "skip", "skip", "skip", "skip", "联系电话", this.handlerString(data.getContactNumber(), null, ""), "skip"},
                {"主 诉", this.handlerString(data.getChiefComplaint(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"现病史", this.handlerString(data.getPresentIllness(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"既往史", this.handlerString(data.getPastHistory(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"过敏史", this.handlerString(data.getAllergicHistory(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"体格检查", "生命体征", data.getVitalSigns(), "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "皮肤粘膜", this.handlerString(data.getSkin(), null, "未检查"), "瞳孔", this.handlerString(data.getPupilResponse(), null, "未检查"), "对光反射", this.handlerString(data.getLightReflection(), null, "未检查"), "skip", "skip"},
                {"skip", "辅助检查", data.getAssistantExamination(), "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"初步诊断", this.handlerString(data.getDiagnosis(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情判断", this.handlerString(data.getConditionGrade(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"急救处置", this.handlerString(data.getTreatment(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"药物治疗", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"出诊结果", this.handlerString(data.getTreatmentResult(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情转归", this.handlerString(data.getRescueChangeResult(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"出诊医生", this.handlerString(data.getDoctorName(), null, ""), "出诊护士", this.handlerString(data.getNurseName(), null, ""), "送达医院", this.handlerString(data.getToHospitalName(), null, ""), "skip", "skip", "skip"}
        };
        //数据填充
        for (int i = 0; i < str.length; i++) {//行循环
            for (int j = 0; j < str[i].length; j++) {//列循环
                //判断后一条数据是否略过,并计算表格宽度
                int skip = 0;
                float cellWidth = sList.get(j + 1) - sList.get(j);
                for (int k = j + 1; k < str[i].length; k++) {//判断后一个填充数据是否跳过
                    if (!"skip".equals(str[i][k])) {
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
                    if (i == 10) {
                        //重新计算数据填充的Y轴
                        final String[] split = str[i][j].split(";");
                        float strY = hList.get(i);
                        final float strY10 = (hList.get(i) - hList.get(i + 2)) / split.length - 1;
                        for (final String s : split) {
                            strY = strY - strY10;
                            PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, strY, s);
                        }
                    } else {
                        //数据大于表格宽度换行填充
                        if (i == 9) {
                            strX = centerLineXList.get(j - 1) + 2;
                        }
                        final int strLength = str[i][j].length() * 2 / 3;
                        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, (hList.get(i) - hList.get(i + 1)) / 2 + hList.get(i + 1), str[i][j].substring(0, strLength));
                        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, hList.get(i + 1) + (hList.get(i) - hList.get(i + 1)) / 8, str[i][j].substring(strLength));
                    }
                } else {
                    //直接填充
                    float strY = (hList.get(i) - hList.get(i + 1)) / 2 + hList.get(i + 1) - font.getHeight(12) * 4;//数据的y轴位置
                    if ((i == 8 || i == 9 || i == 10 || i == 11) && j == 0) {
                        if (i == 8) {
                            //重新计算y轴
                            strY = (hList.get(i) - hList.get(i + 4)) / 2 + hList.get(i + 4);
                            PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, strY, "skip".equals(str[i][j]) ? "" : str[i][j]);
                        }
                    } else if (i == 9) {
                        strX = centerLineXList.get(j - 1) + 2;
                        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, strY, "skip".equals(str[i][j]) ? "" : str[i][j]);
                    } else if (i == 10 && j == 1) {
                        //重新计算y轴
                        strY = (hList.get(i) - hList.get(i + 2)) / 2 + hList.get(i + 2);
                        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, strY, "skip".equals(str[i][j]) ? "" : str[i][j]);
                    } else {
                        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, strY, "skip".equals(str[i][j]) ? "" : str[i][j]);
                    }
                }
                j = 0 != skip ? skip : j;
            }
        }
        pdPageContentStream.close();//关闭流
        document.save(response.getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
    }

    /**
     * 获取数据
     *
     * @param patientId 患者ID
     * @return PdfMedicalRecordDto
     */
    PdfMedicalRecordDto getData(final String patientId) {

        //查询患者病历数据
        final PdfMedicalRecordDto result = this.pdfMapper.pdfMedicalRecord(patientId);
        Assert.notNull(result, "患者病历数据为NULL");
        //查询站点
        if (null != result.getDispatchVehicleSite()) {
            final MedPhepStationEntity medPhepStationEntity = new MedPhepStationEntity().selectById(result.getDispatchVehicleSite());
            result.setDispatchVehicleSite(Objects.nonNull(medPhepStationEntity) ? medPhepStationEntity.getName() : "");
        }
        //查询数据字典
        final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0002, DictionaryEnum.PHFA_0011, DictionaryEnum.PHFA_0016, DictionaryEnum.PHFA_0017, DictionaryEnum.PHFA_0018, DictionaryEnum.PHFA_0019, DictionaryEnum.PHFA_0030, DictionaryEnum.PHFA_0032, DictionaryEnum.PHFA_0033, DictionaryEnum.PHFA_0058);
        result.setAge(null != result.getAge() ? String.format("%s%s", result.getAge(), this.getDicName(dictByCode, DictionaryEnum.PHFA_0002.getValue(), result.getAgeExtend())) : "");
        result.setDiseaseCategory(this.getDicName(dictByCode, DictionaryEnum.PHFA_0011.getValue(), result.getDiseaseCategory()));
        result.setConditionGrade(this.getDicName(dictByCode, DictionaryEnum.PHFA_0030.getValue(), result.getConditionGrade()));
        result.setRescueChangeResult(this.getDicName(dictByCode, DictionaryEnum.PHFA_0032.getValue(), result.getRescueChangeResult()));
        result.setTreatmentResult(this.getDicName(dictByCode, DictionaryEnum.PHFA_0033.getValue(), result.getTreatmentResult()));
        result.setDept(this.getDicName(dictByCode, DictionaryEnum.PHFA_0058.getValue(), result.getDept()));
        //查询生命体征数据
        final QueryWrapper<MedPhepGeneralInspectionsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PATIENT_ID", patientId).eq("rownum", 1).orderByDesc("CREATE_TIME");
        final MedPhepGeneralInspectionsEntity giEnt = new MedPhepGeneralInspectionsEntity().selectOne(queryWrapper);
        final String vitalSigns = "T:%s P:%s R:%s BP:%s SPO2:%s 神志:%s";
        if (Objects.nonNull(giEnt)) {
            result.setVitalSigns(String.format(vitalSigns, this.handlerString(String.valueOf(giEnt.getT()), "℃", "未检查"), this.handlerString(String.valueOf(giEnt.getPulse()), "次/分", "未检查"), this.handlerString(String.valueOf(giEnt.getRr()), "次/分", "未检查"), null != giEnt.getSbp() && null != giEnt.getDbp() ? String.format("%s/%smmHg", giEnt.getSbp(), giEnt.getDbp()) : "未检查", this.handlerString(String.valueOf(giEnt.getSpo2()), "%", "未检查"), this.handlerString(String.valueOf(this.getDicName(dictByCode, DictionaryEnum.PHFA_0016.getValue(), giEnt.getConsciousness())), null, "未检查")));
            //皮肤
            result.setSkin(this.getDicName(dictByCode, DictionaryEnum.PHFA_0017.getValue(), giEnt.getSkin()));
            //瞳孔
            result.setPupilResponse(this.getDicName(dictByCode, DictionaryEnum.PHFA_0018.getValue(), giEnt.getPupilResponse()));
            //对光反射
            result.setLightReflection(this.getDicName(dictByCode, DictionaryEnum.PHFA_0019.getValue(), giEnt.getLightReflection()));
        } else {
            result.setVitalSigns(String.format(vitalSigns, "未检查", "未检查", "未检查", "未检查", "未检查", "未检查"));
        }
        //查询是否存在心电图
        final long isEcg = new MedPhepAttachmentEntity().selectCount(Wrappers.<MedPhepAttachmentEntity>lambdaQuery().eq(MedPhepAttachmentEntity::getBusinessId, patientId).eq(MedPhepAttachmentEntity::getFileType, 0));
        result.setIsEcg(0 != isEcg ? "已采集" : "未采集");
        //查询POCT表
        final long isPoct = new MedPhepPoctEntity().selectCount(Wrappers.<MedPhepPoctEntity>lambdaQuery().eq(MedPhepPoctEntity::getPatientId, patientId));
        result.setIsPoct(0 != isPoct ? "已检查" : "未检查");
        //查询评分数据
        final List<MedPhepEstimateEntity> medPhepEstimateEntities = new MedPhepEstimateEntity().selectList(Wrappers.<MedPhepEstimateEntity>lambdaQuery().eq(MedPhepEstimateEntity::getPatientId, patientId));
        final String assistantExamination = "血糖: %s 心电图: %s TI评估: %s GCS评估: %s;胸痛评估: %s Killip评估: %s;FAST评估: %s;POCT检查: %s;其它: %s";
        if (!CollectionUtils.isEmpty(medPhepEstimateEntities)) {
            medPhepEstimateEntities.stream().filter(ent -> "ti".equals(ent.getName())).findFirst().ifPresent(ent -> {
                result.setTi(ent.getDescription());
            });
            medPhepEstimateEntities.stream().filter(ent -> "gcs".equals(ent.getName())).findFirst().ifPresent(ent -> {
                result.setGcs(ent.getDescription());
            });
            medPhepEstimateEntities.stream().filter(ent -> "chestPain".equals(ent.getName())).findFirst().ifPresent(ent -> {
                result.setChestPain(ent.getDescription());
            });
            medPhepEstimateEntities.stream().filter(ent -> "killip".equals(ent.getName())).findFirst().ifPresent(ent -> {
                result.setKillip(ent.getDescription());
            });
            medPhepEstimateEntities.stream().filter(ent -> "fast".equals(ent.getName())).findFirst().ifPresent(ent -> {
                result.setFast(ent.getDescription());
            });
        }
        result.setAssistantExamination(String.format(assistantExamination, null != result.getBloodglucose() ? String.format("%s%s", result.getBloodglucose(), "mmol/L") : "未检查", result.getIsEcg(), null != result.getTi() ? result.getTi() : "未检查", null != result.getGcs() ? result.getGcs() : "未检查", null != result.getChestPain() ? result.getChestPain() : "未检查", null != result.getKillip() ? result.getKillip() : "未检查", null != result.getFast() ? result.getFast() : "未检查", result.getIsPoct(), null != result.getOtherCheck() ? result.getOtherCheck() : "无"));
        return result;
    }

    /**
     * 处理NULL数据
     *
     * @param str       数据
     * @param unit      单位
     * @param resultStr 如果str为NULL则返回resultStr
     * @return String
     */
    String handlerString(final String str, final String unit, final String resultStr) {

        String result = StringUtils.isNotBlank(str) ? str : resultStr;
        if (StringUtils.isNotBlank(unit) && StringUtils.isNotBlank(str)) {
            result = StringUtils.isNotBlank(str) ? String.format("%s%s", str, unit) : resultStr;
        }
        if ("null".equals(str)) {
            result = resultStr;
        }
        return result;
    }

    /**
     * 获取数据枚举值
     *
     * @param map      数据集
     * @param dicCode  字典编码
     * @param dicValue 字典值
     * @return String
     */
    String getDicName(final Map<String, List<MedDictionaryEntity>> map, final String dicCode, final String dicValue) {

        String result = "";
        if (!CollectionUtils.isEmpty(map) && StringUtils.isNotBlank(dicValue)) {
            result = map.get(dicCode).stream().filter(ent -> dicValue.equals(ent.getItemcode())).findFirst().get().getItemname();
        }
        return result;
    }

    /**
     * PDF - 交接单
     */
    @Override
    public void pdfDeliveryFrom(final String patientId, final HttpServletResponse response) throws IOException {

        Assert.notNull(patientId, "患者ID为NULL");
        //获取数据
        final PdfMedicalRecordDto data = this.getData(patientId);
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
        final String dispatchVehicleSite = this.handlerString(data.getDispatchVehicleSite(), null, "");
        PdfUtil.contentStream(pdPageContentStream, font, 12F, RenderingMode.FILL, 0, xAxis, yAxis, String.format("出车分站: %s", dispatchVehicleSite));
        //第二行 - 尾
        final String recordId = String.format("病历编号: %s", this.handlerString(data.getRecordId(), null, ""));
        final float str2XAxis = pdPage.getMediaBox().getWidth() - xAxis - font.getStringWidth(recordId) / 1000 * 12;
        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, str2XAxis, yAxis, recordId);
        //第二行 - 中
        final String licensePlate = this.handlerString(data.getLicensePlate(), null, "");
        PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, (str2XAxis - 30) / 2, yAxis, String.format("急救车: %s", licensePlate));
        //表格边框线
        final float bottomY = 280F;
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
        //数据填充
        final String[][] str = new String[][]{
                {"姓 名", this.handlerString(data.getName(), null, ""), "skip", "性 别", this.handlerString(data.getGender(), null, ""), "skip", "年 龄", this.handlerString(data.getAge(), null, ""), "skip"},
                {"接诊地址", this.handlerString(data.getAddress(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"患者主诉", this.handlerString(data.getChiefComplaint(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"体格检查", "生命体征", data.getVitalSigns(), "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "皮肤粘膜", this.handlerString(data.getSkin(), null, "未检查"), "瞳孔", this.handlerString(data.getPupilResponse(), null, "未检查"), "对光反射", this.handlerString(data.getLightReflection(), null, "未检查"), "skip", "skip"},
                {"skip", "辅助检查", data.getAssistantExamination(), "skip", "skip", "skip", "skip", "skip", "skip"},
                {"skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"初步诊断", this.handlerString(data.getDiagnosis(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情判断", this.handlerString(data.getConditionGrade(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"急救处置", this.handlerString(data.getTreatment(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"药物治疗", "skip", "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"其它", this.handlerString(data.getOtherInfo(), null, ""), "skip", "skip", "skip", "skip", "skip", "skip", "skip"},
                {"病情转归", this.handlerString(data.getRescueChangeResult(), null, ""), "skip", "skip", "skip", "交接时间", this.handlerString(data.getShiftTime(), null, ""), "skip", "skip"},
                {"交接医院", this.handlerString(data.getToHospitalName(), null, ""), "skip", "skip", "skip", "交接科室", this.handlerString(data.getDept(), null, ""), "skip", "skip"},
                {"出诊医护", this.handlerString(null != data.getEmergencyDoctor() ? data.getEmergencyDoctor() : data.getEmergencyNurse(), null, ""), "skip", "skip", "skip", "急诊医护", this.handlerString(null != data.getDoctor() ? data.getDoctor() : data.getNurse(), null, ""), "skip", "skip"},
        };
        for (int i = 0; i < str.length; i++) {//行循环
            for (int j = 0; j < str[i].length; j++) {//列循环
                //判断后一条数据是否略过,并计算表格宽度
                int skip = j;
                float cellWidth = sList.get(j + 1) - sList.get(j);
                for (int k = j + 1; k < str[i].length; k++) {//判断后一个填充数据是否跳过
                    if (!"skip".equals(str[i][k])) {
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
                            PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, strY, split[l]);
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
                    PdfUtil.contentStream(pdPageContentStream, null, 0, null, 0, strX, strY, "skip".equals(str[i][j]) ? "" : str[i][j]);
                }
                j = skip;
            }
        }
        //生成条形码
//        Thumbnails.of(new File("\\40bd46f33ba3929173cffb39db994dc1.png"))
//                .size(200, 30)
//                .toFile(new File("\\40bd46f33ba3929173cffb39db994dc1_2.png"));
//        final BufferedImage originalImage = ImageIO.read(new File("\\40bd46f33ba3929173cffb39db994dc1.png"));
//
//        Thumbnails.of(originalImage)
//                .scale(0.25) // 缩放因子，原使图像的25%
//                .toFile(new File("\\40bd46f33ba3929173cffb39db994dc1_2.png"));
//        pdPageContentStream.drawImage(PDImageXObject.createFromFile("\\40bd46f33ba3929173cffb39db994dc1_2.png", document), 90F, 130F);

        pdPageContentStream.close();//关闭流
        document.save(response.getOutputStream());//将文档保存到输出流中
        document.close();//关闭文档
    }
}
