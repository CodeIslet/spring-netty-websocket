package me.codeislet.springboot.netty.websocket.service.exception;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public class RegisterProcessingException extends Exception {

    private static final long serialVersionUID = -3406415315932824090L;

    public RegisterProcessingException(String message) {
        super(message);
    }
}