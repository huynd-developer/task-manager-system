package org.example.j6backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public enum MessageType {
        CHAT,JOIN,LEAVE
    }
    @Builder.Default
    private MessageType type = MessageType.CHAT;
    private String content;
    private String sender;

}
