package com.namusd.messenger.model.entity;

import com.namusd.messenger.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 메시지가 속한 채팅방

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender; // 메시지 발신자

    private String content; // 메시지 내용
}
