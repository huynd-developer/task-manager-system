package org.example.j6backend.controller;

import org.example.j6backend.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SimpleChatController {
  @MessageMapping("/simple-chat")
  @SendTo("/topic/simple-chat")
    public ChatMessage sendMessage(ChatMessage chatMessage){
      return chatMessage;
  }
}
