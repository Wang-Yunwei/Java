package com.mdsd.cloud.controller.socket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author WangYunwei [2024-07-15]
 */
@Getter
@Setter
@Accessors(chain = true)
public class PointAction {

    @Schema(description = "类型: 1-变焦,2-拍照,3-录像,4-停录,5-控制飞机机头偏航,6-控制云台俯仰,7-控制云台偏航,8-控制云台横滚,9-悬停,10-等距间隔拍照,11-等时间隔拍照,12-结束间隔拍照,13-单条喊话,14-开始循环喊话,15-结束循环喊话,16-对焦")
    private Byte type;

    @Schema(description = "参数: [1.变焦]->变焦倍数(范围：2~200) 、 [5.控制飞机机头偏航(范围：-180~180)、6.控制云台俯仰(范围：-120~30)、7.控制云台偏航(范围：-180~180)、8.控制云台横滚(范围：-90~60)]->角度(按正北坐标系绝对值控制) 、[9.悬停]->时间(单位:秒,范围:1~25,如果想停留大于25秒时长,可以加多组悬停动作) [10.等距间隔拍照]->距离(单位:米,范围:1~100)  [11.等时间隔拍照]->时间(单位:秒,范围:1~30)  [13/14->喊话器俯仰角度，如果此值为0默认改成-90](范围：0~-90)")
    private Float param;

    @Schema(description = "动作触发后等待时长(单位:秒) [此值如果不需要可以不传,如需悬停可添加类型9悬停动作] [13/14->喊话器声音，如果此值不传默认50](范围：0~100)")
    private Byte waitTime;

    @Schema(description = "喊话内容 (type=13 和 type=14 时生效)")
    private String speakInfo;
}
