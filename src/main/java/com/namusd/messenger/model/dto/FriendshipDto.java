package com.namusd.messenger.model.dto;

import com.namusd.messenger.model.domain.FriendStatus;
import com.namusd.messenger.model.entity.User;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

public class FriendshipDto {

    public static class Response{
        private Long id;
        private User user;
        private User friend;
        private FriendStatus status;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;
    }
}
