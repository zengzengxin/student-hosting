package com.luwei.websocket;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;

/**
 * @Author: ffq
 * @Description:
 * @Date: Create in 17:58 2018/11/16
 */
@ServerEndpoint(prefix = "netty-websocket")
@Component
public class MyWebSocket {

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers) throws IOException {
        System.out.println("new connection");
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("one connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void OnMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello Netty!");
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }
}