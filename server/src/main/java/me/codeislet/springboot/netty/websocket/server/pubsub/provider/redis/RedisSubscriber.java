package me.codeislet.springboot.netty.websocket.server.pubsub.provider.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaworks.redis.pubsub.RedisPubSubAdapter;
import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.codeislet.springboot.netty.websocket.server.pubsub.Dispatcher;
import me.codeislet.springboot.netty.websocket.server.pubsub.MessageFrame;
import me.codeislet.springboot.netty.websocket.server.pubsub.Subscriber;
import me.codeislet.springboot.netty.websocket.server.pubsub.provider.PubSubProviderProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements Subscriber {

    private final StatefulRedisPubSubConnection<String, String> subRedisConnection;
    private final Dispatcher dispatcher;
    private final ObjectMapper objectMapper;
    private final PubSubProviderProperties pubSubProviderProperties;


    @Override
    @PostConstruct
    public void subscribe() {
        subRedisConnection.addListener(new RedisPubSubAdapter<>() {
            @Override
            public void message(String channel, String message) {
                try {
                    dispatcher.dispatch(objectMapper.readValue(message, MessageFrame.class));
                } catch (IOException e) {
                    log.error("[RedisSubscriber:subscribe] error: ", e);
                }
            }
        });
        subRedisConnection.async().subscribe(pubSubProviderProperties.getChannel());
    }
}