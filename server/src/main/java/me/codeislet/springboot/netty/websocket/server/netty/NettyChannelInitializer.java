package me.codeislet.springboot.netty.websocket.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Component
@RequiredArgsConstructor
public class NettyChannelInitializer extends ChannelInitializer<Channel> {

    private final WebSocketInboundHandler webSocketInboundHandler;
    private final NettyProperties nettyProperties;


    @Override
    protected void initChannel(Channel channel) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline
                .addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(nettyProperties.getMaxContentLength()))
                .addLast(new WebSocketServerCompressionHandler())
                .addLast(new WebSocketServerProtocolHandler(nettyProperties.getSocketPath(), null, true))
                .addLast(webSocketInboundHandler);
    }
}