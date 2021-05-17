package me.codeislet.springboot.netty.websocket.service.controller.websocket;

import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import me.codeislet.springboot.netty.websocket.server.ResponseEntity;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketController;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketRequestMapping;
import me.codeislet.springboot.netty.websocket.service.dto.Group;
import me.codeislet.springboot.netty.websocket.service.exception.GroupProcessingException;
import me.codeislet.springboot.netty.websocket.service.service.GroupService;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@WebSocketController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @WebSocketRequestMapping(value = "join")
    public void join(Group.Request req, ChannelHandlerContext ctx, CompletableFuture<ResponseEntity> future) {
        boolean joinSuccessful = groupService.join(req.getGroupName(), ctx.channel());
        if (!joinSuccessful) {
            future.completeExceptionally(new GroupProcessingException("join to group failure."));
        }
        Group.Response res = new Group.Response(LocalDateTime.now(), "Hi!");
        future.complete(new ResponseEntity(req.getGroupName(), "Done", res));
    }

    @WebSocketRequestMapping(value = "leave")
    public void leave(Group.Request req, ChannelHandlerContext ctx, CompletableFuture<ResponseEntity> future) {
        boolean leaveSuccessful = groupService.leave(req.getGroupName(), ctx.channel());
        if (!leaveSuccessful) {
            future.completeExceptionally(new GroupProcessingException("leave group failure."));
        }
        Group.Response res = new Group.Response(LocalDateTime.now(), "Bye!");
        future.complete(new ResponseEntity(req.getGroupName(), "Done", res));
    }
}