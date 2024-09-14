package com.mdsd.cloud.utils;

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
    String serverURI;

    @Value("${spring.application.name}")
    String clientId;

    /**
     * 发布消息
     */
    public void publishMessage(MqttClient mqttClient, String topic, MqttMessage message) {

        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            throw new BusinessException("发布消息到主题 %s 失败!".formatted(topic));
        }
    }

    public static void main(String[] args){
        MQClient mqClient = new MQClient();
        mqClient.createClient("STS/subscribe/M350",0);
        while (true){

        }
    }

    /**
     * MQTT - 创建客户端
     *
     * @param subscribeTopic 订阅_主题 例如: "STS/subscribe/M350"
     * @param subscribeQos   订阅_Qos
     */
    public MqttClient createClient(String subscribeTopic, int subscribeQos) {

        MqttClient mqttClient;
        try {
            mqttClient = new MqttClient("tcp://192.168.0.221:1883", "SocketTransferServer", new MemoryPersistence());
            // 设置连接选项
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setUserName("admin");
            connOpts.setPassword("admin@123".getBytes());
            connOpts.setAutomaticReconnect(true);
            // 设置回调函数
            mqttClient.setCallback(new MQClient.MyMqttCallback());
            // 建立连接
            mqttClient.connect(connOpts);
            // 订阅
            mqttClient.subscribe(subscribeTopic, subscribeQos);
        } catch (MqttException e) {
            throw new BusinessException("创建MQTTClient失败!");
        }
        return mqttClient;
    }

    public static class MyMqttCallback implements MqttCallback {

        @Override
        public void disconnected(MqttDisconnectResponse disconnectResponse) {

            log.info("disconnected---------{}", disconnectResponse.getReturnCode());
        }

        @Override
        public void mqttErrorOccurred(MqttException exception) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {

            log.info("<<< Topic: {}, Qos: {}, Retained: {}, message: {}", topic, message.getQos(), message.isRetained(), new String(message.getPayload()));

        }


        @Override
        public void deliveryComplete(IMqttToken token) {

            log.info("deliveryComplete---------{}", token.isComplete());
        }

        /**
         * 当与服务器的连接成功完成时调用
         * Called when the connection to the server is completed successfully.
         *
         * @param reconnect If true, the connection was the result of automatic reconnect.
         * @param serverURI The server URI that the connection was made to.
         */
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {

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

        }
    }
}
