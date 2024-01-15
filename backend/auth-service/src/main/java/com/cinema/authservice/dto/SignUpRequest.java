package com.cinema.authservice.dto;

import com.cinema.authservice.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Address is mandatory")
    private String address;
    @NotBlank(message = "Contact is mandatory")
    private String contact;
}
