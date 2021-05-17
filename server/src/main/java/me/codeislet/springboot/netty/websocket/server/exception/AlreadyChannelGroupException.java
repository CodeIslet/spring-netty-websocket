package me.codeislet.springboot.netty.websocket.server.exception;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public class AlreadyChannelGroupException extends Exception {

    private static final long serialVersionUID = 2403271765020157434L;

    public AlreadyChannelGroupException(String message) {
        super(message);
    }
}