package com.mdsd.cloud.controller.dictionary.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mdsd.cloud.controller.dictionary.dto.DdcAddBatchDto;
import com.mdsd.cloud.controller.dictionary.entity.MedDictionaryEntity;
import com.mdsd.cloud.controller.dictionary.function.MedDictionaryFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WangYunwei [2022-04-08]
 */
@Slf4j
@Component
public class DdcExcelListener extends AnalysisEventListener<DdcAddBatchDto> {

    MedDictionaryFunction medDictionaryFunction;

    public DdcExcelListener(final MedDictionaryFunction medDictionaryFunction) {

        this.medDictionaryFunction = medDictionaryFunction;
    }

    List<DdcAddBatchDto> list = Lists.newArrayList();

    int invokeIndex = 0;

    int saveDataIndex = 0;

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(final DdcAddBatchDto dto, final AnalysisContext context) {

        log.info("当前解析到 {} 条数据: {}", Integer.valueOf(++this.invokeIndex), JSON.toJSONString(dto));
        this.list.add(dto);
        // 达到5条数据了,需要去存储一次数据库,防止数据几万条数据在内存,容易OOM
        if (this.list.size() >= 30) {
            if (this.saveData()) {
                // 存储完成清理 list
                this.list.clear();
            }
        }
    }

    /**
     * 所有数据解析完成了都会来调用
     */
    @Override
    public void doAfterAllAnalysed(final AnalysisContext context) {

        // 这里也要保存数据,确保最后遗留的数据也存储到数据库
        if (CollectionUtils.isNotEmpty(this.list)) {
            this.saveData();
        }
        log.info("所有数据解析完成！");
    }

    /**
     * 此处可将list中数据插入数据库
     */
    boolean saveData() {

        final List<MedDictionaryEntity> entList = Lists.newArrayList();
        this.list.forEach(dto -> {
            final MedDictionaryEntity medDictionaryEntity = new MedDictionaryEntity();
            BeanUtils.copyProperties(dto, medDictionaryEntity);
            entList.add(medDictionaryEntity);
        });
        return this.medDictionaryFunction.saveBatch(entList);
    }
}
