package com.namusd.messenger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class JwtDto {

    @AllArgsConstructor
    @Builder
    public static class Refresh {
        private String access;
        private String refresh;
    }
}
