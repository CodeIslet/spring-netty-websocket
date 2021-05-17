package me.codeislet.springboot.netty.websocket.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public class SendMessage {

    private SendMessage() {}

    @Getter
    public static class Request {
        private String destination;
        private String message;
        private Object data;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final String destination;
        private final LocalDateTime sentAt;
    }
}