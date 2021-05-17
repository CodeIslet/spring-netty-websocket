package me.codeislet.springboot.netty.websocket.service.exception;

import lombok.extern.slf4j.Slf4j;
import me.codeislet.springboot.netty.websocket.server.ResponseEntity;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketControllerAdvice;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketExceptionHandler;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Slf4j
@WebSocketControllerAdvice
public class WebSocketExceptionAdvice {

    @WebSocketExceptionHandler(throwables = { GroupProcessingException.class })
    public ResponseEntity groupProcessingExceptionHandler(GroupProcessingException e) {
        log.error("[GroupProcessingException] error: ", e);
        return new ResponseEntity(ResponseEntity.ResponseStatus.ERROR, e.getMessage());
    }

    @WebSocketExceptionHandler(throwables = { RegisterProcessingException.class })
    public ResponseEntity registerProcessingExceptionHandler(RegisterProcessingException e) {
        log.error("[RegisterProcessingException] error: ", e);
        return new ResponseEntity(ResponseEntity.ResponseStatus.ERROR, e.getMessage());
    }
}