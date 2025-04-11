package com.mdsd.cloud.controller.pc.rescuers.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.pc.rescuers.dto.PageListOrDetailsPo;
import com.mdsd.cloud.controller.pc.rescuers.service.RescuersService;
import com.mdsd.cloud.entity.MedPhepRescuersEntity;
import com.mdsd.cloud.mapper.MedPhepRescuersMapper;
import com.mdsd.cloud.utils.HandleString;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author WangYunwei [2022-12-06]
 */
@Service
public class RescuersServiceImpl implements RescuersService {

    MedPhepRescuersMapper medPhepRescuersMapper;

    public RescuersServiceImpl(final MedPhepRescuersMapper medPhepRescuersMapper) {

        this.medPhepRescuersMapper = medPhepRescuersMapper;
    }

    /**
     * 急救人员 - 保存or修改
     */
    @Override
    public String saveOrUpdate(final MedPhepRescuersEntity param) {

        if (Strings.isNotBlank(param.getRescuerId())) {
            this.medPhepRescuersMapper.updateById(param);
        } else {
            this.medPhepRescuersMapper.insert(param);
        }
        return param.getRescuerId();
    }

    /**
     * 急救人员 - 批量删除
     */
    @Override
    public Boolean batchDeletion(final List<String> ids) {

        Assert.notEmpty(ids, "入参列表为: NULL");
        return this.medPhepRescuersMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 急救人员 - 分页or详情
     */
    @Override
    public Object pageListOrDetails(final PageListOrDetailsPo param) {

        if (Strings.isNotBlank(param.getRescuerId())) {
            //ID不为空则查详情
            return this.medPhepRescuersMapper.selectById(param.getRescuerId());
        } else {
            //查询列表
            if (null == param.getSize()) {
                //页长为 NULL 查所有数据
                param.setSize(this.medPhepRescuersMapper.selectCount(Wrappers.lambdaQuery()));
            }
            final Page<MedPhepRescuersEntity> page = new Page<>(param.getCurrent(), param.getSize());
            final LambdaQueryWrapper<MedPhepRescuersEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
            if (Strings.isNotBlank(param.getRescuerName())) {
                lambdaQueryWrapper.like(MedPhepRescuersEntity::getRescuerName, HandleString.repString(param.getRescuerName()));
            }
            if (Strings.isNotBlank(param.getRescuerType())) {
                lambdaQueryWrapper.like(MedPhepRescuersEntity::getRescuerType, param.getRescuerType());
            }
            return this.medPhepRescuersMapper.selectPage(page, lambdaQueryWrapper);
        }
    }
}
