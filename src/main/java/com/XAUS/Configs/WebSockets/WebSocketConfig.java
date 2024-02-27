package com.XAUS.Configs.WebSockets;

import com.XAUS.Configs.SecurityConfig.TokenService;
import com.XAUS.Managers.LogManager;
import com.XAUS.Models.User.User;
import com.XAUS.Repositories.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void configureMessageBroker (MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app")
                    .enableSimpleBroker("/topic");

    }

    @Override
    public void registerStompEndpoints (StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-endpoint")
//                .setHandshakeHandler(new UserHandshakeHandler())
                .setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        try{
            registration.interceptors(new ChannelInterceptor() {
                @Override
                public Message<?> preSend(Message<?> message, MessageChannel channel) {
                    StompHeaderAccessor accessor =
                            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        String token = Objects.requireNonNull(accessor.getFirstNativeHeader("Authorization")).replace("Bearer ", ""); // Assuming the token is in the Authorization header
                        String validatedToken = tokenService.validateToken(token);
                        User user = userRepository.findByEmail(validatedToken);
                        Principal principal = new Principal() {
                            @Override
                            public String getName() {
                                return user.getEmail();
                            }

                        };

                        accessor.setUser(principal);
                    }
                    return message;
                }
            });
        }catch(NullPointerException e){
            LogManager.logError(getClass(), "Error while getting user by token on inbound channel ", e);
        }

    }


}
