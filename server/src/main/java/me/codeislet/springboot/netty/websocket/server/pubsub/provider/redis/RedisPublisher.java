package me.codeislet.springboot.netty.websocket.server.pubsub.provider.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;
import lombok.RequiredArgsConstructor;
import me.codeislet.springboot.netty.websocket.server.pubsub.MessageFrame;
import me.codeislet.springboot.netty.websocket.server.pubsub.Publisher;
import me.codeislet.springboot.netty.websocket.server.pubsub.provider.PubSubProviderProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Component
@RequiredArgsConstructor
public class RedisPublisher implements Publisher {

    private final StatefulRedisPubSubConnection<String, String> pubRedisConnection;
    private final ObjectMapper objectMapper;
    private final PubSubProviderProperties pubSubProviderProperties;


    @Override
    public CompletableFuture<Long> publish(MessageFrame messageFrame) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        try {
            future = pubRedisConnection.async()
                    .publish(pubSubProviderProperties.getChannel(), objectMapper.writeValueAsString(messageFrame))
                    .toCompletableFuture();
        } catch (JsonProcessingException e) {
            future.completeExceptionally(e);
        }
        return future;
    }
}