package me.codeislet.springboot.netty.websocket.service;

import lombok.RequiredArgsConstructor;
import me.codeislet.springboot.netty.websocket.server.netty.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@SpringBootApplication
@RequiredArgsConstructor
@ComponentScan(basePackages = {
        "me.codeislet.springboot.netty.websocket.server",
        "me.codeislet.springboot.netty.websocket.service",
})
public class ServiceApplication {

    private final NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> nettyServer.start();
    }
}