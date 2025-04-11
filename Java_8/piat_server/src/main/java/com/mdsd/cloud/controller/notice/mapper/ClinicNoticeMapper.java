package com.mdsd.cloud.controller.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdsd.cloud.controller.notice.dto.ClinicNoticeRequest;
import com.mdsd.cloud.controller.notice.dto.ClinicNoticeVo;
import com.mdsd.cloud.controller.notice.entity.ClinicNoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description :通知
 * @author: wgy
 * @create: 2021-09-24 09:06
 **/
@Mapper
public interface ClinicNoticeMapper extends BaseMapper<ClinicNoticeEntity> {

    List<ClinicNoticeVo> queryList(@Param("param") ClinicNoticeRequest clinicNoticeRequest);
}
