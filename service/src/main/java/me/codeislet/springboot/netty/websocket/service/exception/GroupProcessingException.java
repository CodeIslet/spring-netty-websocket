package me.codeislet.springboot.netty.websocket.service.exception;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public class GroupProcessingException extends Exception {

    private static final long serialVersionUID = -4331656765002288772L;

    public GroupProcessingException(String message) {
        super(message);
    }
}