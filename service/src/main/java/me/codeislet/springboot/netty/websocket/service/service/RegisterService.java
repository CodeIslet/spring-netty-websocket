package me.codeislet.springboot.netty.websocket.service.service;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import me.codeislet.springboot.netty.websocket.server.WebSocketTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final WebSocketTemplate webSocketTemplate;


    public Channel register(String token, Channel channel) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("user token can not be an empty value.");
        }
        if (channel == null) {
            throw new IllegalArgumentException("user channel can not be a null.");
        }
        return webSocketTemplate.join(token, channel);
    }

    public Channel unregister(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("user channel can not be a null.");
        }
        return webSocketTemplate.leave(channel);
    }
}