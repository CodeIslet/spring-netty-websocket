package me.codeislet.springboot.netty.websocket.server.invoker;

import io.netty.channel.ChannelHandlerContext;
import me.codeislet.springboot.netty.websocket.server.RequestEntity;
import me.codeislet.springboot.netty.websocket.server.ResponseEntity;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketController;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketRequestMapping;
import me.codeislet.springboot.netty.websocket.server.exception.WebSocketProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Component
public class WebSocketControllerInvoker {

    private final List<Object> controllerBeans;
    private final ModelMapper modelMapper;

    public WebSocketControllerInvoker(ApplicationContext context, ModelMapper modelMapper) {
        this.controllerBeans = new ArrayList<>(context.getBeansWithAnnotation(WebSocketController.class).values());
        this.modelMapper = modelMapper;
    }

    private Method getController(Object bean, String name) {
        return Arrays.stream(bean.getClass().getDeclaredMethods())
                .filter(m -> StringUtils.equalsIgnoreCase(name, m.getAnnotation(WebSocketRequestMapping.class).value()))
                .findFirst().orElse(null);
    }

    public CompletableFuture<ResponseEntity> invoke(RequestEntity req, ChannelHandlerContext ctx,
                                                    CompletableFuture<ResponseEntity> future) {
        Method controller = null;
        for (Object bean : controllerBeans) {
            controller = getController(bean, req.getMapper());
            if (controller != null) {
                try {
                    Parameter[] parameters = controller.getParameters();
                    if (parameters != null) {
                        Parameter parameter = parameters[0];
                        if (!StringUtils.contains(parameter.getType().getName(), "ChannelHandlerContext")) {
                            Object arg = modelMapper.map(req.getBody(), parameter.getType());
                            if (arg == null) {
                                throw new NullPointerException("parameter can not be a null.");
                            }
                            controller.invoke(bean, arg, ctx, future);
                        }
                    } else {
                        controller.invoke(bean, ctx, future);
                    }
                } catch (Exception e) {
                    future.completeExceptionally(e);
                }
                break;
            }
        }
        if (controller == null) {
            future.completeExceptionally(new WebSocketProcessingException("websocket controller not found."));
        }
        return future;
    }
}