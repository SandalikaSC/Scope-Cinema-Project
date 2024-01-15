package com.cinema.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieUpdateRequest {
    @NotNull
    public long movieId;

    public String title;

//    public MultipartFile banner;

    public String genre;

    public String language;

    public String duration;

}
