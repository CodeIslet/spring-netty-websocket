package me.codeislet.springboot.netty.websocket.server;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.codeislet.springboot.netty.websocket.server.netty.ChannelGroupManager;
import me.codeislet.springboot.netty.websocket.server.netty.ChannelManager;
import me.codeislet.springboot.netty.websocket.server.pubsub.MessageFrame;
import me.codeislet.springboot.netty.websocket.server.pubsub.Publisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketTemplate {

    private final ChannelGroupManager channelGroupManager;
    private final ChannelManager channelManager;
    private final Publisher publisher;


    private void send(MessageFrame frame) {
        CompletableFuture<Long> future = publisher.publish(frame);
        future.exceptionally((Throwable t) -> {
            log.error("[WebSocketTemplate:send] error: ", t);
            return null;
        });
    }

    public void send(String dest, String message, Object data) {
        send(new MessageFrame(MessageFrame.ChannelType.SINGLE, dest, message, data));
    }

    public Channel join(String name, Channel channel) {
        return channelManager.add(name, channel);
    }

    public Channel leave(Channel channel) {
        return channelManager.remove(channel);
    }

    public void sendGroup(String name, String message, Object data) {
        send(new MessageFrame(MessageFrame.ChannelType.GROUP, name, message, data));
    }

    public boolean joinGroup(String name, Channel channel) {
        return channelGroupManager.getOrCreate(name).add(channel);
    }

    public boolean leaveGroup(String name, Channel channel) {
        return channelGroupManager.removeChannelInGroup(name, channel);
    }
}