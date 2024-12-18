package com.namusd.messenger.model.dto;

import com.namusd.messenger.model.domain.FriendStatus;
import com.namusd.messenger.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

public class FriendshipDto {

    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    public static class Response {
        private Long id;
        private UserDto.Response user;
        private UserDto.Response friend;
        private FriendStatus status;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
    }
}
