package me.codeislet.springboot.netty.websocket.server.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.codeislet.springboot.netty.websocket.server.RequestEntity;
import me.codeislet.springboot.netty.websocket.server.ResponseEntity;
import me.codeislet.springboot.netty.websocket.server.exception.WebSocketProcessingException;
import me.codeislet.springboot.netty.websocket.server.invoker.WebSocketAdviceInvoker;
import me.codeislet.springboot.netty.websocket.server.invoker.WebSocketControllerInvoker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Slf4j
@Component("webSocketInboundHandler")
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class WebSocketInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final WebSocketControllerInvoker webSocketControllerInvoker;
    private final WebSocketAdviceInvoker webSocketAdviceInvoker;
    private final ChannelGroupManager channelGroupManager;
    private final ChannelManager channelManager;
    private final ObjectMapper objectMapper;


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        channelManager.remove(ctx.channel());
        channelGroupManager.removeChannelInAllGroups(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        CompletableFuture<ResponseEntity> future = new CompletableFuture<>();
        RequestEntity req = objectMapper.readValue(frame.retain().text(), RequestEntity.class);
        if (StringUtils.isEmpty(req.getMapper())) {
            future.completeExceptionally(new WebSocketProcessingException("invalid request mapper name."));
        }
        webSocketControllerInvoker.invoke(req, ctx, future);
        future.exceptionally((Throwable t) -> {
            try {
                Object except = webSocketAdviceInvoker.invoke(t);
                sendResponse(ctx.channel(), (ResponseEntity) except);
            } catch (InvocationTargetException | IllegalAccessException e) {
                log.error("[WebSocketInboundHandler:channelRead0] error: ", e);
            }
            return null;
        });
        future.thenAccept(response -> sendResponse(ctx.channel(), response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        sendResponse(ctx.channel(), new ResponseEntity(ResponseEntity.ResponseStatus.ERROR, "server error."));
    }

    private void sendResponse(Channel channel, ResponseEntity response) {
        try {
            channel.writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(response)));
        } catch (JsonProcessingException e) {
            log.error("[WebSocketInboundHandler:sendResponse] error: " , e);
        }
    }
}
