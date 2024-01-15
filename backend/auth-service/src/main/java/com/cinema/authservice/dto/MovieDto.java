package com.cinema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Movie DTO
public class MovieDto{
    private long  movieId;
    private String title;
    private String banner;
    private String genre;
    private String language;
    private Time duration;
}
