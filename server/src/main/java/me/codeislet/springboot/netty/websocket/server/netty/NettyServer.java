package me.codeislet.springboot.netty.websocket.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServer {

    private final ServerBootstrap serverBootstrap;
    private final InetSocketAddress nettyTcpPort;
    private Channel channel;

    public void start() throws InterruptedException {
        log.info("[NettyServer:start] start server. (bind: {})", nettyTcpPort);
        channel = serverBootstrap.bind(nettyTcpPort).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() {
        log.info("[NettyServer:stop] stop server.");
        channel.close();
        channel.parent().close();
    }
}