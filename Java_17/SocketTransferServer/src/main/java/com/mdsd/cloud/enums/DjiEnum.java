package com.mdsd.cloud.enums;

import com.mdsd.cloud.response.BusinessException;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author WangYunwei [2025-03-26]
 */
@Getter
public enum DjiEnum {

    心跳(0x00, 0x00, null),
    无人机信息(0x01, 0x00, null),
    消息订阅(0x02, 0x00, null),
    相机管理(0x03, 0x00, null),
    云台管理(0x04, 0x00, null),
    电源管理(0x05, 0x00, null),
    飞行控制(0x06, 0x00, null),
    自定义控件(0x07, 0x00, null),
    HMS功能(0x08, 0x00, null),
    时间同步(0x09, 0x00, null),
    数据传输(0x0A, 0x00, null),
    XPORT控制(0x0B, 0x00, null),
    本地升级(0x0C, 0x00, null),
    灰度感知图(0x0D, 0x00, null),
    相机码流(0x0E, 0x00, null),
    航点(0x0F, 0x00, null),
    精准定位(0x10, 0x00, null),
    SDK互联互通(0x11, 0x00, null),

    云台管理_反初始化(云台管理.module, 0x0400, null),
    云台管理_初始化(云台管理.module, 0x0401, null),
    云台管理_设置工作模式(云台管理.module, 0x0402, "{\"mountPosition\":%d,\"mode\":%d}"),
    云台管理_重置角度(云台管理.module, 0x0403, "{\"mountPosition\":%d,\"resetMode\":%d}"),
    云台管理_旋转角度(云台管理.module, 0x0404, "{\"mountPosition\":%d,\"rotation\":[%d,%d,%d,%d,%f]}"),
    云台管理_设置俯仰限位扩展(云台管理.module, 0x0405, null),
    云台管理_设置最大速度百分比(云台管理.module, 0x0406, null),
    云台管理_设置云台控制器的平滑因子(云台管理.module, 0x0407, null),
    云台管理_恢复出厂设置(云台管理.module, 0x0408, null),

    飞行控制_反初始化(飞行控制.module, 0x0600, null),
    飞行控制_初始化(飞行控制.module, 0x0601, null),
    飞行控制_设置RTK功能(飞行控制.module, 0x0602, null),
    飞行控制_获取RTK状态(飞行控制.module, 0x0603, null),
    飞行控制_设置失联动作(飞行控制.module, 0x0604, null),
    飞行控制_获取失联动作(飞行控制.module, 0x0605, null),
    飞行控制_设置水平视觉避障(飞行控制.module, 0x0606, null),
    飞行控制_获取水平视觉避障(飞行控制.module, 0x0607, null),
    飞行控制_设置水平雷达避障(飞行控制.module, 0x0608, null),
    飞行控制_获取水平雷达避障(飞行控制.module, 0x0609, null),
    飞行控制_设置上视觉避障(飞行控制.module, 0x060A, null),
    飞行控制_获取上视觉避障(飞行控制.module, 0x060B, null),
    飞行控制_设置上雷达避障(飞行控制.module, 0x060C, null),
    飞行控制_获取上雷达避障(飞行控制.module, 0x060D, null),
    飞行控制_设置下视觉避障(飞行控制.module, 0x060E, null),
    飞行控制_获取下视觉避障(飞行控制.module, 0x060F, null),
    飞行控制_紧急制动(飞行控制.module, 0x0610, null),
    飞行控制_退出紧急制动(飞行控制.module, 0x0611, null),
    飞行控制_在地面时启动电机(飞行控制.module, 0x0612, null),
    飞行控制_在地面时关闭电机(飞行控制.module, 0x0613, null),
    飞行控制_紧急停止电机(飞行控制.module, 0x0614, null),
    飞行控制_起飞(飞行控制.module, 0x0615, null),
    飞行控制_降落(飞行控制.module, 0x0616, null),
    飞行控制_取消降落(飞行控制.module, 0x0617, null),
    飞行控制_确认着陆(飞行控制.module, 0x0618, null),
    飞行控制_强制着陆(飞行控制.module, 0x0619, null),
    飞行控制_自定义home点(飞行控制.module, 0x061A, null),
    飞行控制_使用GPS位置设置home点(飞行控制.module, 0x061B, null),
    飞行控制_设置返航高度(飞行控制.module, 0x061C, null),
    飞行控制_获取返航高度(飞行控制.module, 0x061D, null),
    飞行控制_获取国家码(飞行控制.module, 0x061E, null),
    飞行控制_返航(飞行控制.module, 0x061F, null),
    飞行控制_取消返航(飞行控制.module, 0x0620, null),
    飞行控制_获取摇杆控制权(飞行控制.module, 0x0621, null),
    飞行控制_释放摇杆控制权(飞行控制.module, 0x0622, null),
    飞行控制_注册控制权切换事件回调函数(飞行控制.module, 0x0623, null),
    飞行控制_设置摇杆模式(飞行控制.module, 0x0624, "{\"horizontalCoordinate\":%d,\"horizontalControlMode\":%d,\"stableControlMode\":%d,\"verticalControlMode\":%d,\"yawControlMode\":%d}"),
    飞行控制_执行摇杆动作(飞行控制.module, 0x0625, "{\"north\":%d,\"east\":%d,\"down\":%d,\"yaw\":%d,\"down_speed\":%d}"),
    飞行控制_执行紧急制动(飞行控制.module, 0x0626, null),
    飞行控制_取消紧急制动(飞行控制.module, 0x0627, null),
    飞行控制_获取飞行器通用信息(飞行控制.module, 0x0628, null),
    飞行控制_设置丢失动作启用状态(飞行控制.module, 0x0629, null),
    飞行控制_获取丢失动作启用状态(飞行控制.module, 0x062A, null),
    飞行控制_注册触发FTS事件回调函数(飞行控制.module, 0x062B, null),

    航点_反初始化(航点.module, 0x0F00, null),
    航点_初始化(航点.module, 0x0F01, null),
    航点_上传任务(航点.module, 0x0F02, "{\"missTotalLen\":%d,\"missionList\":%s}"),
    航点_开始任务(航点.module, 0x0F03, null),
    航点_停止任务(航点.module, 0x0F04, null),
    航点_暂停任务(航点.module, 0x0F05, null),
    航点_恢复任务(航点.module, 0x0F06, null),
    航点_获取全局巡航速度(航点.module, 0x0F07, null),
    航点_设置全局巡航速度(航点.module, 0x0F08, null);

    private final int module;

    private final int directive;

    private final String arguments;

    DjiEnum(int module, int directive, String arguments) {
        this.module = module;
        this.directive = directive;
        this.arguments = arguments;
    }

    public static DjiEnum getEnum(int module, int directive) {
        return Arrays.stream(DjiEnum.values()).filter(el -> module == el.module && directive == el.directive).findFirst().orElseThrow(() -> new BusinessException(String.format("未知指令动作: 0x%02X_0x%02X", module, directive)));
    }
}
