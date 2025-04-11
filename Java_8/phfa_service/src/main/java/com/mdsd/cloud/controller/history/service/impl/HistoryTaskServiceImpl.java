package com.mdsd.cloud.controller.history.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.mdsd.cloud.controller.dictionary.dto.GetDictByCodesPao;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.service.DataDictionaryService;
import com.mdsd.cloud.controller.history.dto.HtcListPagePo;
import com.mdsd.cloud.controller.history.dto.HtcListPo;
import com.mdsd.cloud.controller.history.dto.HtcListRo;
import com.mdsd.cloud.controller.history.mapper.HistoryTaskMapper;
import com.mdsd.cloud.controller.history.service.HistoryTaskService;
import com.mdsd.cloud.enumeration.DictionaryEnum;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author WangYunwei [2022-06-28]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HistoryTaskServiceImpl implements HistoryTaskService {

    @NonNull
    HistoryTaskMapper historyTaskMapper;

    @NonNull
    DataDictionaryService dataDictionaryService;

    /**
     * 历史任务 - 列表
     */
    @Override
    public List<HtcListRo> htcList(final HtcListPo htcListPo) {

        final List<HtcListRo> result = this.historyTaskMapper.htcList(htcListPo);
        Optional.ofNullable(result).filter(list -> list.size() > 0).ifPresent(list -> {
            //查询数据字典
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(new GetDictByCodesPao().setCode(Lists.newArrayList(DictionaryEnum.PHFA_0002.getValue())));
            //处理年龄类别
            list.forEach(dto -> {
                if (null != dto.getAge() && null != dto.getAgeExtend()) {
                    dto.setAge(String.format("%s%s", dto.getAge(), dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> dto.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname()));
                } else if (null != dto.getAgeExtend()) {
                    dto.setAge(dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> dto.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname());
                }
            });
        });
        return result.stream().sorted(Comparator.nullsLast(Comparator.comparing(HtcListRo::getStatus).reversed())).sorted(Comparator.nullsLast(Comparator.comparing(HtcListRo::getCallTime).reversed())).collect(Collectors.toList());
    }

    @Override
    public IPage<HtcListRo> htcPageList(HtcListPagePo htcListPagePo) {

        final IPage<HtcListRo> htcPageList = this.historyTaskMapper.htcPageList(new Page<>(htcListPagePo.getPageNo(), htcListPagePo.getPageSize()), htcListPagePo);
        if (CollectionUtils.isNotEmpty(htcPageList.getRecords())) {
            //查询数据字典
            final Map<String, List<MedDictionaryEntity>> dictByCode = this.dataDictionaryService.getDictByCode(DictionaryEnum.PHFA_0002);
            Optional.ofNullable(dictByCode).filter(map -> map.size() > 0).ifPresent(map -> {
                //处理年龄类别
                for (final HtcListRo item : htcPageList.getRecords()) {
                    if (null != item.getAge() && null != item.getAgeExtend()) {
                        item.setAge(String.format("%s%s", item.getAge(), dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> item.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname()));
                    } else if (null != item.getAgeExtend()) {
                        item.setAge(dictByCode.get(DictionaryEnum.PHFA_0002.getValue()).stream().filter(ent -> item.getAgeExtend().equals(ent.getItemcode())).findFirst().get().getItemname());
                    }
                }
            });
        }
        return htcPageList;
    }
}
