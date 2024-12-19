package com.namusd.messenger.model.entity;

import com.namusd.messenger.common.BaseTimeEntity;
import com.namusd.messenger.model.domain.ChatRoomType;
import com.namusd.messenger.model.dto.ChatRoomDto;
import com.namusd.messenger.model.dto.ChatRoomUserDto;
import com.namusd.messenger.model.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@ToString(exclude = {"participants"})
public class ChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;

    @OneToMany
    private List<User> participants;

    private boolean isPrivate;

    public void updateRoomName(String newRoomName) {
        this.roomName = newRoomName;
    }

    public ChatRoomDto.Response toDto() {
        List<UserDto.Response> participants = this.participants.stream()
                .map(User::toDto)
                .collect(Collectors.toList());

        return ChatRoomDto.Response.builder()
                .id(this.id)
                .isPrivate(this.isPrivate)
                .roomName(this.roomName)
                .type(this.type)
                .participants(participants)
                .build();
    }

    public void withParticipants(List<User> chatRoomUsers) {
        this.participants = chatRoomUsers;
    }
}
