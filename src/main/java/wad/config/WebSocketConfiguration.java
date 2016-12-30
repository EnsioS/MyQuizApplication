package wad.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // osoite, johon selain ottaa yhteyttä rekisteröityäkseen
        registry.addEndpoint("/register").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // osoite, jonka alla websocket-kommunikaatio tapahtuu
        registry.setApplicationDestinationPrefixes("/ws");
        // osoite, jonka kautta viestit kuljetetaan
        registry.enableSimpleBroker("/channel");
    }

}
