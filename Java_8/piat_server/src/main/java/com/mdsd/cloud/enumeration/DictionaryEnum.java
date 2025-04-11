package com.mdsd.cloud.enumeration;

import lombok.Getter;

/**
 * @author WangYunwei [2022-07-25]
 */
@Getter
public enum DictionaryEnum {
    /**
     * 性别
     */
    PIAT_0001("性别", "PIAT_0001"),
    /**
     * 证件类型
     */
    PIAT_0002("证件类型", "PIAT_0002"),
    /**
     * 民族
     */
    PIAT_0003("民族", "PIAT_0003"),
    /**
     * 来院方式
     */
    PIAT_0004("来院方式", "PIAT_0004"),
    /**
     * 绿色通道
     */
    PIAT_0005("绿色通道", "PIAT_0005"),
    /**
     * 职业
     */
    PIAT_0006("职业", "PIAT_0006"),
    /**
     * 意识
     */
    PIAT_0007("意识", "PIAT_0007"),
    /**
     * 去向
     */
    PIAT_0008("去向", "PIAT_0008"),
    /**
     * 分诊级别
     */
    PIAT_0009("分诊级别", "PIAT_0009"),
    /**
     * 右下角菜单按钮
     */
    PIAT_0010("右下角菜单按钮", "PIAT_0010"),
    /**
     * 修改分级分诊原因
     */
    PIAT_0011("修改分级分诊原因", "PIAT_0011"),
    /**
     * 事件性质
     */
    PIAT_0012("事件性质", "PIAT_0012"),
    /**
     * 状态
     */
    PIAT_0013("状态", "PIAT_0013"),
    /**
     * 就诊科室
     */
    PIAT_0014("就诊科室", "PIAT_0014"),
    /**
     * 就诊医生
     */
    PIAT_0015("就诊医生", "PIAT_0015"),
    /**
     * 其他
     */
    PIAT_0016("其他", "PIAT_0016"),
    /**
     * 分诊错误信息
     */
    PIAT_0017("分诊错误信息", "PIAT_0017");

    private final String key;

    private final String value;

    DictionaryEnum(final String key, final String value) {

        this.key = key;
        this.value = value;
    }
}
