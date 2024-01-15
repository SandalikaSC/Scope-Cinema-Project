package com.cinema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//Get User Responce
public class GetUserResponse {
    private long userId;
    private String name;
    private String email;
    private String address;
    private String contact;
}
