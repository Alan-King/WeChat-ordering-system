package com.alan.sell.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocketService {
    private Session session;

    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("【WebSocket消息】有新的连接，连接总数为{}", webSocketSet.size());
    }
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("【WebSocket消息】有连接断开，连接总数为{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【WebSocket消息】收到客户端发来的消息：{}", message);

    }

    public void sendMessage(String message) {
        for (WebSocketService webSocket : webSocketSet) {
            try {
                log.info("【WebSocket消息】广播消息：{}", message);
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
