package com.cinema.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {
    @NotNull
    public String title;
    @NotNull
    public String genre;
    @NotNull
    public String language;
    @NotNull
    public String duration;

    private Map<Long, Long> timeslotSeats;

}
