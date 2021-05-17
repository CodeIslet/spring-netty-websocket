package me.codeislet.springboot.netty.websocket.server.pubsub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MessageFrame {

    private ChannelType channelType;
    private String destination;
    private String message;
    private Object data;

    public enum ChannelType {
        GROUP, SINGLE
    }
}