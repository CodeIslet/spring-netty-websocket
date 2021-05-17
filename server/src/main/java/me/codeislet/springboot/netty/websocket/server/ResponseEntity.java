package me.codeislet.springboot.netty.websocket.server;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Getter
@NoArgsConstructor
public class ResponseEntity {

    private ResponseStatus status;
    private String identifier;
    private String message;
    private Object body;
    private LocalDateTime time;

    public enum ResponseStatus {
        OK, ERROR, BAD_REQUEST
    }

    public ResponseEntity(ResponseStatus status, String identifier, String message, Object body) {
        this.status = status;
        this.identifier = identifier;
        this.message = message;
        this.body = body;
        this.time = LocalDateTime.now();
    }

    public ResponseEntity(String identifier, String message, Object body) {
        this(ResponseStatus.OK, identifier, message, body);
    }

    public ResponseEntity(ResponseStatus status, String message) {
        this(status, null, message, null);
    }

    public ResponseEntity(ResponseStatus status) {
        this(status, null, null, null);
    }
}