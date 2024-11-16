package com.mdsd.cloud.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdsd.cloud.enums.ServerEnum;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @author WangYunwei [2024-07-10]
 */
@Getter
public class CommonEvent extends ApplicationEvent {

    private final ServerEnum source;

    private JsonNode jsonNode;

    private ByteBuf byteBuf;

    public CommonEvent(ServerEnum source, JsonNode jsonNode) {
        super(source);
        this.source = source;
        this.jsonNode = jsonNode;
    }

    public CommonEvent(ServerEnum source, ByteBuf byteBuf) {
        super(source);
        this.source = source;
        this.byteBuf = byteBuf;
    }

}
