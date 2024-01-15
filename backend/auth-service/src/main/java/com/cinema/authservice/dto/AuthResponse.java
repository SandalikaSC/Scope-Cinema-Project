package com.cinema.authservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//Login Response DTO
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;
    private String userName;
}
