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
public class GroupService {

    private final WebSocketTemplate webSocketTemplate;


    public boolean join(String groupName, Channel channel) {
        if (StringUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("group name can not be an empty value.");
        }
        if (channel == null) {
            throw new IllegalArgumentException("channel can not be a null.");
        }
        return webSocketTemplate.joinGroup(groupName, channel);
    }

    public boolean leave(String groupName, Channel channel) {
        if (StringUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("group name can not be an empty value.");
        }
        if (channel == null) {
            throw new IllegalArgumentException("channel can not be a null.");
        }
        return webSocketTemplate.leaveGroup(groupName, channel);
    }
}