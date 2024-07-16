package com.mdsd.cloud.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author WangYunwei [2024-07-10]
 */
public class SocketEvent<T> extends ApplicationEvent {
    private T msg;

    public SocketEvent(Object source, T msg) {

        super(source);
        this.msg = msg;
    }

    public T getMsg() {

        return msg;
    }
}
