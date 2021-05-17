package me.codeislet.springboot.netty.websocket.server.annotation;

import java.lang.annotation.*;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSocketRequestMapping {

    String value();
}