package com.cinema.authservice.controller;

import com.cinema.authservice.dto.MovieTimeDetailedDto;
import com.cinema.authservice.dto.SeatInventoryDto;
import com.cinema.authservice.dto.SeatRequestDTO;
import com.cinema.authservice.service.MovieInformationService;
import com.cinema.authservice.service.SeatInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movieInfo")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovieInformationController {
    @Autowired
    private final MovieInformationService service;


    //add kafka listner for get movie info given movie id
    @GetMapping("/{movieId}")
    public ResponseEntity<List<MovieTimeDetailedDto>> getSeatInventoryForMovie(
             @PathVariable long movieId
    ) {

        List<MovieTimeDetailedDto> movieTimeDetailedDto=service.getAllMovieDetails(movieId);

        return ResponseEntity.ok(movieTimeDetailedDto);
    }

//    add kafka listner for get seats with boolean array
    @GetMapping("/")
    public ResponseEntity<boolean[]> getSeatInventoryForMovie(
            @RequestBody SeatRequestDTO seatRequestDTO
    ) {

        boolean[] SeatAvailability=service.getSeatAvailability(seatRequestDTO);

        return ResponseEntity.ok(SeatAvailability);
    }


}
