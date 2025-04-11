package com.mdsd.cloud.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wcq
 * @version 1.0
 * @date 2021/9/16 14:06
 */
@Getter
@AllArgsConstructor
public enum ArriveTypeEnum {

    BX("01", "步行", "5", ""),
    CF("02", "搀扶", "5", ""),
    LY("03", "轮椅", "5" , ""),
    PC("04", "平车", "5", ""),
    BR("05", "抱入", "5", ""),
    BY120("06", "本院120", "1", ""),
    WY120("07", "外院120", "1", ""),
    YYL("08", "110车", "2", ""),
    LRSDYY("09", "路人送到医院", "2", ""),
    WYZY("10", "外院转院", "3", ""),

    ;

    @EnumValue
    private String value;
    private String text;
    private String stroke;
    private String other;

    public static ArriveTypeEnum getByValue(String value) {
        for (ArriveTypeEnum item : ArriveTypeEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }

        return null;
    }
}
