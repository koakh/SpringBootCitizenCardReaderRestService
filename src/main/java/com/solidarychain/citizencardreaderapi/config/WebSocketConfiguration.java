package com.solidarychain.citizencardreaderapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Component
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {

  // the prefix you will prepend to every message’s route
  public static final String TOPIC_PREFIX = "/topic";
  static final String HANDLER = "/handler";
  static final String QUEUE_PREFIX = "/queue";
  static final String ALLOWED_ORIGINS = "http://localhost:8080";

  @Override
  // used to configure the endpoint on the backend for clients and server to link
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint(HANDLER).withSockJS();
  }

  @Override
  // used to configure the broker used to relay messages between server and
  // client.
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    /*
     * The application destination prefix is an arbitrary prefix to differentiate
     * between messages that need to be routed to message-handling methods for
     * application level work vs messages to be routed to the broker to broadcast to
     * subscribed clients. After application level work is finished the message can
     * be routed to broker for broadcasting.
     */
    registry.setApplicationDestinationPrefixes("/app");
    /*
     * The list of destination prefixes provided in this are based on what broker is
     * getting used. In this case we will use in-memory broker which doesn't have
     * any such requirements. For the purpose of maintaining convention the "/topic"
     * and the "/queue" prefixes are chosen. The convention dictates usage of
     * "/topic" destination for pub-sub model targeting many subscribers and the
     * "/queue" destination for point to point messaging.
     */
    registry.enableSimpleBroker(TOPIC_PREFIX, QUEUE_PREFIX);
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(myHandler(), HANDLER).setAllowedOrigins(ALLOWED_ORIGINS);
    // registry.addHandler(myHandler(), HANDLER).setAllowedOriginPatterns(ALLOWED_ORIGINS);
  }

  @Bean
  public WebSocketHandler myHandler() {
    return new MyHandler();
  }
}