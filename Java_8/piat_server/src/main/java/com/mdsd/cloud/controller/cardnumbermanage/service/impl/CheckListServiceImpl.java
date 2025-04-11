package com.mdsd.cloud.controller.cardnumbermanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdsd.cloud.controller.cardnumbermanage.dto.*;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicCheckListEntity;
import com.mdsd.cloud.controller.cardnumbermanage.entity.MedClinicRegitionRelationEntity;
import com.mdsd.cloud.controller.cardnumbermanage.function.MedClinicCheckListFunction;
import com.mdsd.cloud.controller.cardnumbermanage.function.MedClinicRegitionRelationFunction;
import com.mdsd.cloud.controller.cardnumbermanage.mapper.CheckListMapper;
import com.mdsd.cloud.controller.cardnumbermanage.mapper.RegitionRelationMapper;
import com.mdsd.cloud.controller.cardnumbermanage.service.CheckListService;
import com.mdsd.cloud.controller.cardnumbermanage.struct.entity.EntityConverter;
import com.mdsd.cloud.controller.registration.entity.MedClinicCardInfoEntity;
import com.mdsd.cloud.controller.registration.entity.MedClinicCheckMasterEntity;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCardInfoMapper;
import com.mdsd.cloud.controller.registration.mapper.MedClinicCheckMasterMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author hxn
 * @version 1.0
 */
@Service
public class CheckListServiceImpl extends ServiceImpl<CheckListMapper, MedClinicCheckListEntity> implements CheckListService {

    EntityConverter entityConverter;

    CheckListMapper checkListMapper;

    RegitionRelationMapper regitionRelationMapper;

    MedClinicCardInfoMapper medClinicCardInfoMapper;

    MedClinicCheckMasterMapper medClinicCheckMasterMapper;

    MedClinicRegitionRelationFunction regitionRelationFunction;

    MedClinicCheckListFunction clinicCheckListFunction;

    public CheckListServiceImpl(final EntityConverter entityConverter,
                                final CheckListMapper checkListMapper,
                                final RegitionRelationMapper regitionRelationMapper,
                                final MedClinicCardInfoMapper medClinicCardInfoMapper,
                                final MedClinicCheckMasterMapper medClinicCheckMasterMapper,
                                final MedClinicRegitionRelationFunction regitionRelationFunction,
                                final MedClinicCheckListFunction clinicCheckListFunction) {

        this.entityConverter = entityConverter;
        this.checkListMapper = checkListMapper;
        this.regitionRelationMapper = regitionRelationMapper;
        this.medClinicCardInfoMapper = medClinicCardInfoMapper;
        this.medClinicCheckMasterMapper = medClinicCheckMasterMapper;
        this.regitionRelationFunction = regitionRelationFunction;
        this.clinicCheckListFunction = clinicCheckListFunction;
    }

    /**
     * 根据急诊编号信息查询关联挂号信息
     *
     * @param regId 就诊主表主键
     * @return 返回list
     */
    @Override
    public List<RegitionRelationResultDto> selectCheckListByRegId(final String regId) {

        List<RegitionRelationResultDto> regitionRelationResultDtoArrayList = new ArrayList<>();
        if (StringUtils.isNotBlank(regId)) {
            regitionRelationResultDtoArrayList = this.regitionRelationMapper.selectListByRegId(regId);
        }
        return regitionRelationResultDtoArrayList;
    }

    /**
     * 匹配卡号，换卡his数据查询
     *
     * @param hisSearchParamDto 接口参数
     * @return 返回分页结果
     */
    @Override
    public IPage<RegitionRelationResultDto> selectListHis(final HisSearchParamDto hisSearchParamDto) {

        final Page<RegitionRelationResultDto> cardInfoResultDtoPage = new Page<>(hisSearchParamDto.getCurrent(), hisSearchParamDto.getPageSize());
        return this.checkListMapper.selectHisPage(cardInfoResultDtoPage, hisSearchParamDto);
    }

    /**
     * 关联卡号,匹配卡号，换卡
     *
     * @param paramListDto 接口参数
     */
    @Override
    @Transactional
    public boolean bindingCard(final ParamListDto paramListDto, final String status) {

        if (paramListDto != null) {

            MedClinicCardInfoEntity cardInfoEntity;
            final MedClinicCheckMasterEntity checkMasterEntity;
            MedClinicRegitionRelationEntity regitionRelationEntity;
            MedClinicCheckListEntity checkListEntity;
            final CardInfoParamDto cardInfoParamDto = paramListDto.getCardInfoParamDto();
            final HisParamDto hisParamDto = paramListDto.getHisParamDto();

            //根据his提供的用户信息对应修改患者信息表中信息
            //根据急诊号获取就诊主表信息
            checkMasterEntity = this.medClinicCheckMasterMapper.selectById(cardInfoParamDto.getRegId());

            //关联卡号
            if (status.equals("1")) {
                List<MedClinicRegitionRelationEntity> regitionRelationEntities = new ArrayList<>();
                final List<MedClinicCheckListEntity> clinicCheckListEntities = new ArrayList<>();

                //无需操作 只对两个关联表进行数据新增
                final List<RegitionRelationResultDto> regitionRelationResultDtosLeft = paramListDto.getRegitionRelationResultDtosLeft();
                final List<RegitionRelationResultDto> regitionRelationResultDtosRight = paramListDto.getRegitionRelationResultDtosRight();

                //筛选穿梭框左边传回的数组是否有新增的id为空的对象
                final List<RegitionRelationResultDto> regitionRelationResultDtos = regitionRelationResultDtosLeft.stream()
                        .filter((RegitionRelationResultDto dto) -> dto.getId() == null).collect(Collectors.toList());
                if (regitionRelationResultDtos.size() > 0) {
                    for (final RegitionRelationResultDto dto : regitionRelationResultDtos) {
                        regitionRelationEntity = this.entityConverter.transfRegitionRelationEntityConverter(checkMasterEntity, dto);
                        regitionRelationEntity.setId(UUID.randomUUID().toString());
                        //创建卡号挂号关联信息表
                        checkListEntity = new MedClinicCheckListEntity();
                        checkListEntity.setRegisterNo(regitionRelationEntity.getId());
                        checkListEntity.setRegId(checkMasterEntity.getRegId());
                        checkListEntity.setPatientId(dto.getPatientId());
                        checkListEntity.setCreateTime(cardInfoParamDto.getCreateTime());
                        //添加list
                        clinicCheckListEntities.add(checkListEntity);
                        regitionRelationEntities.add(regitionRelationEntity);
                    }
                    this.regitionRelationFunction.saveBatch(regitionRelationEntities);
                    this.clinicCheckListFunction.saveBatch(clinicCheckListEntities);
                }
                //筛选穿梭框右边传回的数组是否有新增的id为不为空的对象
                final List<String> ids = regitionRelationResultDtosRight.stream()
                        .filter((RegitionRelationResultDto dto) -> dto.getId() != null).map(p -> p.getId()).collect(Collectors.toList());
                if (ids.size() > 0) {
                    regitionRelationEntities = this.regitionRelationMapper.selectList(Wrappers.<MedClinicRegitionRelationEntity>lambdaQuery()
                            .in(MedClinicRegitionRelationEntity::getId, ids));
                    final List<MedClinicRegitionRelationEntity> medClinicRegitionRelationEntities = regitionRelationEntities.stream()
                            .peek(medClinicRegitionRelationEntity -> medClinicRegitionRelationEntity.setRegId(""))
                            .collect(Collectors.toList());
                    this.regitionRelationFunction.updateBatchById(medClinicRegitionRelationEntities);
                }
                return true;
            } else {
                //查询挂号信息表中是否已经存在his挂号信息
                final QueryWrapper<MedClinicRegitionRelationEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq(StringUtils.isNotEmpty(hisParamDto.getHisNo()), "REGISTER_NO", hisParamDto.getHisNo())
                        .eq(StringUtils.isNotEmpty(checkMasterEntity.getRegId()), "REG_ID", checkMasterEntity.getRegId());
                final MedClinicRegitionRelationEntity medClinicRegitionRelationEntity = this.regitionRelationMapper.selectOne(queryWrapper);

                //挂号信息表中如果为空，刚未匹配过，进行匹配操作
                if (medClinicRegitionRelationEntity == null) {

                    //根据就诊主表信息查找相关患者信息
                    cardInfoEntity = this.medClinicCardInfoMapper.selectById(checkMasterEntity.getInfoId());
                    //匹配卡号
                    if (status.equals("2")) {
                        //根据就诊主表中的患者卡号获取患者卡号信息表数据
                        // 匹配卡号修改患者信息表数据
                        cardInfoEntity = this.entityConverter.cardInfoEntityConverter(cardInfoEntity, hisParamDto);
                        this.medClinicCardInfoMapper.updateById(cardInfoEntity);
                    }
                    //换卡
                    if (status.equals("3")) {
                        //根据卡号查询患者信息表中是否存在对应信息
                        if (cardInfoEntity.getCardId() != null) { //判断数据是否有就诊卡号
                            //如果有就诊卡号进行换上操作
                            if (!cardInfoEntity.getCardId().equals(hisParamDto.getVisitId())) {
                                cardInfoEntity = this.entityConverter.cardInfoEntityConverter(hisParamDto);
                                this.medClinicCardInfoMapper.insert(cardInfoEntity);
                            }
                        } else { //如果无就诊卡号在原数据上进行his卡号匹配
                            cardInfoEntity = this.entityConverter.cardInfoEntityConverter(cardInfoEntity, hisParamDto);
                            this.medClinicCardInfoMapper.updateById(cardInfoEntity);
                        }
                    }
                    //添加挂号信息表
                    regitionRelationEntity = this.entityConverter.regitionRelationEntityConverter(checkMasterEntity, hisParamDto);
                    this.regitionRelationMapper.insert(regitionRelationEntity);
                    //添加卡号挂号关联表信息
                    checkListEntity = this.entityConverter.checkListEntityConverter(checkMasterEntity, hisParamDto, regitionRelationEntity);
                    this.checkListMapper.insert(checkListEntity);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<RegitionRelationResultDto> selectHis() {

        return this.checkListMapper.selectHisPage();
    }

    /**
     * 查询卡号患者详细信息
     *
     * @param regId 就诊ID
     * @return 返回CardInfoResultDto
     */
    @Override
    public CardInfoResultDto selectCardInfoByRegId(final String regId) {

        return this.checkListMapper.selectCardInfoByRegId(regId);
    }

}
