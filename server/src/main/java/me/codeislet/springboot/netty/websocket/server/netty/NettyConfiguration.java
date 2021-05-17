package me.codeislet.springboot.netty.websocket.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.InetSocketAddress;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Configuration
@RequiredArgsConstructor
public class NettyConfiguration {

    private final NettyProperties nettyProperties;

    @Bean
    InetSocketAddress nettyTcpPort() {
        return new InetSocketAddress(nettyProperties.getPort());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup(nettyProperties.getBossThread());
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(nettyProperties.getWorkerThread());
    }

    @Bean
    public ServerBootstrap serverBootstrap(NettyChannelInitializer nettyChannelInitializer,
                                           NioEventLoopGroup bossGroup,
                                           NioEventLoopGroup workerGroup) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        return bootstrap
                .group(bossGroup, workerGroup)
                .childHandler(nettyChannelInitializer)
                .channel(NioServerSocketChannel.class);
    }
}
