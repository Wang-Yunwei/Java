package com.mdsd.cloud.controller.history.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mdsd.cloud.controller.history.dto.HtcListPagePo;
import com.mdsd.cloud.controller.history.dto.HtcListPo;
import com.mdsd.cloud.controller.history.dto.HtcListRo;

import java.util.List;

/**
 * @author WangYunwei [2022-06-28]
 */
public interface HistoryTaskService {

    /**
     * 历史任务 - 列表
     *
     * @param htcListPo 入参
     * @return List<HtcListRo>
     */
    List<HtcListRo> htcList(HtcListPo htcListPo);

    /**
     * 历史任务 - 分页列表
     *
     * @param htcListPagePo 入参
     * @return IPage<HtcListRo>
     */
    IPage<HtcListRo> htcPageList(HtcListPagePo htcListPagePo);
}
