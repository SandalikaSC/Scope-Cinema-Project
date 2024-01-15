package com.cinema.authservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//Change Password Request DTO
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
