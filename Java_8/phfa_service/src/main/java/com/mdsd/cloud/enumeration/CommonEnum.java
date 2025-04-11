package com.mdsd.cloud.enumeration;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * @author WangYunwei [2022-01-19]
 */
@Getter
public enum CommonEnum {
    /**
     * 五大专科
     */
    PHEP("phep", "院前急救"),
    CHEST_PAIN("CPC", "胸痛"),
    APOPLEXY("STROKE", "卒中"),
    TRAUMA("TRAUMA", "创伤"),
    MATERNAL("MATERNAL", "孕产妇"),
    NEONATE("NEONATE", "新生儿"),
    NOTICE_EVENT("18", "预警事件标识");

    private final String key;

    private final String value;

    CommonEnum(final String key, final String value) {

        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(final String key) {

        if (!StringUtils.isEmpty(key)) {
            for (final CommonEnum item : CommonEnum.values()) {
                if (item.getKey().equalsIgnoreCase(key)) {
                    return item.getValue();
                }
            }
        }
        return null;
    }
}
