package me.codeislet.springboot.netty.websocket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.codeislet.springboot.netty.websocket.server.netty.ChannelGroupManager;
import me.codeislet.springboot.netty.websocket.server.netty.ChannelManager;
import me.codeislet.springboot.netty.websocket.server.pubsub.Dispatcher;
import me.codeislet.springboot.netty.websocket.server.pubsub.MessageFrame;
import org.springframework.stereotype.Component;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketDispatcher implements Dispatcher {

    private final ChannelGroupManager channelGroupManager;
    private final ChannelManager channelManager;
    private final ObjectMapper objectMapper;


    @Override
    public void dispatch(MessageFrame messageFrame) {
        switch (messageFrame.getChannelType()) {
            case GROUP:
                group(messageFrame);
                break;
            case SINGLE:
                single(messageFrame);
                break;
            default:
                throw new IllegalArgumentException("invalid channel type.");
        }
    }

    private void group(MessageFrame frame) {
        try {
            ChannelGroup group = channelGroupManager.getOrCreate(frame.getDestination());
            ResponseEntity response = new ResponseEntity(frame.getDestination(), frame.getMessage(), frame.getData());
            group.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(response)));
        } catch (JsonProcessingException e) {
            log.error("[WebSocketDispatcher:group] error: ", e);
        }
    }

    private void single(MessageFrame frame) {
        try {
            Channel channel = channelManager.get(frame.getDestination());
            ResponseEntity response = new ResponseEntity(frame.getDestination(), frame.getMessage(), frame.getData());
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(response)));
        } catch (JsonProcessingException e) {
            log.error("[WebSocketDispatcher:single] error: ", e);
        }
    }
}