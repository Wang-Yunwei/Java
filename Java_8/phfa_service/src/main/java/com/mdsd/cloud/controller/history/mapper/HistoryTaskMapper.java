package com.mdsd.cloud.controller.history.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mdsd.cloud.controller.history.dto.HtcListPagePo;
import com.mdsd.cloud.controller.history.dto.HtcListPo;
import com.mdsd.cloud.controller.history.dto.HtcListRo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangYunwei [2022-06-28]
 */
@Mapper
public interface HistoryTaskMapper {

    /**
     * 历史任务 - 列表
     *
     * @param htcListPo 入参
     * @return List<HtcListRo>
     */
    List<HtcListRo> htcList(HtcListPo htcListPo);

    /**
     * 历史任务 - 分页列表
     * @param objectPage 分布page
     * @param htcListPagePo 入参
     * @return IPage<HtcListRo>
     */
    IPage<HtcListRo> htcPageList(Page<Object> objectPage, @Param("dto") HtcListPagePo htcListPagePo);
}
