package me.codeislet.springboot.netty.websocket.service.controller.http;

import lombok.RequiredArgsConstructor;
import me.codeislet.springboot.netty.websocket.service.dto.SendMessage;
import me.codeislet.springboot.netty.websocket.service.service.SendMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class SendMessageController {

    private final SendMessageService sendMessageService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public SendMessage.Response send(@RequestBody SendMessage.Request req) {
        sendMessageService.sendToUser(req.getDestination(), req.getMessage(), req.getData());
        return new SendMessage.Response(req.getDestination(), LocalDateTime.now());
    }

    @PostMapping("/group")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public SendMessage.Response sendToGroup(@RequestBody SendMessage.Request req) {
        sendMessageService.sendToGroup(req.getDestination(), req.getMessage(), req.getData());
        return new SendMessage.Response(req.getDestination(), LocalDateTime.now());
    }
}