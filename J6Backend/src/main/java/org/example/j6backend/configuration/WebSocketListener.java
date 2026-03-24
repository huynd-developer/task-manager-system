package org.example.j6backend.configuration;

import org.example.j6backend.entity.ChatMessage;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Lắng nghe sự kiện gửi dữ liệu từ endpoint
 */
public class WebSocketListener {
    private final SimpMessageSendingOperations messageSendingOperations;

    public WebSocketListener(SimpMessageSendingOperations messageSendingOperations) {
        this.messageSendingOperations = messageSendingOperations;
    }
// Lắng nghe sự kiện
    public void disconnect(SessionDisconnectEvent event){
        ChatMessage msg = new ChatMessage();
        msg.setType(ChatMessage.MessageType.LEAVE);

    }
}
