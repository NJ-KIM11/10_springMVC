package com.ohgiraffers.chap09websocket.server;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class ChatWebSocketHandler extends TextWebSocketHandler {

    // 접속한 클라이언트의 webSocketSession 을 관리할 set
    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 WebSocket 연결을 성공적으로 수행했을 때 호출되는 메소드
        clients.add(session);
        System.out.println("웹소켓 연결 : " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 텍스트 메시지를 수신했을때 호출되는 메소드
        // db 에 추가하고 싶으면 여기서

        System.out.println("메시지 출력 : " + session.getId() + " : " + message.getPayload());
        synchronized (clients){     // 동기화해서 안전하게 처리
            for (WebSocketSession client : clients){    // 여러유저의 세션들에서
                if(!client.equals(session)){            // 나를 제외한 나머지(나의 세션만 빼고)
                    client.sendMessage(new TextMessage(message.getPayload()));  // 메세지 본문내용 전송
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // 통신 중 에러가 발생했을 때 호출되는 메소드
        System.out.println("에러 발생 : " + session.getId());   // 누구의 에러인지 확인가능(어느 session 인지)
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트가 Websocket 연결을 닫았을 때 호출되는 메소드
        clients.remove(session);
        System.out.println("웹소켓 종료 : " + session.getId());
    }



}
