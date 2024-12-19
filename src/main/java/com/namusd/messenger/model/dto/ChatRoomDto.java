package com.namusd.messenger.model.dto;

import com.namusd.messenger.model.domain.ChatRoomType;
import lombok.*;

import java.util.List;

public class ChatRoomDto {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Response {
        private Long id;
        private String roomName;
        private ChatRoomType type;
        private List<UserDto.Response> participants;
        private boolean isPrivate;
    }
}
