package com.namusd.messenger.model.entity;

import com.namusd.messenger.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private String roomHash;

    private String content;

    private String sender;

    private String senderNickname;
    private String type;

    private Long fileId;
    private LocalDateTime regDate;

}
