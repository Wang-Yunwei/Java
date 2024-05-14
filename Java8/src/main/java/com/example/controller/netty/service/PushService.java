package com.example.controller.netty.service;

/**
 * @author WangYunwei [2021-12-20]
 */
public interface PushService {

    /**
     * 推送给指定用户
     */
    void pushMsgToOne(String userId, String msg);

    /**
     * 推送给所有用户
     */
    void pushMsgToAll(String msg);

}
