package me.codeislet.springboot.netty.websocket.service.controller.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import me.codeislet.springboot.netty.websocket.server.ResponseEntity;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketController;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketRequestMapping;
import me.codeislet.springboot.netty.websocket.service.dto.Register;
import me.codeislet.springboot.netty.websocket.service.exception.RegisterProcessingException;
import me.codeislet.springboot.netty.websocket.service.service.RegisterService;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@WebSocketController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;


    @WebSocketRequestMapping(value = "register")
    public void register(Register.Request req, ChannelHandlerContext ctx, CompletableFuture<ResponseEntity> future) {
        Channel channel = registerService.register(req.getToken(), ctx.channel());
        if (channel == null) {
            future.completeExceptionally(new RegisterProcessingException("user register failure."));
        }
        Register.Response res = new Register.Response(LocalDateTime.now(), "Hi!");
        future.complete(new ResponseEntity(req.getToken(), "agent: " + req.getAgent(), res));
    }

    @WebSocketRequestMapping(value = "unregister")
    public void unregister(ChannelHandlerContext ctx, CompletableFuture<ResponseEntity> future) {
        registerService.unregister(ctx.channel());
        future.complete(new ResponseEntity(ResponseEntity.ResponseStatus.OK));
    }
}