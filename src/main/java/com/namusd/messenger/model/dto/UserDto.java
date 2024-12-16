package com.namusd.messenger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

    @Getter
    @AllArgsConstructor
    public static class Join{
        private String username;
        private String password;
    }
}
