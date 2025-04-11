package com.mdsd.cloud.controller.diagnosis.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author WangYunwei [2022-04-28]
 */
@Getter
@RequiredArgsConstructor
public enum PrintDeliveryRecordEnum {
    PDR_000("000", "院前-院内交接记录\n", 0),
    PDR_010("010", "编号：%s", 0),
    PDR_020("020", "姓名：%s    性别：%s    年龄：%s%s", 0),
    PDR_030("030", "主诉：%s", 0),
    PDR_040("040", "初步诊断：%s", 0),
    PDR_050("050", "联动情况：%s", 0),
    PDR_060("060", "导管留置：%s", 0),
    PDR_070("070", "急救措施：%s", 0),
    PDR_080("080", "已用药物：%s", 0),
    PDR_090("090", "生命体征：", 0),
    PDR_100("100", "T：%s    P：%s    R：%s", 0),
    PDR_110("110", "SBP：%s    DBP：%s    SPO2：%s", 0),
    PDR_120("120", "血糖：%s", 0),
    PDR_130("130", "瞳孔左：%s    瞳孔右：%s", 0),
    PDR_140("140", "对光反射左：%s    对光反射右：%s", 0),
    PDR_150("150", "神智：%s", 0),
    PDR_160("160", "皮肤粘膜：%s", 0),
    PDR_170("170", "专科检查：", 0),
    PDR_171("171", "TI评估：%s    GCS评估：%s", 0),
    PDR_172("172", "胸痛评估：%s    Killip评估：%s", 0),
    PDR_173("173", "FAST评估：%s", 0),
    PDR_180("180", "其它：%s", 0),
    PDR_200("200", "急救医生：%s", 0),
    PDR_210("210", "接诊医护人员签名：%s", 0),
    PDR_220("220", "missionId二维码Base64", 1),
    PDR_230("230", "出车分站：%s", 0),
    PDR_240("240", "交接时间：%s\n\n\n", 0);

    private final String key;

    private final String value;

    private final Integer type;

    /**
     * 根据key获取Enum
     */
    public static PrintDeliveryRecordEnum getEnumByKey(final String key) {

        for (final PrintDeliveryRecordEnum enu : PrintDeliveryRecordEnum.values()) {
            if (enu.getKey().equals(key)) {
                return enu;
            }
        }
        return null;
    }

    /**
     * 根据key获取value
     */
    public static String getValueByKey(final String key) {

        for (final PrintDeliveryRecordEnum enu : PrintDeliveryRecordEnum.values()) {
            if (enu.getKey().equals(key)) {
                return enu.getValue();
            }
        }
        return null;
    }

    /**
     * 根据key获取type
     */
    public static Integer getTypeByKey(final Integer key) {

        for (final PrintDeliveryRecordEnum enu : PrintDeliveryRecordEnum.values()) {
            if (enu.getKey().equals(key)) {
                return enu.getType();
            }
        }
        return null;
    }
}
