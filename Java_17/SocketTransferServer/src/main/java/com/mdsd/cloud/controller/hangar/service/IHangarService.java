package com.mdsd.cloud.controller.hangar.service;

import com.mdsd.cloud.controller.hangar.dto.OperateInp;
import com.mdsd.cloud.response.ResponseDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author WangYunwei [2024-09-03]
 */
public interface IHangarService {

    /**
     * 操作机库
     */
    void operate(OperateInp param);
}
