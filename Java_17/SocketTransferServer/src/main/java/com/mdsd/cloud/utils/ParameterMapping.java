package com.mdsd.cloud.utils;

import com.mdsd.cloud.controller.tyjw.dto.*;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * @author WangYunwei [2024-08-20]
 */
public class ParameterMapping {

    /**
     * ProtoBuf: 机库信息
     */
    public static TyjwProtoBuf.HangarPoint getHangarPoint(HangarPointDTO arg) {

        TyjwProtoBuf.HangarPoint.Builder builder = TyjwProtoBuf.HangarPoint.newBuilder()
                .setHangarId(arg.getHangarId())// 机库ID
                .setHangarLng(arg.getHangarLng())// 机库经度
                .setHangarLat(arg.getHangarLat())// 机库纬度
                .setAlternateLng(arg.getAlternateLng())// 备降点经度
                .setAlternateLat(arg.getAlternateLat())// 备降点纬度
                .setMediaUploadFlag(arg.getMediaUploadFlag());// 飞机回仓后是否等待所有媒体文件传完再关仓: 0-立即关仓, 1-等待传完后关仓
        return builder.build();
    }

    /**
     * ProtoBuf: 获取航线点动作
     */
    public static TyjwProtoBuf.PointAction getPointAction(PointActionDTO arg) {

        TyjwProtoBuf.PointAction.Builder builder = TyjwProtoBuf.PointAction.newBuilder()
                .setType(arg.getType())// 类型: 1-变焦,2-拍照,3-录像,4-停录,5-控制飞机机头偏航,6-控制云台俯仰,7-控制云台偏航,8-控制云台横滚,9-悬停,10-等距间隔拍照,11-等时间隔拍照,12-结束间隔拍照,13-单条喊话,14-开始循环喊话,15-结束循环喊话,16-对焦
                .setParam(arg.getParam())// 参数: [1.变焦]->变焦倍数(范围：2~200) 、 [5.控制飞机机头偏航(范围：-180~180)、6.控制云台俯仰(范围：-120~30)、7.控制云台偏航(范围：-180~180)、8.控制云台横滚(范围：-90~60)]->角度(按正北坐标系绝对值控制) 、[9.悬停]->时间(单位:秒,范围:1~25,如果想停留大于25秒时长,可以加多组悬停动作) [10.等距间隔拍照]->距离(单位:米,范围:1~100)  [11.等时间隔拍照]->时间(单位:秒,范围:1~30)  [13/14->喊话器俯仰角度，如果此值为0默认改成-90](范围：0~-90)
                .setWaitTime(arg.getWaitTime())// 动作触发后等待时长(单位:秒) [此值如果不需要可以不传,如需悬停可添加类型9悬停动作] [13/14->喊话器声音，如果此值不传默认50](范围：0~100)
                .setSpeakInfo(arg.getSpeakInfo());// 喊话内容 (type=13 和 type=14 时生效)
        return builder.build();
    }

    /**
     * ProtoBuf: 航线点
     */
    public static TyjwProtoBuf.PointData getPointData(PointDataDTO arg) {

        TyjwProtoBuf.PointData.Builder builder = TyjwProtoBuf.PointData.newBuilder()
                .setLng(arg.getLng())// 经度
                .setLat(arg.getLat())// 纬度
                .setHeight(arg.getHeight())// 高度 (单位: 米,范围0~遥控器限高,此值为相对起飞点高度,如果太高会有失去网络覆盖风险)
                .setSpeed(arg.getSpeed());// 速度 (单位: 米/秒,范围 0 ~ 15),默认8
        if (null != arg.getFlightPathMode()) {
            builder.setFlightPathMode(arg.getFlightPathMode());// 航线飞行模式: 1-直线飞行,2-协调转弯,3-曲线飞行(停),4-曲线飞行(不停)
        }
        if (null != arg.getDampingDistance()) {
            builder.setDampingDistance(arg.getDampingDistance());// 转弯半径(仅协调转弯模式下有效),取值范围1~655.35单位:米,两点之间距离要小于此值,此值要根据实际情况计算,切勿随便设此值
        }
        if (null != arg.getSafeGohomeFlag()) {
            builder.setSafeGohomeFlag(arg.getSafeGohomeFlag());// 是否为安全返航点: 0-不是,1-是 (暂时未启用)
        }
        if (null != arg.getHeadingMode()) {
            builder.setHeadingMode(arg.getHeadingMode());// 偏航模式: 0-自动,1-锁定,2-遥控器控制,3-云台依照航点方向旋转,4-朝向兴趣点,5-飞机和云台的偏航同时旋转
        }
        if (null != arg.getHeading()) {
            builder.setHeading(arg.getHeading());// 航点偏航 (-180 ~ 180)
        }
        if (null != arg.getInterestIndex()) {
            builder.setInterestIndex(arg.getInterestIndex());// 兴趣点下标,默认-1
        }
        if (null != arg.getGroupIndex()) {
            builder.setGroupIndex(arg.getGroupIndex());// 组下标
        }
        if (!CollectionUtils.isEmpty(arg.getActions())) {
            arg.getActions().forEach(el -> builder.addActions(getPointAction(el)));// 动作数组
        }
        return builder.build();
    }

    /**
     * ProtoBuf: 航线数据
     */
    public static TyjwProtoBuf.PlanLineData getPlanLineData(PlanLineDataDTO arg) {

        TyjwProtoBuf.PlanLineData.Builder builder = TyjwProtoBuf.PlanLineData.newBuilder()
                .setFinishedAction(arg.getFinishedAction())// 结束航线后动作: 1-返航,2-原地悬停,3-原地降落,4-原地绕飞,5-返回至第一个航点并悬停
                .setMaxSpeed(arg.getMaxSpeed())// 最大飞行速度(单位:米/秒,范围0~15),默认15
                .setAutoSpeed(arg.getAutoSpeed())// 自动飞行速度(单位:米/秒,范围0~15),默认8
                .setLoseAction(arg.getLoseAction())// 网络失联后动作: 0-返回HOME点,1=继续航线,默认0
                .setHomeHeight(arg.getHomeHeight());// 返航高度 (单位:米,范围20~1500)
        if (null != arg.getIsSaveEnergyMode()) {
            builder.setIsSaveEnergyMode(arg.getIsSaveEnergyMode());// 是否开启节能模式: 0-不是,1-是(说明: 节能模式下,飞机沿最短直线距离由起飞点到航线第一个航点,非升高到第一个航点高度再飞往第一个航点)
        }
        if (Objects.nonNull(arg.getTakeOffPoint())) {
            builder.setTakeOffPoint(getPointData(arg.getTakeOffPoint()));// 起飞点(对象只包含经纬高)
        }
        if (null != arg.getSecurityHeight()) {
            builder.setSecurityHeight(arg.getSecurityHeight());// 安全起飞高度 (单位:米,范围20~1500)
        }
        if (null != arg.getTemplateId()) {
            builder.setTemplateId(arg.getTemplateId());// 航线模板编号,非模板航线时传 0
        }
        if (!CollectionUtils.isEmpty(arg.getPoints())) {
            arg.getPoints().forEach(el -> builder.addPoints(getPointData(el)));// 坐标点数组
        }
        if (Objects.nonNull(arg.getTakePoint())) {
            builder.setTakePoint(getHangarPoint(arg.getTakePoint()));// 起飞点机库信息
        }
        if (Objects.nonNull(arg.getLandPoint())) {
            builder.setLandPoint(getHangarPoint(arg.getLandPoint()));// 降落点机库信息
        }
        if (Objects.nonNull(arg.getNormalModel())) {
            builder.setNormalModel(getNormalModel(arg.getNormalModel()));// 普通建模
        }
        if (Objects.nonNull(arg.getSurroundModel())) {
            builder.setSurroundModel(getSurroundModel(arg.getSurroundModel()));// 环绕建模
        }
        if (!CollectionUtils.isEmpty(arg.getInterests())) {
            arg.getInterests().forEach(el -> builder.addInterests(getPointData(el)));// 兴趣点数组(对象只包含经纬高)
        }
        return builder.build();
    }

    /**
     * ProtoBuf: 获取普通建模
     */
    public static TyjwProtoBuf.NormalModel getNormalModel(NormalModelDTO arg) {

        TyjwProtoBuf.NormalModel.Builder builder = TyjwProtoBuf.NormalModel.newBuilder()
                .setServiceId(arg.getServiceId())// 建模平台编号(默认1): 1-瞰景,2-大势
                .setCameraId(arg.getCameraId())// 相机编号
                .setIsModeling(arg.getIsModeling())// 是否实时建模: 0-不,1-是
                .setMode(arg.getMode())// 作业模式: 1-倾斜,2-正摄
                .setHOverlap(arg.getHOverlap())// 横向重叠率
                .setVOverlap(arg.getVOverlap())// 纵向重叠率
                .setMirror(arg.getMirror())// 正反: 1-正,2-反
                .setDirection(arg.getDirection())// 方向: 1-南北,2-东西
                .setFlightPathMode(arg.getFlightPathMode())// 航线飞行模式: 1-直线飞行,2-协调转弯
                .setHeight(arg.getHeight());// 测绘高度(单位:米,范围20~1500)
        arg.getArea().forEach(el -> builder.addArea(getPointData(el)));// 作业区域
        return builder.build();
    }

    /**
     * ProtoBuf: 获取环绕建模
     */
    public static TyjwProtoBuf.SurroundModel getSurroundModel(SurroundModelDTO arg) {

        TyjwProtoBuf.SurroundModel.Builder builder = TyjwProtoBuf.SurroundModel.newBuilder()
                .setServiceId(arg.getServiceId())// 建模平台编号: 1-瞰景,2-天宇
                .setCameraId(arg.getCameraId())// 相机编号
                .setIsModeling(arg.getIsModeling())// 是否实时建模: 0=不,1=是
                .setHeight(arg.getHeight())// 测绘高度(单位:米,范围20~1500)
                .setExposureCount(arg.getExposureCount());// 曝光点数(范围: 12、16、20、24)
        arg.getArea().forEach(el -> builder.addArea(getPointData(el)));// 作业区域
        arg.getBoundary().forEach(el -> builder.addBoundary(getPointData(el)));// 边界区域
        return builder.build();
    }

}
