package com.mdsd.cloud.controller.diagnosis.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author wcq
 */
@Component
@RequiredArgsConstructor
public class DictManager {

    private final DataDictionaryService dataDictionaryService;

    public String matchingValue(final String key, final String value) {

        System.out.println(key + "=====" + value);
        switch (key) {
            case "dispatchVehicleSite":
                return this.matching(key, "PHFA_0007");
            case "gender":
                return this.matching(value, "PHFA_0001");
            case "consciousness":
                return this.matching(value, "PHFA_0016");
            case "skin":
                return this.matching(value, "PHFA_0017");
            case "pupilResponse":
                return this.matching(value, "PHFA_0018");
            case "lightReflection":
                return this.matching(value, "PHFA_0019");
            case "conditionGrade":
                return this.matching(value, "PHFA_0030");
            case "drugUsage":
                return this.matching(value, "PHFA_0055");
            case "diagnosis":
                return this.matching(value, "PHFA_0057");
            default:
                return value;
        }
    }

    /**
     * 字典匹配
     *
     * @param result 需要修改的字段
     * @param param  字典表中标识
     * @return 返回string
     */
    public String matching(String result, final String param) {
        //查询字典数据
        final GetDictByCodesPao getDictByCodesPao = new GetDictByCodesPao();
        final Map<String, List<MedDictionaryEntity>> dictionaryMap = this.dataDictionaryService.getDictByCode(getDictByCodesPao);
        final List<MedDictionaryEntity> drDtoList = dictionaryMap.get(param);
        if (!CollectionUtils.isEmpty(drDtoList) && StringUtils.isNotBlank(result)) {
            for (final MedDictionaryEntity drDto : drDtoList) {
                if (result.equals(drDto.getItemcode())) {
                    if (param.equals("PHFA_0057")) {
                        final StringBuilder sb = new StringBuilder();
                        result = sb.append(drDto.getItemname()).append("-").append(drDto.getDescribe()).toString();
                    } else {
                        result = drDto.getItemname();
                    }
                }
            }
        }
        return result;
    }
}
