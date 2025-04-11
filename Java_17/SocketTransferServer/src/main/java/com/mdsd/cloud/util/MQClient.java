package com.mdsd.cloud.util;

import com.mdsd.cloud.response.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author WangYunwei [2024-09-10]
 */
@Slf4j
@Component
public class MQClient {

    @Value("tcp://${env.ip.sts}:${env.port.sts.mqtt}")
    private String serverURI;

    @Value("${spring.application.name}")
    private String clientId;

    public static final String taskTopic = "STS/%s/PUBLISH/%s";

    public static final String userTopic = "STS_PUBLISH/USER";

    public static final String userContent = "{\"userId\":\"%s\",\"isConnect\":%b}";

    private static MqttClient mqClient;

    /**
     * 发布
     */
    public static void publish(String topic, byte[] payload, int qos, boolean retained) {
        try {
            mqClient.publish(topic, payload, qos, retained);
        } catch (MqttException e) {
            throw new BusinessException("发布消息到主题 %s 失败!".formatted(topic));
        }
    }

    /**
     * 订阅
     */
    public static void subscribe(String topicFilter, int qos) {
        try {
            mqClient.subscribe(topicFilter, qos);
        } catch (MqttException e) {
            throw new BusinessException("订阅主题 %s 失败!".formatted(topicFilter));
        }
    }

    /**
     * 单层通配符: STS/M350/PUBLISH/+
     * STS/M350/PUBLISH/+ 将匹配 TS/M350/PUBLISH/x26123 和 TS/M350/PUBLISH/x26127，但不会匹配 TS/M350/PUBLISH/x26123/subtopic
     * 多层通配符: STS/M350/PUBLISH/#
     * TS/M350/PUBLISH/# 将匹配 TS/M350/PUBLISH/x26123、TS/M350/PUBLISH/x26127 和 TS/M350/PUBLISH/x26123/subtopic
     */
    public static void main(String[] args) {
        try {
            mqClient = new MqttClient("tcp://192.168.0.221:1883", "STS_MAIN", new MemoryPersistence());
            // 设置连接选项
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setUserName("mdsd");
            connOpts.setPassword("mdsd@123".getBytes());
            connOpts.setAutomaticReconnect(true);
            // 设置回调函数
            mqClient.setCallback(new MqttCallbackImpl());
            // 建立连接
            mqClient.connect(connOpts);
            // 订阅
            subscribe(userTopic, 0);
            // 发布
            publish(userTopic, String.format(userContent, "1850816877849214976", Boolean.FALSE).getBytes(), 1, false);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

    }

    public void createMqClient() {
        try {
            mqClient = new MqttClient(serverURI, clientId, new MemoryPersistence());
            // 设置连接选项
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setUserName("mdsd");
            connOpts.setPassword("mdsd@123".getBytes());
            connOpts.setAutomaticReconnect(true);
            // 设置回调函数
            mqClient.setCallback(new MqttCallbackImpl());
            // 建立连接
            mqClient.connect(connOpts);
        } catch (MqttException e) {
            throw new BusinessException("创建MQTTClient失败!");
        }
    }

    static class MqttCallbackImpl implements MqttCallback {
        /**
         * 当与服务器的连接成功完成时调用
         * Called when the connection to the server is completed successfully.
         *
         * @param reconnect If true, the connection was the result of automatic reconnect.
         * @param serverURI The server URI that the connection was made to.
         */
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            log.info("connectComplete --------- {}", serverURI);
        }

        @Override
        public void disconnected(MqttDisconnectResponse disconnectResponse) {
            log.info("disconnected --------- {}", disconnectResponse.getReturnCode());
        }

        @Override
        public void mqttErrorOccurred(MqttException exception) {
            log.info("mqttErrorOccurred --------- {}", exception.getMessage());
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
//            log.info(">>> Topic: {}, Qos: {}, Retained: {}, message: {}", topic, message.getQos(), message.isRetained(), new String(message.getPayload()));
        }

        @Override
        public void deliveryComplete(IMqttToken token) {
//            log.info("deliveryComplete --------- {}", token.isComplete());
        }

        /**
         * 当客户端接收到 AUTH 包时调用
         * Called when an AUTH packet is received by the client.
         *
         * @param reasonCode The Reason code, can be Success (0), Continue authentication (24)
         *                   or Re-authenticate (25).
         * @param properties The {@link MqttProperties} to be sent, containing the
         *                   Authentication Method, Authentication Data and any required User
         *                   Defined Properties.
         */
        @Override
        public void authPacketArrived(int reasonCode, MqttProperties properties) {
            log.info("authPacketArrived --------- {}", reasonCode);
        }
    }
}
