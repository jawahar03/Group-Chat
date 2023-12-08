package com.grpchat.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.grpchat.chat.ChatMessage;
import com.grpchat.chat.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j

public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations operations;
	
	@EventListener
	public void disconnectEventListner(SessionDisconnectEvent event)
	{
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		String userName = (String) accessor.getSessionAttributes().get("username");
		if(userName != null)
		{
			log.info("User Disconnected"+userName);
			var chatmsg = ChatMessage.builder().type(MessageType.LEAVE).sender(userName).build();
			operations.convertAndSend("/topic/public", chatmsg);
		}
		
	}
}
