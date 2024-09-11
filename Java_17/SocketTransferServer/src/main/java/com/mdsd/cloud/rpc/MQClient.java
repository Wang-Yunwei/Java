package com.mdsd.cloud.rpc;

import com.mdsd.cloud.response.BusinessException;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author WangYunwei [2024-09-10]
 */
public class MQClient {

    @Value("tcp://${env.ip.sts}")
    String serverURI;

    @Value("${spring.application.name}")
    String clientId;

    /**
     * 发布消息
     */
    public void publishMessage(MqttClient mqttClient, String topic, int qos, MqttMessage message) {

        MqttMessage mqttMessage = new MqttMessage("Hello World".getBytes());
        mqttMessage.setQos(qos);
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            throw new BusinessException("发布消息到主题 %s 失败!".formatted(topic));
        }
    }

    /**
     * MQTT - 创建客户端
     *
     * @param subscribeTopic 订阅_主题
     * @param subscribeQos   订阅_Qos
     * @throws MqttException
     */
    public MqttClient created(String subscribeTopic, int subscribeQos) {

        MqttClient mqttClient;
        try {
            mqttClient = new MqttClient(serverURI, clientId, new MemoryPersistence());
            // 连接选项
            MqttConnectionOptions mqttConnectionOptions = new MqttConnectionOptions();
            mqttConnectionOptions.setUserName("admin");
            mqttConnectionOptions.setPassword("admin@123".getBytes());
            // 设置回调函数
            mqttClient.setCallback(new MQClient.MyCallback());
            // 建立连接
            mqttClient.connect(mqttConnectionOptions);
            // 订阅
            mqttClient.subscribe(subscribeTopic, subscribeQos);
        } catch (MqttException e) {
            throw new BusinessException("创建MQTTClient失败!");
        }
        return mqttClient;
    }

    public static class MyCallback implements MqttCallback {
        /**
         * This method is called when the server gracefully disconnects from the client
         * by sending a disconnect packet, or when the TCP connection is lost due to a
         * network issue or if the client encounters an error.
         *
         * @param disconnectResponse a {@link MqttDisconnectResponse} containing relevant properties
         *                           related to the cause of the disconnection.
         */
        @Override
        public void disconnected(MqttDisconnectResponse disconnectResponse) {
            System.out.println("disconnected---------");
        }

        /**
         * This method is called when an exception is thrown within the MQTT client. The
         * reasons for this may vary, from malformed packets, to protocol errors or even
         * bugs within the MQTT client itself. This callback surfaces those errors to
         * the application so that it may decide how best to deal with them.
         * <p>
         * For example, The MQTT server may have sent a publish message with an invalid
         * topic alias, the MQTTv5 specification suggests that the client should
         * disconnect from the broker with the appropriate return code, however this is
         * completely up to the application itself.
         *
         * @param exception - The exception thrown causing the error.
         */
        @Override
        public void mqttErrorOccurred(MqttException exception) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            // subscribe后得到的消息会执行到这里面
            System.out.println("接收消息主题:" + topic);
            System.out.println("接收消息Qos:" + message.getQos());
            System.out.println("接收消息内容:" + new String(message.getPayload()));
        }


        @Override
        public void deliveryComplete(IMqttToken token) {
            System.out.println("deliveryComplete---------" + token.isComplete());
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
         * 当客户端接收到AUTH包时调用
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
