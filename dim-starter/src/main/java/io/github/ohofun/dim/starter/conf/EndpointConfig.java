package io.github.ohofun.dim.starter.conf;



import io.github.ohofun.dim.starter.handler.SampleWebSocketHandler;
import io.github.ohofun.dim.starter.interceptor.SampleWebSocketInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author ohofun
 * @version 1.0
 * @date 2021/12/10 23:36
 */
@SpringBootConfiguration
@EnableWebSocket
public class EndpointConfig implements WebSocketConfigurer {

    private final SampleWebSocketHandler sampleWebSocketHandler;
    private final SampleWebSocketInterceptor sampleWebSocketInterceptor;

    public EndpointConfig(SampleWebSocketHandler sampleWebSocketHandler, SampleWebSocketInterceptor sampleWebSocketInterceptor) {
        this.sampleWebSocketHandler = sampleWebSocketHandler;
        this.sampleWebSocketInterceptor = sampleWebSocketInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(sampleWebSocketHandler, "test"+"/**")
                .addInterceptors(sampleWebSocketInterceptor)
                .setAllowedOrigins("*");
    }
}

