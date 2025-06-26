package com.mdsd.cloud.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.mdsd.cloud.enums.CommonEnum;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author WangYunwei [2024-07-10]
 */
@Getter
public class CommonEvent extends ApplicationEvent {

    private final CommonEnum source;

    private JsonNode jsonNode;

    private ByteBuf byteBuf;

    private DjiProtoBuf.Payload payload;

    public CommonEvent(CommonEnum source, JsonNode jsonNode) {
        super(source);
        this.source = source;
        this.jsonNode = jsonNode;
    }

    public CommonEvent(CommonEnum source, ByteBuf byteBuf) {
        super(source);
        this.source = source;
        this.byteBuf = byteBuf;
    }

    public CommonEvent(CommonEnum source, DjiProtoBuf.Payload payload) {
        super(source);
        this.source = source;
        this.payload = payload;
    }
}
