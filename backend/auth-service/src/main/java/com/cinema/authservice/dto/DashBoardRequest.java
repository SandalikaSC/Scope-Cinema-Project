package com.cinema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Dashboard Details Request DTO
public class DashBoardRequest {

    private long totalMovies;
    private long ticketsReservedToday;
    private long seatsBooked;
    List<MovieDto> trendingMovieDtoList;
}
