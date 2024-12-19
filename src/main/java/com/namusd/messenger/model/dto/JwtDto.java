package com.namusd.messenger.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class JwtDto {

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Refresh {
        @JsonProperty("accessToken")
        private String accessToken;
        @JsonProperty("refreshToken")
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class RefreshRequest{
        @JsonProperty("sendRefreshToken")
        private String sendRefreshToken;
    }
}
