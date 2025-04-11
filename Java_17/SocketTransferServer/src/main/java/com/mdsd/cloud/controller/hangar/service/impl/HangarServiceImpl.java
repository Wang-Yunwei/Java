package com.mdsd.cloud.controller.hangar.service.impl;

import com.mdsd.cloud.controller.hangar.dto.OperateInp;
import com.mdsd.cloud.controller.hangar.service.IHangarService;
import com.mdsd.cloud.enums.HangarEnum;
import com.mdsd.cloud.feign.HangarFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYunwei [2024-09-03]
 */
@Slf4j
@Service
public class HangarServiceImpl implements IHangarService {

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private String batteryPower;

    final HangarFeign feign;

    public HangarServiceImpl(HangarFeign feign) {
        this.feign = feign;
    }

    /**
     * 操作机库
     */
    @Override
    public Map<String, String> operate(OperateInp param) {

        HangarEnum hangarEnum = HangarEnum.getCommandEnum(param.getCommand(), param.getAction());
        switch (hangarEnum) {
            case 机库_舱门_开:
                feign.doorOpen(hangarEnum.getArg());
                break;
            case 机库_舱门_关:
                feign.doorClose(hangarEnum.getArg());
                break;
            case 机库_推杆_开:
                feign.barOpen(hangarEnum.getArg());
                break;
            case 机库_推杆_关:
                feign.barClose(hangarEnum.getArg());
                break;
            case 机库_空调_开:
                feign.airOpen(hangarEnum.getArg());
                break;
            case 机库_空调_关:
                feign.airClose(hangarEnum.getArg());
                break;
            case 机库_充电操作_开机:
            case 机库_充电操作_关机:
            case 机库_充电操作_待机:
            case 机库_充电操作_充电:
            case 机库_充电操作_检查:
                feign.chargingOperation(hangarEnum.getArg());
                break;
            default:
                return feign.getState();
        }
        return null;
    }

    @Override
    public void chargingUav() {
        scheduledExecutorService.schedule(() -> {
            Map<String, String> operate = operate(new OperateInp().setCommand(HangarEnum.机库_状态.getCommand()).setAction(HangarEnum.机库_状态.getAction()));
            if (!CollectionUtils.isEmpty(operate) && "close".equals(operate.get("charge_state"))) {
                String[] split = batteryPower.split("_"); // 当前 batteryPower 是云盒关机前的电量信息
                // 当任意一边电池电量小于 95 时, 判定为需要充电
                if (Integer.parseInt(split[0]) < 95 || Integer.parseInt(split[1]) < 95) {
                    log.info("当前电量小于 95% 开始执行充电: {}", batteryPower);
                    // 执行充电
                    operate(new OperateInp().setCommand(HangarEnum.机库_充电操作_充电.getCommand()).setAction(HangarEnum.机库_充电操作_充电.getAction()));
                    // 关闭推流
                } else {
                    log.info("电池电量大于 95% 不执行充电: {}", batteryPower);
                }
            }
        }, 5, TimeUnit.MINUTES);
    }
}
