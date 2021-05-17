package me.codeislet.springboot.netty.websocket.server.exception;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public class WebSocketProcessingException extends Exception {

    private static final long serialVersionUID = 8798906312597426184L;

    public WebSocketProcessingException(String message) {
        super(message);
    }
}