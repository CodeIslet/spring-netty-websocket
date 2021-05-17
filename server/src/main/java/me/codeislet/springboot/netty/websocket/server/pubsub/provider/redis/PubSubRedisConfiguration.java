package me.codeislet.springboot.netty.websocket.server.pubsub.provider.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.pubsub.StatefulRedisPubSubConnection;
import com.lambdaworks.redis.resource.ClientResources;
import com.lambdaworks.redis.resource.DefaultClientResources;
import lombok.RequiredArgsConstructor;
import me.codeislet.springboot.netty.websocket.server.pubsub.provider.PubSubProviderProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Configuration
@RequiredArgsConstructor
public class PubSubRedisConfiguration {

    private final PubSubProviderProperties pubSubProviderProperties;

    @Bean(destroyMethod = "shutdown")
    ClientResources pubSubRedisClientResource() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    RedisClient pubSubRedisClient(ClientResources pubSubRedisClientResource) {
        return RedisClient.create(
                pubSubRedisClientResource,
                RedisURI.create(pubSubProviderProperties.getHost(), pubSubProviderProperties.getPort())
        );
    }

    @Bean(destroyMethod = "close")
    StatefulRedisPubSubConnection<String, String> pubRedisConnection(@Qualifier("pubSubRedisClient") RedisClient client) {
        return client.connectPubSub();
    }

    @Bean(destroyMethod = "close")
    StatefulRedisPubSubConnection<String, String> subRedisConnection(@Qualifier("pubSubRedisClient") RedisClient client) {
        return client.connectPubSub();
    }
}