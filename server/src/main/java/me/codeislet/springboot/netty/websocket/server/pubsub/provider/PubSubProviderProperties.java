package me.codeislet.springboot.netty.websocket.server.pubsub.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Setter
@Configuration
@ConfigurationProperties(prefix = "websocket.pubsub")
public class PubSubProviderProperties {

    private String provider;
    private String host;
    private Integer port;
    private String channel;

    public String getProvider() {
        return Objects.requireNonNullElse(provider, "local");
    }

    public String getHost() {
        return Objects.requireNonNullElse(host, "localhost");
    }

    public Integer getPort() {
        return port;
    }

    public String getChannel() {
        return Objects.requireNonNullElse(channel, "");
    }
}
