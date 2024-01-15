package com.cinema.authservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResetPwRequest {
    private String token;
    private String password;
    private String confirmPassword;
}

