package com.mdsd.cloud.controller.map.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mdsd.cloud.constants.CommonConstants;
import com.mdsd.cloud.controller.map.dto.RecordTrackPo;
import com.mdsd.cloud.controller.map.dto.SmcListOrDetailPo;
import com.mdsd.cloud.controller.map.dto.SmcSaveOrUpdatePo;
import com.mdsd.cloud.controller.map.service.SpecialtyMapService;
import com.mdsd.cloud.entity.MedPhepGpsInfosEntity;
import com.mdsd.cloud.entity.MedPhepMissionsEntity;
import com.mdsd.cloud.mapper.MedPhepGpsInfosMapper;
import com.mdsd.cloud.mapper.MedPhepMissionsMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2021-11-15]
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class SpecialtyMapServiceImpl extends ServiceImpl<MedPhepGpsInfosMapper, MedPhepGpsInfosEntity> implements SpecialtyMapService {

    @NonNull
    RedisTemplate<String, String> redisTemplate;

    @NonNull
    MedPhepMissionsMapper medPhepMissionsMapper;//任务信息表

    /**
     * 专科地图 - 记录轨迹
     */
    @Override
    public Boolean recordTrack(final RecordTrackPo recordTrackPo) {

        //判断任务状态
        Assert.notNull(recordTrackPo, "入参对象为: NULL");
        final MedPhepMissionsEntity medPhepMissionsEntity = this.medPhepMissionsMapper.selectById(recordTrackPo.getMissionId());
        if (Objects.nonNull(medPhepMissionsEntity) && !medPhepMissionsEntity.getStatus().equals("06")) {
            //拼接RedisKey
            final String redisKey = String.format(CommonConstants.PREFIX, "map", recordTrackPo.getMissionId());
            //将轨迹数据存放到RedisList中
            this.redisTemplate.opsForList().rightPush(redisKey, JSON.toJSONString(recordTrackPo));
            //行动轨迹24小时后失效
            return this.redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 专科地图 - 保存or更新
     */
    @Override
    public Boolean smcSaveOrUpdate(final SmcSaveOrUpdatePo smcSaveOrUpdatePo) {

        Boolean result = null;
        if (Objects.nonNull(smcSaveOrUpdatePo) && StringUtils.isNotBlank(smcSaveOrUpdatePo.getMissionId())) {
            //拼接RedisKey
            final String redisKey = String.format(CommonConstants.PREFIX, "map", smcSaveOrUpdatePo.getMissionId());
            //判断是否有值
            final Boolean aBoolean = this.redisTemplate.hasKey(redisKey);//判断key是否存在
            final ListOperations<String, String> listOperations = this.redisTemplate.opsForList();
            if (Boolean.TRUE.equals(aBoolean) && listOperations.size(redisKey) > 0) {
                final List<MedPhepGpsInfosEntity> entList = Lists.newArrayList();
                final List<String> range = this.redisTemplate.opsForList().range(redisKey, 0, listOperations.size(redisKey));
                Optional.ofNullable(range).filter(list -> list.size() > 0).ifPresent(list -> {
                    //保存中间点
                    range.forEach(ent -> {
                        final RecordTrackPo recordTrackPo = JSON.parseObject(ent, RecordTrackPo.class);
                        Optional.ofNullable(recordTrackPo.getCurrentPoints()).ifPresent(dto -> {
                            final MedPhepGpsInfosEntity medPhepGpsInfosEntity = new MedPhepGpsInfosEntity();
                            medPhepGpsInfosEntity.setMissionId(recordTrackPo.getMissionId());
                            medPhepGpsInfosEntity.setRecordingTime(dto.getRecordingTime());
                            medPhepGpsInfosEntity.setLongitude(dto.getLng());
                            medPhepGpsInfosEntity.setLatitude(dto.getLat());
                            entList.add(medPhepGpsInfosEntity);
                        });
                    });
                    //保存终点
//                    final RecordTrackPo recordTrackPo = JSON.parseObject(list.get(0), RecordTrackPo.class);
//                    final MedPhepGpsInfosEntity medPhepGpsInfosEntity = new MedPhepGpsInfosEntity();
//                    medPhepGpsInfosEntity.setMissionId(recordTrackPo.getMissionId());
//                    medPhepGpsInfosEntity.setLongitude(recordTrackPo.getEndPoints().getLng());
//                    medPhepGpsInfosEntity.setLatitude(recordTrackPo.getEndPoints().getLat());
//                    entList.add(medPhepGpsInfosEntity);
                });
                //保存到数据库并删除Redis中数据
                if (CollectionUtils.isNotEmpty(entList) && this.saveOrUpdateBatch(entList)) {
                    result = this.redisTemplate.delete(redisKey);
                }
            }
        }
        return result;
    }

    /**
     * 专科地图 - 移除
     */
    @Override
    public Boolean smcRemove(final List<String> missionIds) {

        return this.remove(Wrappers.<MedPhepGpsInfosEntity>lambdaQuery().in(MedPhepGpsInfosEntity::getMissionId, missionIds));
    }

    /**
     * 专科地图 - 查询列表or详情
     */
    @Override
    public Object smcListOrDetail(final SmcListOrDetailPo smcListOrDetailPo) {

        if (Objects.nonNull(smcListOrDetailPo) && StringUtils.isNotBlank(smcListOrDetailPo.getId())) {
            //查详情
            return this.getById(smcListOrDetailPo.getId());
        } else {
            if (Objects.nonNull(smcListOrDetailPo) && StringUtils.isNotBlank(smcListOrDetailPo.getMissionId())) {
                //判断任务状态=06完成急救则查数据库数据否则查询Redis
                final MedPhepMissionsEntity medPhepMissionsEntity = this.medPhepMissionsMapper.selectById(smcListOrDetailPo.getMissionId());
                if (Objects.nonNull(medPhepMissionsEntity) && medPhepMissionsEntity.getStatus().equals("06")) {
                    return this.list(Wrappers.<MedPhepGpsInfosEntity>lambdaQuery().eq(MedPhepGpsInfosEntity::getMissionId, smcListOrDetailPo.getMissionId()));
                } else {
                    final List<RecordTrackPo> entList = Lists.newArrayList();
                    //拼接redisKey
                    final String redisKey = String.format(CommonConstants.PREFIX, "map", smcListOrDetailPo.getMissionId());
                    //判断是否有值
                    final Boolean aBoolean = this.redisTemplate.hasKey(redisKey);//判断key是否存在
                    final ListOperations<String, String> listOperations = this.redisTemplate.opsForList();
                    if (aBoolean && listOperations.size(redisKey) > 0) {
                        final List<String> range = listOperations.range(redisKey, 0, listOperations.size(redisKey));
                        range.forEach(ent -> {
                            entList.add(JSON.parseObject(ent, RecordTrackPo.class));
                        });
                    }
                    return entList;

                }
            } else {
                return this.list();
            }
        }
    }

    /**
     * REDIS - 根据MissionId删除
     */
    @Override
    public Boolean redisRemoveByMissionId(final String missionId) {

        if (StringUtils.isNotBlank(missionId)) {
            return this.redisTemplate.delete(String.format(CommonConstants.PREFIX, "map", missionId));
        }
        return false;
    }
}
