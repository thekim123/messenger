package com.namusd.messenger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginDto {
    private String username;
    private String password;
}
