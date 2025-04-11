package com.mdsd.cloud.controller.common.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author WangYunwei [2022-07-25]
 */
@Mapper
public interface CommonMapper {

    /**
     * 公共 - 推送数据到DAS
     *
     * @param missionId
     * @return
     */
    String pushDataToDas(String missionId);
}
