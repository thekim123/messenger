package com.namusd.messenger.model.dto;

import com.namusd.messenger.model.domain.ParticipantRole;
import com.namusd.messenger.model.entity.ChatRoom;
import com.namusd.messenger.model.entity.User;
import lombok.*;

import javax.persistence.*;

public class ChatRoomUserDto {

    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    @ToString
    @Getter
    public static class Response{
        private Long id;
        private ChatRoom chatRoom;
        private UserDto.Response participant;
        private ParticipantRole role;
        private boolean isRead;
    }

}
