package com.cinema.authservice.service;


import com.cinema.authservice.dto.MovieTimeDetailedDto;
import com.cinema.authservice.dto.SeatRequestDTO;

import java.util.List;

public interface MovieInformationService {


    List<MovieTimeDetailedDto> getAllMovieDetails(long movieId);

    boolean[] getSeatAvailability(SeatRequestDTO seatRequestDTO);
}
