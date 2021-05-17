package me.codeislet.springboot.netty.websocket.server.netty;

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
@ConfigurationProperties(prefix = "websocket.netty")
public class NettyProperties {

    private String socketPath;
    private Integer maxContentLength;
    private Integer port;
    private Integer bossThread;
    private Integer workerThread;

    public String getSocketPath() {
        return Objects.requireNonNullElse(socketPath, "");
    }

    public Integer getMaxContentLength() {
        return Objects.requireNonNullElse(maxContentLength, 65536);
    }

    public Integer getPort() {
        return Objects.requireNonNullElse(port, 8090);
    }

    public Integer getBossThread() {
        return Objects.requireNonNullElse(bossThread, 1);
    }

    public Integer getWorkerThread() {
        return Objects.requireNonNullElse(workerThread, 2);
    }
}
