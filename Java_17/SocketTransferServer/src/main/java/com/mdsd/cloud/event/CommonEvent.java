package com.mdsd.cloud.event;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author WangYunwei [2024-07-10]
 */
@Getter
public class CommonEvent extends ApplicationEvent {

    private Map<String, String> map;

    private ByteBuf byteBuf;

    public CommonEvent(Object source, Map<String, String> map) {

        super(source);
        this.map = map;
    }

    public CommonEvent(Object source, ByteBuf byteBuf) {

        super(source);
        this.byteBuf = byteBuf;
    }

}
