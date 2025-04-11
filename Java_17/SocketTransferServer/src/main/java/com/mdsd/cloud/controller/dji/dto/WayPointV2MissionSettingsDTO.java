package com.mdsd.cloud.controller.dji.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author WangYunwei [2025-03-25]
 */
@Getter
@Setter
public class WayPointV2MissionSettingsDTO {

    /**
     * 最大飞行速度 ,单位: m/s ,范围: [2,15]
     */
    private float maxFlightSpeed;

    /**
     * 自动飞行速度,单位: m/s, 范围: [-15,15]
     */
    private float autoFlightSpeed;

    /**
     * 航点任务列表
     */
    private List<WayPointV2Mission> mission;

    /**
     * 航点任务总数,最大总数 65535
     */
    private short missTotalLen;

    /**
     * 动作列表
     */
    private WaypointV2ActionList actionList;

    @Getter
    @Setter
    public class WayPointV2Mission {

        /**
         * 航点相对于 WayPointV2InitSettings 参考点的经度坐标 ,单位:米
         */
        private double longitude;

        /**
         * 航点相对于 WayPointV2InitSettings 参考点的经度坐标 ,单位:米
         */
        private double latitude;

        /**
         * 相对于起飞高度的相对高度 ,单位:米
         */
        private float relativeHeight;

        /**
         * 航点飞行路径模式 ,定义了飞行器如何到达该航点
         */
        private byte waypointType;

        /**
         * 当前航点的机头方向模式 ,定义了飞行器在当前航点时的机头方向行为
         */
        private byte headingMode;

        /**
         * 当前航点的速度配置 ,包括是否设置局部航点的巡航速度和最大速度
         */
        private WaypointV2Config config;

        /**
         * 减速距离 ,表示飞行器在接近航点时开始减速的距离
         */
        private short dampingDistance;

        /**
         * 如果航路点任务的 headingMode 设置为
         * DJIWaypointV2_DJIWaypointV2HeadingMode_WaypointCustom,飞机航向将在两个不同航向的航路点之间逐渐改变
         * 飞行器到达航点时将要旋转到的机头方向角度 ,范围: [-180, 180] ,0 表示真北方向
         */
        private float heading;

        /**
         * 当 DJIWaypointV2_headingMode 设置为 DJIWaypointV2_DJIWaypointV2HeadingMode_TowardPointOfInterest 时使用
         * 飞行器在执行任务时始终朝向该兴趣点
         */
        private WaypointV2RelativePosition pointOfInterest;

        /**
         * 遥控器油门摇杆推至最大偏转时的飞行速度偏移量,单位:米/秒,范围:[2, 15] 米/秒
         */
        private float maxFlightSpeed;

        /**
         * 飞行器在航点之间移动的基础自动速度,范围:[-15, 15] 米/秒,实际速度是基础自动速度与遥控器油门摇杆提供的速度控制的组合
         */
        private float autoFlightSpeed;

        @Getter
        @Setter
        public class WaypointV2Config {

            /**
             * 0-设置局部航点的巡航速度;1-取消设置局部航点的巡航速度
             */
            private short useLocalCruiseVel;

            /**
             * 0-设置局部航点的最大速度;1-取消设置局部航点的最大速度
             */
            private short useLocalMaxVel;
        }

        @Getter
        @Setter
        public class WaypointV2RelativePosition {

            /**
             * 相对于参考点的X轴距离,北方向为正
             */
            private float positionX;

            /**
             * 相对于参考点的Y轴距离,东方向为正
             */
            private float positionY;

            /**
             * 相对于参考点的Z轴距离,向上为正
             */
            private float positionZ;
        }
    }

    @Getter
    @Setter
    public class WaypointV2ActionList {

        /**
         * 动作列表
         */
        private List<WaypointV2Action> actions;

        /**
         * 列表大小
         */
        private short actionNum;

        @Getter
        @Setter
        public class WaypointV2Action {

            /**
             * 动作ID
             */
            private short actionId;

            /**
             * 动作的触发器
             */
            private WaypointV2Trigger trigger;

            /**
             * 动作执行装置
             */
            private WaypointV2Actuator actuator;

            @Getter
            @Setter
            public class WaypointV2Trigger {
                /**
                 * 触发器类型 ,参见 E_DJIWaypointV2ActionTriggerType
                 */
                private byte actionTriggerType;

                /**
                 * 当 actionTriggerType 为 DJI_WAYPOINT_V2_ACTION_TRIGGER_TYPE_SAMPLE_REACH_POINT(样本到达点触发) 时有效
                 */
                private WaypointV2SampleReachPointTriggerParam sampleReachPointTriggerParam;

                /**
                 * 当 actionTriggerType 为 DJI_WAYPOINT_V2_ACTION_TRIGGER_TYPE_ASSOCIATE(关联触发) 时有效
                 */
                private WaypointV2AssociateTriggerParam associateTriggerParam;

                /**
                 * 当 actionTriggerType 为 DJI_WAYPOINT_V2_ACTION_TRIGGER_TYPE_TRAJECTORY(轨迹触发) 时有效
                 */
                private WaypointV2TrajectoryTriggerParam trajectoryTriggerParam;

                /**
                 * 当 actionTriggerType 为 DJI_WAYPOINT_V2_ACTION_TRIGGER_TYPE_INTERVAL(间隔触发) 时有效
                 */
                private WaypointV2IntervalTriggerParam intervalTriggerParam;

                @Getter
                @Setter
                public class WaypointV2SampleReachPointTriggerParam {
                    /**
                     * 触发动作的航点索引,它决定了动作将在哪个航点被触发
                     */
                    private short waypointIndex;
                    /**
                     * 结束航点数
                     */
                    private short terminateNum;
                }

                @Getter
                @Setter
                public class WaypointV2AssociateTriggerParam {
                    /**
                     * 关联触发器类型
                     */
                    private byte actionAssociatedType;
                    /**
                     * 等待时间单位 ,1-秒;0-毫秒
                     */
                    private byte waitTimeUint;
                    /**
                     * 等待时间
                     */
                    private byte waitingTime;
                    /**
                     * 关联动作ID
                     */
                    private short actionIdAssociated;
                }

                @Getter
                @Setter
                public class WaypointV2TrajectoryTriggerParam {
                    /**
                     * 起始索引 ,表示触发器将在哪个航点开始生效 ,即当飞行器到达由 startIndex 指定的航点时 ,触发器将开始起作用
                     */
                    private short startIndex;

                    /**
                     * 结束索引
                     */
                    private short endIndex;
                }

                @Getter
                @Setter
                public class WaypointV2IntervalTriggerParam {

                    /**
                     * 起始索引 ,表示触发器将在哪个航点开始生效
                     */
                    private short startIndex;

                    /**
                     * 间隔值
                     * 如果 actionIntervalType 是 DJIWaypointV2MissionV2_DJIWaypointV2TriggerAssociatedTimingType_Time
                     * 则 interval表示时间间隔 ,单位为0.01秒
                     * 如果 actionIntervalType 是 DJIWaypointV2MissionV2_DJIWaypointV2TriggerAssociatedTimingType_Distance
                     * 则 interval 表示距离间隔 ,单位为0.01米
                     */
                    private short interval;

                    /**
                     * 间隔触发器的类型 ,参见 E_DJIWaypointV2ActionIntervalType
                     */
                    private byte actionIntervalType;
                }
            }

            @Getter
            @Setter
            public class WaypointV2Actuator {
                /**
                 * 执行装置类型 ,参见 E_DJIWaypointV2ActionActuatorType
                 */
                private byte actuatorType;
                /**
                 * 执行装置索引,当诊断与相机或云台相关且连接的产品有多个云台和相机时有效
                 */
                private byte actuatorIndex;
                /**
                 * 相机执行装置参数 ,当 actuatorType 为 DJI_WAYPOINT_V2_ACTION_ACTUATOR_TYPE_CAMERA 时有效
                 */
                private WaypointV2CameraActuatorParam cameraActuatorParam;
                /**
                 * 云台执行装置参数 ,当 actuatorType 为 DJI_WAYPOINT_V2_ACTION_ACTUATOR_TYPE_GIMBAL
                 */
                private WaypointV2GimbalActuatorParam gimbalActuatorParam;
                /**
                 * 飞行器控制执行装置参数 ,当 actuatorType 为 DJI_WAYPOINT_V2_ACTION_ACTUATOR_TYPE_AIRCRAFT_CONTROL
                 */
                private WaypointV2AircraftControlParam aircraftControlActuatorParam;

                @Getter
                @Setter
                public class WaypointV2CameraActuatorParam {

                    /**
                     * 相机执行装置的操作类型 ,参见 DJIWaypointV2MissionV2_DJIWaypointV2ActionActuatorCameraOperationType
                     */
                    private short operationType;

                    /**
                     * 当 operationType 为 DJIWaypointV2MissionV2_DJIWaypointV2ActionActuatorCameraOperationType_Focus 时有效
                     */
                    WaypointV2CameraFocusParam focusParam;

                    /**
                     * 当 operationType 为 DJIWaypointV2MissionV2_DJIWaypointV2ActionActuatorCameraOperationType_FocalLength 时有效
                     */
                    WaypointV2CameraFocalLengthParam zoomParam;

                    @Getter
                    @Setter
                    public class WaypointV2CameraFocusParam {

                        /**
                         * 镜头聚焦目标点
                         * 当对焦模式为自动时 ,目标点是焦点位置
                         * 当对焦模式为手动时 ,目标点是缩放区域(如果启用了手动模式下的对焦辅助)
                         * x 和 y 的范围是从 0.0 到 1.0。点 [0.0, 0.0] 表示屏幕的左上角
                         */
                        private WaypointV2CGPoint focusTarget;

                        /**
                         * 对焦类型 ,0-点对焦;1-区域对焦(矩形)
                         */
                        private byte regionType;

                        /**
                         * 对焦区域宽度 ,范围[0,1]
                         */
                        private float width;

                        /**
                         * 对焦区域高度 ,范围[0,1]
                         */
                        private float height;
                        /**
                         * 预留字段
                         */
                        private int reserve;
                        /**
                         * 重试次数 ,范围[0,255]
                         */
                        private byte retryTimes;

                        @Getter
                        @Setter
                        public class WaypointV2CGPoint {
                            /**
                             * X轴焦点值,范围:[0,1]
                             */
                            private float x;
                            /**
                             * Y轴焦点值,范围:[0,1]
                             */
                            private float y;
                        }
                    }

                    @Getter
                    @Setter
                    public class WaypointV2CameraFocalLengthParam {
                        /**
                         * 变焦镜头的焦距
                         * 有效范围是 [DJICamera_DJICameraOpticalZoomSpec_minFocalLength, DJICamera_DJICameraOpticalZoomSpec_maxFocalLength]
                         * 必须是 DJICamera_DJICameraOpticalZoomSpec_focalLengthStep 的倍数
                         * 仅支持那些 DJICamera_CameraSettings_isOpticalZoomSupported 返回 TRUE 的相机
                         */
                        private short focalLength;
                        /**
                         * 重试次数
                         */
                        private byte retryTimes;
                    }
                }

                @Getter
                @Setter
                public class WaypointV2GimbalActuatorParam {

                    /**
                     * 云台执行装置的操作类型 ,参见 E_DJIWaypointV2ActionActuatorGimbalOperationType
                     */
                    private short operationType;

                    /**
                     * 云台执行器转动
                     */
                    private GimbalRotation rotation;

                    @Getter
                    @Setter
                    public class GimbalRotation {

                        /**
                         * 云台横滚角（roll angle），单位是0.1度，范围是 [-3600, 3600]，即从 -360 度到 360 度
                         */
                        private short x;
                        /**
                         * 云台俯仰角（pitch angle），单位是0.1度，范围是 [-3600, 3600]，即从 -360 度到 360 度
                         */
                        private short y;
                        /**
                         * 云台偏航角（yaw angle），单位是0.1度，范围是 [-3600, 3600]，即从 -360 度到 360 度
                         */
                        private short z;
                        /**
                         * 控制模式 ,0: 绝对位置控制;1: 相对位置控制
                         */
                        private byte ctrl_mode;
                        /**
                         * 是否忽略横滚命令 ,0-横滚命令正常; 1-忽略横滚命令
                         */
                        private byte rollCmdIgnore;
                        /**
                         * 是否忽略俯仰命令 ,0-俯仰命令正常; 1-忽略俯仰命令
                         */
                        private byte pitchCmdIgnore;
                        /**
                         * 是否忽略偏航命令 ,0-偏航命令正常; 1-忽略偏航命令
                         */
                        private byte yawCmdIgnore;
                        /**
                         * 绝对偏航模式参考 ,0-绝对旋转偏航相对于飞行器; 1-绝对旋转偏航相对于北
                         */
                        private byte absYawModeRef;
                        /**
                         * 保留字段
                         */
                        private byte reserved;
                        /**
                         * 旋转时间,单位: 0.1s ,范围:[1,255]即从 0.1 秒到 25.5 秒
                         */
                        private byte durationTime;
                    }
                }

                @Getter
                @Setter
                public class WaypointV2AircraftControlParam {
                    /**
                     * 飞行控制操作类型 ,参见 DJIWaypointV2MissionV2_DJIWaypointV2ActionActuatorAircraftControlOperationType
                     */
                    private short operationType;
                    /**
                     * 航向旋转参数
                     * 当 operationType 为 DJIWaypointV2MissionV2_DJIWaypointV2ActionActuatorAircraftControlOperationType_RotateYaw 时有效
                     */
                    private WaypointV2AircraftControlRotateHeadingParam yawRotatingParam;
                    /**
                     * 飞行控制参数
                     * 当 operationType 为 DJIWaypointV2MissionV2_DJIWaypointV2ActionActuatorAircraftControlOperationType_FlyingControl
                     * 时有效
                     */
                    private WaypointV2AircraftControlFlyingParam flyControlParam;

                    @Getter
                    @Setter
                    public class WaypointV2AircraftControlRotateHeadingParam {
                        /**
                         * 值为1(TRUE)时,表示无人机应开始飞行; 值为0(FALSE)时,表示无人机应停止飞行
                         */
                        private byte isRelative;
                        /**
                         * 预留字段
                         */
                        private byte reserved;
                        /**
                         * 偏航角
                         */
                        private float yaw;
                    }

                    @Getter
                    @Setter
                    public class WaypointV2AircraftControlFlyingParam {
                        /**
                         * 值为1(TRUE)时,表示无人机应开始飞行; 值为0(FALSE)时,表示无人机应停止飞行
                         */
                        private byte isStartFlying;
                        /**
                         * 预留字段
                         */
                        private byte reserved;
                    }
                }
            }
        }
    }
}
