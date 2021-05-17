package me.codeislet.springboot.netty.websocket.server.pubsub;

import java.util.concurrent.CompletableFuture;


/**
 * @author Code Islet
 * @since 0.1.0
 */
public interface Publisher {

    CompletableFuture<Long> publish(MessageFrame messageFrame);
}