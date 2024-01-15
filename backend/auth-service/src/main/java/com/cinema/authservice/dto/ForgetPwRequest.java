package com.cinema.authservice.dto;

import jakarta.validation.constraints.Email;
import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

//Forget Password Request DTO
public class ForgetPwRequest {
    @Email(message = "Email Can't be Empty")
    private String email;
}

