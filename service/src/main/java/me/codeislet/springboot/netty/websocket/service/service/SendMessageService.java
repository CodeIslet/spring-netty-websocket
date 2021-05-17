package me.codeislet.springboot.netty.websocket.service.service;

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
public class SendMessageService {

    private final WebSocketTemplate webSocketTemplate;


    public void sendToUser(String token, String message, Object data) {
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("token string can not be an empty value.");
        }
        webSocketTemplate.send(token, message, data);
    }

    public void sendToGroup(String groupName, String message, Object data) {
        if (StringUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("group name can not be an empty value.");
        }
        webSocketTemplate.sendGroup(groupName, message, data);
    }
}