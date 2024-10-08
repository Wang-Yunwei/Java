package com.mdsd.cloud.enums;

import com.mdsd.cloud.response.BusinessException;

import java.util.Arrays;

/**
 * @author WangYunwei [2024-09-26]
 */
public enum TyjwReturnCodeEnum {

    RETURN_CODE_1(1, "成功执行V3航线任务"),
    RETURN_CODE_2(2, "执行V3航线信息错误"),
    RETURN_CODE_3(3, "执行V3航线WPMZ文件不匹配"),
    RETURN_CODE_4(4, "加载V3航线WPMZ文件失败"),
    RETURN_CODE_5(5, "执行V3航线WPMZ文件不匹配"),
    RETURN_CODE_6(6, "执行V3航线指令无效"),
    RETURN_CODE_81(81, "自动起飞失败，电池电压差过大"),
    RETURN_CODE_160(160, "开始航线失败,未上传航线"),
    RETURN_CODE_216(216, "拍照失败，存储卡已满"),
    RETURN_CODE_224(224, "拍照失败，未安装载荷"),
    RETURN_CODE_225(225, "拍照失败，未安装载荷"),
    RETURN_CODE_227(227, "拍照失败，未安装存储卡"),
    RETURN_CODE_257(257, "Customize Toolbar..."),
    RETURN_CODE_258(258, "在当前状态下无法中断航线"),
    RETURN_CODE_259(259, "航线未运行时不能停止航线"),
    RETURN_CODE_260(260, "航线未运行时不能暂停航线"),
    RETURN_CODE_261(261, "飞行任务冲突，无法获得飞机的控制权限"),
    RETURN_CODE_262(262, "当前状态下恢复航线失败"),
    RETURN_CODE_513(513, "执行V3航线失败由于航点高度超过区域限高"),
    RETURN_CODE_514(514, "执行V3航线失败由于航线距离超过区域限远"),
    RETURN_CODE_515(515, "执行V3航线失败由于航线路径跨越禁飞区"),
    RETURN_CODE_516(516, "执行V3航线失败由于航点高度低于区域最小飞行高度"),
    RETURN_CODE_517(517, "执行V3航线失败由于障碍物"),
    RETURN_CODE_518(518, "执行V3航线失败由于rtk断开连接"),
    RETURN_CODE_519(519, "执行V3航线失败由于禁飞区边界限制"),
    RETURN_CODE_520(520, "执行V3航线失败由于遥控器失灵"),
    RETURN_CODE_521(521, "执行V3航线失败由于机场高度限制"),
    RETURN_CODE_522(522, "V3航线请求起飞失败"),
    RETURN_CODE_523(523, "V3航线自动起飞失败"),
    RETURN_CODE_524(524, "V3航线请求执行失败"),
    RETURN_CODE_525(525, "计划航线执行失败"),
    RETURN_CODE_526(526, "请求快速起飞协助失败"),
    RETURN_CODE_527(527, "快速起飞协助失败"),
    RETURN_CODE_528(528, "执行V3航线失败由于防御限制"),
    RETURN_CODE_769(769, "执行V3航线失败由于gps无效"),
    RETURN_CODE_770(770, "执行V3航线失败由于遥控器档位模式"),
    RETURN_CODE_771(771, "执行V3航线失败由于返航点记录失败"),
    RETURN_CODE_772(772, "执行V3航线失败由于电量过低"),
    RETURN_CODE_773(773, "执行V3航线失败由于返航"),
    RETURN_CODE_774(774, "执行V3航线失败由于adsb错误"),
    RETURN_CODE_775(775, "执行V3航线失败由于遥控器失联"),
    RETURN_CODE_776(776, "执行V3航线失败由于rtk未准备好"),
    RETURN_CODE_777(777, "执行V3航线失败由于无人机正在移动"),
    RETURN_CODE_778(778, "执行V3航线失败由于无人机在地面上，且电机已打开"),
    RETURN_CODE_779(779, "执行V3航线失败由于地面跟随相机无效"),
    RETURN_CODE_780(780, "执行V3航线失败由于地面跟随高度无效"),
    RETURN_CODE_781(781, "执行V3航线失败由于地面跟随地图错误"),
    RETURN_CODE_782(782, "执行V3航线失败由于返航点与rtk不一致"),
    RETURN_CODE_784(784, "执行V3航线失败由于风速过大"),
    RETURN_CODE_999(999, "航线飞行中不允许上传新航线"),
    RETURN_CODE_1000(1000, "航线格式错误"),
    RETURN_CODE_1001(1001, "下载航线失败"),
    RETURN_CODE_1023(1023, "执行V3航线失败由于无人机严重错误"),
    RETURN_CODE_1025(1025, "执行V3航线失败由于无法找到有效载荷"),
    RETURN_CODE_1026(1026, "执行V3航线失败由于操作执行失败"),
    RETURN_CODE_1027(1027, "执行V3航线失败由于未装载农药"),
    RETURN_CODE_1028(1028, "执行V3航线失败由于雷达异常断开"),
    RETURN_CODE_1200(1200, "M210停止航线出错"),
    RETURN_CODE_1281(1281, "执行V3航线失败由于用户退出"),
    RETURN_CODE_1282(1282, "执行V3航线失败由于用户中断"),
    RETURN_CODE_1283(1283, "执行V3航线失败由于用户设置返航点"),
    RETURN_CODE_1284(1284, "执行V3航线失败由于用户agro计划器状态更改"),
    RETURN_CODE_1285(1285, "执行V3航线失败由于切换遥控器模式"),
    RETURN_CODE_1536(1536, "执行V3航线失败由于航线轨迹初始化失败"),
    RETURN_CODE_1537(1537, "执行V3航线失败由于航线轨迹异常退出"),
    RETURN_CODE_1538(1538, "执行V3航线失败由于在地面，且电机已打开"),
    RETURN_CODE_1539(1539, "执行V3航线失败由于航线轨迹起始索引无效"),
    RETURN_CODE_1540(1540, "执行V3航线失败由于航线轨迹的csys模式无效"),
    RETURN_CODE_1541(1541, "执行V3航线失败由于航线轨迹高度模式无效"),
    RETURN_CODE_1542(1542, "执行V3航线失败由于航线轨迹航点无效"),
    RETURN_CODE_1543(1543, "执行V3航线失败由于航线轨迹偏航模式无效"),
    RETURN_CODE_1544(1544, "执行V3航线失败由于航线轨迹模式无效"),
    RETURN_CODE_1545(1545, "执行V3航线失败由于航线轨迹航点类型无效"),
    RETURN_CODE_1546(1546, "执行V3航线失败由于第一个航点类型错误"),
    RETURN_CODE_1547(1547, "执行V3航线失败由于航线全局水平超出范围"),
    RETURN_CODE_1548(1548, "执行V3航线失败由于航点数量超出范围"),
    RETURN_CODE_1549(1549, "执行V3航线失败由于航点经度或者维度超出范围"),
    RETURN_CODE_1550(1550, "执行V3航线失败由于转弯半径超出范围"),
    RETURN_CODE_1551(1551, "执行V3航线失败由于航线最大水平超出范围"),
    RETURN_CODE_1552(1552, "执行V3航线失败由于航线超出范围"),
    RETURN_CODE_1553(1553, "执行V3航线失败由于航迹偏航超出范围"),
    RETURN_CODE_1554(1554, "执行V3航线失败由于航线偏航模式无效"),
    RETURN_CODE_1555(1555, "执行V3航线失败由于航线中断信息任务id改变"),
    RETURN_CODE_1556(1556, "执行V3航线失败由于航线中断信息进度超出范围"),
    RETURN_CODE_1557(1557, "执行V3航线失败由于航线中断信息无效"),
    RETURN_CODE_1558(1558, "执行V3航线失败由于航线中断信息索引超出范围"),
    RETURN_CODE_1559(1559, "执行V3航线失败由于航线中断经度或者维度超出范围"),
    RETURN_CODE_1560(1560, "执行V3航线失败由于航线中断信息偏离航线范围"),
    RETURN_CODE_1561(1561, "执行V3航线失败由于航迹无效中断信息标志"),
    RETURN_CODE_1562(1562, "执行V3航线失败由于获取航线信息失败"),
    RETURN_CODE_1563(1563, "执行V3航线失败由于航迹生成失败"),
    RETURN_CODE_1564(1564, "执行V3航线失败由于航线lib库运行失败"),
    RETURN_CODE_1565(1565, "执行V3航线失败由于紧急制动"),
    RETURN_CODE_1588(1588, "执行V3航线失败由于动作点执行异常"),
    RETURN_CODE_1591(1591, "执行V3航线失败由于动作点索引重复"),
    RETURN_CODE_1592(1592, "执行V3航线失败由于航线大小太长或太短"),
    RETURN_CODE_1593(1593, "执行V3航线失败由于动作点树为空"),
    RETURN_CODE_1594(1594, "执行V3航线失败由于动作点树层为空"),
    RETURN_CODE_1595(1595, "执行V3航线失败由于动作点重复"),
    RETURN_CODE_1596(1596, "执行V3航线失败由于操作模式子数小于2"),
    RETURN_CODE_1597(1597, "执行V3航线失败由于动作点索引超出范围"),
    RETURN_CODE_1598(1598, "执行V3航线失败由于动作点ID为65535"),
    RETURN_CODE_1599(1599, "执行V3航线失败由于动作子节点不一致"),
    RETURN_CODE_1600(1600, "执行V3航线失败由于动作树层数过多"),
    RETURN_CODE_1601(1601, "执行V3航线失败由于动作树层数太少"),
    RETURN_CODE_1602(1602, "执行V3航线失败由于动作组超出范围"),
    RETURN_CODE_1603(1603, "执行V3航线失败由于动作组有效范围错误"),
    RETURN_CODE_1604(1604, "执行V3航线失败由于动作树根状态无效"),
    RETURN_CODE_1605(1605, "执行V3航线失败由于动作树模式状态无效"),
    RETURN_CODE_1606(1606, "执行V3航线失败由于中断信息动作组id超出范围"),
    RETURN_CODE_1607(1607, "执行V3航线失败由于动作状态树大小错误"),
    RETURN_CODE_1608(1608, "执行V3航线失败由于中断信息触发运行结果无效"),
    RETURN_CODE_1609(1609, "执行V3航线失败由于中断信息动作组id重复"),
    RETURN_CODE_1610(1610, "执行V3航线失败由于中断信息动作位置重复"),
    RETURN_CODE_1611(1611, "执行V3航线失败由于中断信息动作位置超出范围"),
    RETURN_CODE_1612(1612, "执行V3航线失败由于恢复id不在中断信息中"),
    RETURN_CODE_1613(1613, "执行V3航线失败由于将动作状态从无中断修改为中断"),
    RETURN_CODE_1614(1614, "执行V3航线失败由于无效的恢复信息"),
    RETURN_CODE_1626(1626, "切换视频源参数不正确"),
    RETURN_CODE_1634(1634, "执行V3航线失败由于未找到航点动作执行器"),
    RETURN_CODE_1649(1649, "执行V3航线失败由于未找到航点动作触发器"),
    RETURN_CODE_1650(1650, "执行V3航线失败由于单次检查失败"),
    RETURN_CODE_1700(1700, "M210开始航线出错"),
    RETURN_CODE_1701(1701, "开始航线失败,航线中请勿重复开始航线"),
    RETURN_CODE_1800(1800, "M210暂停航线出错"),
    RETURN_CODE_1801(1801, "暂停航线失败,1：重启云盒后请返航 2：不支持暂停遥控器开启的航线"),
    RETURN_CODE_1802(1802, "暂停航线失败,暂停一个未开始的航线"),
    RETURN_CODE_1900(1900, "M210继续航线出错"),
    RETURN_CODE_1901(1901, "断点续飞失败,请结束航线"),
    RETURN_CODE_1902(1902, "安全返航失败,请手动接管返航"),
    RETURN_CODE_1903(1903, "恢复航线失败,1：重启云盒后请返航 2：不支持恢复遥控器暂停的航线"),
    RETURN_CODE_2000(2000, "停止航线失败,1：重启云盒后请返航 2：不支持停止遥控器开启的航线"),
    RETURN_CODE_2121(2121, "修改返航高度参数不正确"),
    RETURN_CODE_2200(2200, "修改相机模式指令错误"),
    RETURN_CODE_2222(2222, "修改相机模式参数不正确"),
    RETURN_CODE_2400(2400, "非录像模式下开始录像"),
    RETURN_CODE_2500(2500, "非录像中停止录像"),
    RETURN_CODE_2600(2600, "切换广角视频源失败,请确认载荷是否有该功能"),
    RETURN_CODE_2601(2601, "切换变焦视频源失败,请确认载荷是否有该功能"),
    RETURN_CODE_2602(2602, "切换红外视频源失败,请确认载荷是否有该功能"),
    RETURN_CODE_2727(2727, "切换摄像头参数不正确"),
    RETURN_CODE_3100(3100, "设置降落点失败,获取飞机HOME点状态异常"),
    RETURN_CODE_3131(3131, "设置降落点参数不正确"),
    RETURN_CODE_5000(5000, "正在二维码降落中,禁止切换视频源"),
    RETURN_CODE_5001(5001, "正在二维码降落中,禁止修改相机模式"),
    RETURN_CODE_5002(5002, "当前飞行器不在返航中,请勿执行取消返航"),
    RETURN_CODE_5003(5003, "下载V3航线失败"),
    RETURN_CODE_5004(5004, "飞行器在地面,无需返航"),
    RETURN_CODE_5005(5005, "飞行器正在返航中"),
    RETURN_CODE_5006(5006, "请勿重复开始录像"),
    RETURN_CODE_5007(5007, "恢复航线失败,恢复一个未开始的航线"),
    RETURN_CODE_5008(5008, "停止航线失败,停止一个未开始的航线"),
    RETURN_CODE_5009(5009, "云盒固件升级中,禁止起飞"),
    RETURN_CODE_5010(5010, "云盒固件升级中,禁止开始航线"),
    RETURN_CODE_5011(5011, "云盒固件不支持激光测距,请联系技术支持"),
    RETURN_CODE_6000(6000, "指点飞行开始成功"),
    RETURN_CODE_6001(6001, "指点飞行中途变更目标点成功"),
    RETURN_CODE_6002(6002, "停止指点飞行成功"),
    RETURN_CODE_6003(6003, "手动对焦成功"),
    RETURN_CODE_6004(6004, "开始载荷精准降落"),
    RETURN_CODE_6005(6005, "开始下视精准降落"),
    RETURN_CODE_6006(6006, "二维码识别成功,即将取消自动降落进入二维码引导降落"),
    RETURN_CODE_6007(6007, "精准降落成功"),
    RETURN_CODE_6008(6008, "停止精准降落"),
    RETURN_CODE_6009(6009, "无人机已关机"),
    RETURN_CODE_6010(6010, "航线执行完成"),
    RETURN_CODE_6011(6011, "指定距离飞行完成,已到达目标点"),
    RETURN_CODE_6012(6012, "指定距离飞行开始成功"),
    RETURN_CODE_6013(6013, "指定距离飞行中途变更目标点成功"),
    RETURN_CODE_6014(6014, "停止指定距离飞行成功"),
    RETURN_CODE_6015(6015, "图片实时回传模块初始化成功"),
    RETURN_CODE_6016(6016, "开始激光测距成功"),
    RETURN_CODE_6017(6017, "结束激光测距成功"),
    RETURN_CODE_6018(6018, "开始单点测温成功"),
    RETURN_CODE_6019(6019, "结束单点测温成功"),
    RETURN_CODE_6020(6020, "开始区域测温成功"),
    RETURN_CODE_6021(6021, "结束区域测温成功"),
    RETURN_CODE_7001(7001, "载荷不支持切换视频源"),
    RETURN_CODE_7002(7002, "FPV视频中不支持切换视频源"),
    RETURN_CODE_7003(7003, "正在二维码降落中,禁止控制载荷"),
    RETURN_CODE_7004(7004, "正在二维码降落中,禁止关闭视频"),
    RETURN_CODE_7005(7005, "正在二维码降落中,禁止切换视频码流"),
    RETURN_CODE_7006(7006, "正在二维码降落中,禁止切换FPV视频"),
    RETURN_CODE_7007(7007, "正在二维码降落中,禁止拍照"),
    RETURN_CODE_7008(7008, "当前画面已经是FPV,请勿重复切换"),
    RETURN_CODE_7009(7009, "当前画面已经是载荷,请勿重复切换"),
    RETURN_CODE_7010(7010, "操控飞机无效,请先获取控制权"),
    RETURN_CODE_7011(7011, "指点飞行失败,请先获取控制权"),
    RETURN_CODE_7012(7012, "指点飞行失败,请将飞机升到空中"),
    RETURN_CODE_7013(7013, "指点飞行失败,打点距离大于20KM"),
    RETURN_CODE_7014(7014, "精准降落校准载荷,请勿使用遥控器控制载荷"),
    RETURN_CODE_7015(7015, "此版本云盒固件不支持设置云台模式,如需使用请联系技术支持升级"),
    RETURN_CODE_7016(7016, "航线任务已中断,可能原因【遥控器切挡/飞行器强制返航/网络RTK信号丢失/GPS信号丢失】"),
    RETURN_CODE_7017(7017, "航线任务已中断,遥控器取消航线"),
    RETURN_CODE_7018(7018, "航线任务已中断,请勿在电机开启状态下开始航线"),
    RETURN_CODE_7019(7019, "航线任务已中断,自动起飞超时"),
    RETURN_CODE_7020(7020, "航线任务已中断,自动起飞未达到预定高度"),
    RETURN_CODE_7021(7021, "航线任务已中断,自动起飞异常"),
    RETURN_CODE_7022(7022, "航线任务已中断,被高优先级任务抢占"),
    RETURN_CODE_7023(7023, "航线任务已中断,未知原因"),
    RETURN_CODE_7024(7024, "航线任务已中断,航线规划失败"),
    RETURN_CODE_7025(7025, "机场航线规划失败,请检查起飞机场坐标和备降点坐标是否设置成功"),
    RETURN_CODE_7026(7026, "机场航线规划失败,请检查降落机场坐标和备降点坐标是否设置成功"),
    RETURN_CODE_7027(7027, "电机启动中请勿自动起飞"),
    RETURN_CODE_7028(7028, "飞机已在空中请勿自动起飞"),
    RETURN_CODE_7029(7029, "飞机已在地面请勿自动降落"),
    RETURN_CODE_7030(7030, "正在二维码降落中,禁止切换载荷模式,请保持在录像模式下"),
    RETURN_CODE_7031(7031, "无人机起飞时不允许获取/释放操纵杆控制权限，建议:请在起飞前或起飞后进行操作"),
    RETURN_CODE_7032(7032, "无人机降落时不允许获取/释放操纵杆控制权限，建议:请在降落前或降落后进行操作"),
    RETURN_CODE_7033(7033, "一键起飞指定高度失败,飞行器首次上电起飞请保持遥控器与飞行器连接"),
    RETURN_CODE_7034(7034, "电机启动中,请勿紧急制动"),
    RETURN_CODE_7035(7035, "自动起飞失败,请先解除紧急制动"),
    RETURN_CODE_7036(7036, "自动返航失败,请先解除紧急制动"),
    RETURN_CODE_7037(7037, "自动降落失败,请先解除紧急制动"),
    RETURN_CODE_7038(7038, "开始任务失败,请先解除紧急制动"),
    RETURN_CODE_7039(7039, "恢复任务失败,请先解除紧急制动"),
    RETURN_CODE_7040(7040, "强制降落失败,请先解除紧急制动"),
    RETURN_CODE_7041(7041, "返航到指定机场失败,检测到飞行器在地面"),
    RETURN_CODE_7042(7042, "返航到指定机场失败,飞机当前位置距离机场大于20KM"),
    RETURN_CODE_7043(7043, "返航到指定机场失败,请先解除紧急制动"),
    RETURN_CODE_7044(7044, "打点飞行失败,安全模式自动起飞失败"),
    RETURN_CODE_7045(7045, "打点飞行失败,安全模式自动起飞超时"),
    RETURN_CODE_7046(7046, "打点飞行失败,检测到飞行器距上障碍物距离小于3米"),
    RETURN_CODE_7047(7047, "打点飞行失败,获取飞机控制权异常,请检查当前飞机模式"),
    RETURN_CODE_7048(7048, "打点飞行失败,请先解除紧急制动"),
    RETURN_CODE_7049(7049, "指定距离飞行失败,请先解除紧急制动"),
    RETURN_CODE_7050(7050, "指定距离飞行失败,获取飞机控制权异常,请检查当前飞机模式"),
    RETURN_CODE_7051(7051, "打点飞行失败,安全模式自动起飞失败,打点高度不能小于20米"),
    RETURN_CODE_7052(7052, "返航到指定机场失败,返航高度设置不正确,范围应在【20~1500】米"),
    RETURN_CODE_7053(7053, "修改返航高度失败,返航高度设置不正确,范围应在【20~1500】米"),
    RETURN_CODE_7054(7054, "开启激光测距失败,GPS信号弱,请移至空旷地"),
    RETURN_CODE_7055(7055, "开始单点测温失败,设定单点测温坐标失败"),
    RETURN_CODE_7056(7056, "开始区域测温失败,设定区域测温坐标失败"),
    RETURN_CODE_7057(7057, "打点飞行失败,安全模式自动起飞失败,GPS信号弱,请移至空旷地"),
    RETURN_CODE_7058(7058, "飞行器已在地面请勿强制降落"),
    RETURN_CODE_7059(7059, "云盒固件版本过低,不支持此功能,请联系技术支持远程升级"),
    RETURN_CODE_7060(7060, "开始航线失败,请先取消返航"),
    RETURN_CODE_7061(7061, "开始航线失败,请先取消降落"),
    RETURN_CODE_7062(7062, "打点飞行失败,请先取消返航"),
    RETURN_CODE_7063(7063, "打点飞行失败,请先取消降落"),
    RETURN_CODE_7064(7064, "返航到指定机场失败,请先取消降落"),
    RETURN_CODE_7065(7065, "切换FPV失败,飞行器无FPV镜头"),
    RETURN_CODE_7066(7066, "图片实时回传功能已失效,遥控器格式完相机后请重启云盒或飞机"),
    RETURN_CODE_8000(8000, "航线中触发避障,被动触发暂停航线"),
    RETURN_CODE_8001(8001, "指点飞行退出,检测到返航或降落或强制降落状态"),
    RETURN_CODE_8002(8002, "指点飞行退出,检测到飞机在地面"),
    RETURN_CODE_8003(8003, "初始化图片实时回传功能异常,请检查载荷TF卡是否安装"),
    RETURN_CODE_8004(8004, "初始化图片实时回传功能异常"),
    RETURN_CODE_8005(8005, "机场开仓超时,即将降落至备降点"),
    RETURN_CODE_8006(8006, "机场开仓失败,即将降落至备降点"),
    RETURN_CODE_8007(8007, "二维码识别超时,即将降落至备降点"),
    RETURN_CODE_8008(8008, "二维码识别超时,请人工接管"),
    RETURN_CODE_8009(8009, "二维码精准降落初始化失败,请检查载荷类型"),
    RETURN_CODE_8010(8010, "未识别到二维码,即将降落至备降点"),
    RETURN_CODE_8011(8011, "二维码降落中检测到被动返航,请注意飞机电量,必要时遥控器接管"),
    RETURN_CODE_8012(8012, "云盒控制权丢失,MSDK程序抢夺控制权,有炸机风险请遥控器接管"),
    RETURN_CODE_8013(8013, "云盒控制权丢失,遥控器失联重置"),
    RETURN_CODE_8014(8014, "云盒控制权丢失,遥控器非P挡"),
    RETURN_CODE_8015(8015, "云盒控制权丢失,遥控器切挡"),
    RETURN_CODE_8016(8016, "云盒控制权丢失,遥控器暂停触发"),
    RETURN_CODE_8017(8017, "云盒控制权丢失,遥控器触发返航"),
    RETURN_CODE_8018(8018, "云盒控制权丢失,飞机触发低电量返航,请注意安全"),
    RETURN_CODE_8019(8019, "云盒控制权丢失,飞机触发低电量强制降落,请注意安全"),
    RETURN_CODE_8020(8020, "云盒控制权丢失,OSDK断连,请遥控器接管飞机"),
    RETURN_CODE_8021(8021, "云盒控制权丢失,到达禁飞区边界,请确认空域"),
    RETURN_CODE_8022(8022, "飞机距前障碍物距离小于3米,请谨慎飞行"),
    RETURN_CODE_8023(8023, "飞机距右障碍物距离小于3米,请谨慎飞行"),
    RETURN_CODE_8024(8024, "飞机距后障碍物距离小于3米,请谨慎飞行"),
    RETURN_CODE_8025(8025, "飞机距左障碍物距离小于3米,请谨慎飞行"),
    RETURN_CODE_8026(8026, "飞机距上障碍物距离小于3米,请谨慎飞行"),
    RETURN_CODE_8027(8027, "视频编解码模块异常,开始发送原画,请不要用遥控器长时间查看载荷媒体库"),
    RETURN_CODE_8028(8028, "视频编解码模块异常,即将降落至备降点"),
    RETURN_CODE_8029(8029, "指点飞行退出,检测到云盒无控制权"),
    RETURN_CODE_8030(8030, "起飞机场备降点距机场距离不合理,范围应在【5~500】米,请重新设置"),
    RETURN_CODE_8031(8031, "降落机场备降点距机场距离不合理,范围应在【5~500】米,请重新设置"),
    RETURN_CODE_8032(8032, "一键起飞指定高度退出,检测到手动中断飞机起飞升高"),
    RETURN_CODE_8033(8033, "一键起飞指定高度退出,检测到飞行器距上障碍物距离小于3米"),
    RETURN_CODE_8034(8034, "一键起飞指定高度退出,检测到云盒无控制权"),
    RETURN_CODE_8035(8035, "一键降落指定高度退出,飞行器已取消降落"),
    RETURN_CODE_8036(8036, "一键降落指定高度退出,检测到飞行器距下障碍物距离小于10米"),
    RETURN_CODE_8037(8037, "距离控制飞行退出,检测到飞行器不在空中"),
    RETURN_CODE_8038(8038, "距离控制飞行退出,检测到云盒丢失控制权"),
    RETURN_CODE_8039(8039, "距离控制飞行退出,检测到飞行器返航"),
    RETURN_CODE_8040(8040, "距离控制飞行退出,检测到飞行器降落"),
    RETURN_CODE_8041(8041, "距离控制飞行退出,检测到已到达最长控制时长"),
    RETURN_CODE_8042(8042, "飞行器已在自定义电子围栏外,控制已失效,请立即返航或降落"),
    RETURN_CODE_8043(8043, "飞行器已在自定义禁飞区内,控制已失效,请立即返航或降落"),
    RETURN_CODE_8044(8044, "一键起飞指定高度退出,检测到紧急制动"),
    RETURN_CODE_8045(8045, "打点飞行退出,检测到紧急制动"),
    RETURN_CODE_8046(8046, "飞行器悬停等待机场开仓中,请勿操控飞行器"),
    RETURN_CODE_8047(8047, "距离控制飞行退出,检测到紧急制动"),
    RETURN_CODE_65535(65535, "致命错误！意外结果！"),
    RETURN_CODE_65536(65536, "成功"),
    RETURN_CODE_65537(65537, "最小初始航点值设置大于允许的最大航点值"),
    RETURN_CODE_65538(65538, "最小初始航点值小于允许的最小航点数量"),
    RETURN_CODE_65539(65539, "航点终点指数等于或大于总航点数"),
    RETURN_CODE_65540(65540, "开始指数大于上传的航线的终点指数"),
    RETURN_CODE_65541(65541, "上传的航点终点指数大于初始航点总数"),
    RETURN_CODE_65542(65542, "预期下载的首尾航点指数不在FC中存储的范围内"),
    RETURN_CODE_65544(65544, "当前位置远离第一个航点"),
    RETURN_CODE_65546(65546, "两个相邻航点过于接近，其结果可能与实际不同"),
    RETURN_CODE_65547(65547, "两个相邻的航点不在[0.5m，5000m]范围内，其结果可能与实际不同"),
    RETURN_CODE_65548(65548, "上传航线的最大速度大于总体最大速度"),
    RETURN_CODE_65549(65549, "上传航线的本地巡航速度大于本地最高限速"),
    RETURN_CODE_65550(65550, "上传航线的本地巡航速度大于总体最高限速"),
    RETURN_CODE_65551(65551, "总体最大速度大于允许的最大速度或小于允许的最小速度"),
    RETURN_CODE_65552(65552, "总体巡航速度大于总体最大速度"),
    RETURN_CODE_65553(65553, "返航模式超出航线设置的返航范围"),
    RETURN_CODE_65554(65554, "结束操作超出计划航线设定的结束范围"),
    RETURN_CODE_65555(65555, "rc_lost_action超出计划航线设定的 rc_lost_action范围"),
    RETURN_CODE_65556(65556, "上传航线的偏航角模式无效。参考航线规划中的偏航角模式定义。"),
    RETURN_CODE_65557(65557, "上传航线中的偏航指令不在范围内。范围应在 [-180 180]度"),
    RETURN_CODE_65558(65558, "上传航线的偏航转向无效。应为0:顺时针或1:逆时针"),
    RETURN_CODE_65559(65559, "上载航线的航线类型无效。参考math/waypoint/planner.h中定义的航点类型"),
    RETURN_CODE_65560(65560, "开始/停止指令无效"),
    RETURN_CODE_65561(65561, "暂停/恢复指令不属于任何指令范围"),
    RETURN_CODE_65562(65562, "break/restore命令不等于任何指令范围"),
    RETURN_CODE_65563(65563, "初始参考点位置坐标超出设定的范围"),
    RETURN_CODE_65564(65564, "阻尼距离大于或等于相邻航点的距离"),
    RETURN_CODE_65565(65565, "无法将航线退出类型设置为航线"),
    RETURN_CODE_65566(65566, "地面站初始信息尚未上传"),
    RETURN_CODE_65567(65567, "航线尚未上传"),
    RETURN_CODE_65568(65568, "最小初始航点数未上传"),
    RETURN_CODE_65569(65569, "收到起飞命令时航点计划已开始"),
    RETURN_CODE_65570(65570, "收到停止命令时航点计划未运行"),
    RETURN_CODE_65571(65571, "地面站未启动（暂停/继续时）"),
    RETURN_CODE_65572(65572, "地面站未启动(中断/恢复时)"),
    RETURN_CODE_65573(65573, "不在航点任务内（无法暂停/继续或中断/恢复）"),
    RETURN_CODE_65574(65574, "当前状态是暂停，无法再次使用暂停命令"),
    RETURN_CODE_65575(65575, "当前不在暂停状态，无法使用继续命令"),
    RETURN_CODE_65576(65576, "当前状态是中断，无法再次使用中断命令"),
    RETURN_CODE_65577(65577, "当前不在中断状态，无法使用恢复命令"),
    RETURN_CODE_65578(65578, "配置禁止使用暂停/继续API"),
    RETURN_CODE_65579(65579, "配置禁止使用中断/恢复API"),
    RETURN_CODE_65580(65580, "没有可还原断点的记录"),
    RETURN_CODE_65581(65581, "当前没有记录可恢复的轨迹任务点"),
    RETURN_CODE_65582(65582, "没有下一个轨迹任务点可进行恢复"),
    RETURN_CODE_65583(65583, "没有再下一个轨迹任务点可进行恢复"),
    RETURN_CODE_65584(65584, "上传的航点索引与储存的航点不连续"),
    RETURN_CODE_65585(65585, "选择进入的航线模式设置的不是这个初始起点的航线"),
    RETURN_CODE_65586(65586, "航点计划在航点初始化时已启动"),
    RETURN_CODE_65587(65587, "航点阻尼距离超出设定范围"),
    RETURN_CODE_65588(65588, "航点坐标位置超出合理范围"),
    RETURN_CODE_65589(65589, "首个航点类型错误，无法启动航线"),
    RETURN_CODE_65590(65590, "航点位置超出高度范围"),
    RETURN_CODE_65592(65592, "航点位置超出距离范围"),
    RETURN_CODE_65593(65593, "航点位置超出高度范围"),
    RETURN_CODE_65696(65696, "未上传航线"),
    RETURN_CODE_131072(131072, "成功"),
    RETURN_CODE_131073(131073, "航点验证失败"),
    RETURN_CODE_131074(131074, "原点尚未记录"),
    RETURN_CODE_131075(131075, "由于GPS信号不良，当前定位精度较低"),
    RETURN_CODE_131077(131077, "rtk未连接或无效"),
    RETURN_CODE_196608(196608, "成功"),
    RETURN_CODE_196609(196609, "轨道穿过NFZ"),
    RETURN_CODE_196610(196610, "智能电池的电流容量或非智能电池的电压低于1级或2级阈值"),
    RETURN_CODE_4194304(4194304, "成功"),
    RETURN_CODE_4194305(4194305, "操作的ID是重复的"),
    RETURN_CODE_4194306(4194306, "内存空间不足"),
    RETURN_CODE_4194307(4194307, "用于获取操作信息的缓冲区大小小于操作大小"),
    RETURN_CODE_4194308(4194308, "找不到操作的ID"),
    RETURN_CODE_4194309(4194309, "下载操作开始ID大于操作结束ID"),
    RETURN_CODE_4194310(4194310, "无法下载或获取操作内核中存储的无操作项的最小-最大操作ID"),
    RETURN_CODE_4259840(4259840, "成功"),
    RETURN_CODE_4259841(4259841, "触发器的类型ID无效"),
    RETURN_CODE_4259873(4259873, "到达航路点触发器中的结束索引小于开始索引"),
    RETURN_CODE_4259874(4259874, "间隔大于开始索引到结束索引的差值"),
    RETURN_CODE_4259875(4259875, "自动终止，间隔大于开始索引到结束索引的差值"),
    RETURN_CODE_4259905(4259905, "关联类型大于最大值"),
    RETURN_CODE_4259969(4259969, "间隔类型大于最大值"),
    RETURN_CODE_4325376(4325376, "成功"),
    RETURN_CODE_4325377(4325377, "不支持执行致动器，例如，尝试停止摄像机拍摄"),
    RETURN_CODE_4325378(4325378, "执行器的类型ID无效"),
    RETURN_CODE_4325379(4325379, "执行器的功能ID无效"),
    RETURN_CODE_4390912(4390912, "成功"),
    RETURN_CODE_4390913(4390913, "无法将快照cmd发送到相机"),
    RETURN_CODE_4390914(4390914, "无法向摄像头发送视频启动命令"),
    RETURN_CODE_4390915(4390915, "无法向摄像头发送视频停止命令"),
    RETURN_CODE_4390916(4390916, "相机焦点参数xy超出有效范围（0,1）"),
    RETURN_CODE_4390917(4390917, "无法向摄像头发送调焦指令"),
    RETURN_CODE_4390918(4390918, "无法向摄像头发送聚焦指令"),
    RETURN_CODE_4390919(4390919, "相机对焦功能的焦距超出有效范围"),
    RETURN_CODE_4391168(4391168, "相机无法执行命令"),
    RETURN_CODE_4456448(4456448, "成功"),
    RETURN_CODE_4456449(4456449, "控制参数无效"),
    RETURN_CODE_4456450(4456450, "时间参数无效"),
    RETURN_CODE_4456451(4456451, "无法到达目标角度"),
    RETURN_CODE_4456452(4456452, "转向设备忙或无转向设备"),
    RETURN_CODE_4456453(4456453, "索引错误"),
    RETURN_CODE_4587520(4587520, "成功"),
    RETURN_CODE_4587521(4587521, "偏航角大于最大偏航角"),
    RETURN_CODE_4587522(4587522, "超时，无法达到目标偏航角"),
    RETURN_CODE_4587523(4587523, "偏航角已占用"),
    RETURN_CODE_4587524(4587524, "设置偏航角与现在的值相同"),
    RETURN_CODE_4653056(4653056, "成功"),
    RETURN_CODE_4653057(4653057, "载荷控制指令发送失败"),
    RETURN_CODE_4653058(4653058, "载荷控制指令执行失败");

    private final int code;

    private final String msg;

    TyjwReturnCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static TyjwReturnCodeEnum getMsg(int code){
        return Arrays.stream(TyjwReturnCodeEnum.values()).filter(el -> code == el.code).findFirst().orElseThrow(() -> new BusinessException(String.format("未知返还码: %d", code)));
    }
}
