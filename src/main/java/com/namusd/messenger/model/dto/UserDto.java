package com.namusd.messenger.model.dto;

import com.namusd.messenger.model.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    @AllArgsConstructor
    public static class Join {
        private String username;
        private String password;
    }


    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String username;
        private UserRole roles;
    }


}
