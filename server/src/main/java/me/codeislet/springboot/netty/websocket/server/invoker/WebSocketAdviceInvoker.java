package me.codeislet.springboot.netty.websocket.server.invoker;

import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketControllerAdvice;
import me.codeislet.springboot.netty.websocket.server.annotation.WebSocketExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Component
public class WebSocketAdviceInvoker {

    private final List<Object> adviceBeans;

    public WebSocketAdviceInvoker(ApplicationContext context) {
        adviceBeans = new ArrayList<>(context.getBeansWithAnnotation(WebSocketControllerAdvice.class).values());
    }

    private Method getAdvisor(Object bean, Throwable throwable) {
        if (bean == null || throwable == null) {
            return null;
        }
        for (Method method : bean.getClass().getDeclaredMethods()) {
            WebSocketExceptionHandler handler = method.getAnnotation(WebSocketExceptionHandler.class);
            Class<? extends Throwable>[] clazz = handler.throwables();
            for (Class<? extends Throwable> cls : clazz) {
                if (StringUtils.equals(throwable.getClass().getName(), cls.getName())) {
                    return method;
                }
            }
        }
        return null;
    }

    public Object invoke(Throwable throwable) throws InvocationTargetException, IllegalAccessException {
        if (throwable == null) {
            return null;
        }
        for (Object bean : adviceBeans) {
            Method advisor = getAdvisor(bean, throwable);
            if (advisor != null) {
                return advisor.invoke(bean, throwable);
            }
        }
        return null;
    }
}
