package me.codeislet.springboot.netty.websocket.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {

    private String mapper;
    private Object body;
}