package me.codeislet.springboot.netty.websocket.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public class Group {

    private Group() {}

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Request {
        private String groupName;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final LocalDateTime joinedAt;
        private final String message;
    }
}