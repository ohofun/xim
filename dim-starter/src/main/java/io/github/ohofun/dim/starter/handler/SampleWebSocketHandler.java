package io.github.ohofun.dim.starter.handler;

import io.github.ohofun.dim.starter.util.AnnotationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author ohofun
 * @version 1.0
 * @date 2021/12/9 22:36
 */
@Component
public class SampleWebSocketHandler extends TextWebSocketHandler {

    private final AnnotationUtil annotationUtil;

    public SampleWebSocketHandler(AnnotationUtil annotationUtil) {
        this.annotationUtil = annotationUtil;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        for (Class<?> aClass : annotationUtil.getClassesWithAnnotationFromPackage("io.github.ohofun.dim.starter", Component.class)) {
            System.out.println(aClass.getName());
        }
        System.out.println(session.getUri());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }

}
