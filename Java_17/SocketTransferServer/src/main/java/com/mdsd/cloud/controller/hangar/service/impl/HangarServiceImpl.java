package com.mdsd.cloud.controller.hangar.service.impl;

import com.mdsd.cloud.controller.hangar.dto.OperateInp;
import com.mdsd.cloud.controller.hangar.service.IHangarService;
import com.mdsd.cloud.enums.CommandEnum;
import com.mdsd.cloud.feign.HangarFeign;
import com.mdsd.cloud.response.BusinessException;
import org.springframework.stereotype.Service;

/**
 * @author WangYunwei [2024-09-03]
 */
@Service
public class HangarServiceImpl implements IHangarService {

    final HangarFeign feign;

    public HangarServiceImpl(HangarFeign feign) {
        this.feign = feign;
    }

    /**
     * 操作机库
     */
    @Override
    public void operate(OperateInp param) {

        CommandEnum commandEnum = CommandEnum.getCommandEnum(param.getCommand(), param.getAction());
        switch (commandEnum) {
            case 机库_舱门_开启 -> feign.doorOpen(commandEnum.getArg());
            case 机库_舱门_关闭 -> feign.doorClose(commandEnum.getArg());
            case 机库_推杆_开启 -> feign.barOpen(commandEnum.getArg());
            case 机库_推杆_关闭 -> feign.barClose(commandEnum.getArg());
            case 机库_空调_开启 -> feign.airOpen(commandEnum.getArg());
            case 机库_空调_关闭 -> feign.airClose(commandEnum.getArg());
            case 机库_无人机_开启 -> feign.uavColl(commandEnum.getArg());
            case 机库_无人机_关闭 -> feign.uavColl(commandEnum.getArg());
            default -> throw new BusinessException("未知指令动作!");
        }
    }
}
