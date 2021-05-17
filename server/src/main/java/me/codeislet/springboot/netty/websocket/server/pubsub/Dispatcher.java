package me.codeislet.springboot.netty.websocket.server.pubsub;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public interface Dispatcher {

    void dispatch(MessageFrame messageFrame);
}