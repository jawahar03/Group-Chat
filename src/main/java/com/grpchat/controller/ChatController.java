package com.grpchat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.grpchat.chat.ChatMessage;

@Controller
public class ChatController 
{
	@MessageMapping("chat")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage msg)
	{
		return msg;
	}
	
	@MessageMapping("adduser")
	@SendTo("topic/public")
	public ChatMessage addUser(@Payload ChatMessage msg, SimpMessageHeaderAccessor accessor)
	{
		accessor.getSessionAttributes().put("Username", msg.getSender());
		return msg;
	}
}
