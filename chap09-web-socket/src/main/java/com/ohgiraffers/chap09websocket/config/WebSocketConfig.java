package com.ohgiraffers.chap09websocket.config;

import com.ohgiraffers.chap09websocket.server.ChatWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    // 매핑 필요없이 구현해 놓으면 spring 에서 알아서 실행
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // WebSocket 핸들러를 등록하는 메소드( 커넥션 )
        registry.addHandler(new ChatWebSocketHandler(), "/chattingServer").setAllowedOrigins("*");
        //   /chattingServer 에서 요청이 왔을때  ChatWebSocketHandler 에서 구현된걸 등록한다.   // 모든 곳에서의 요청 허가

    }
}
