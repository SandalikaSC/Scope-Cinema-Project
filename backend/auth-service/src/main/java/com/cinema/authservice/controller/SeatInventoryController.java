package com.cinema.authservice.controller;

import com.cinema.authservice.dto.MovieTimeDto;
import com.cinema.authservice.dto.SeatInventoryDto;
import com.cinema.authservice.dto.TimeSlotDto;
import com.cinema.authservice.service.SeatInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SeatInventoryController {
    @Autowired
    private final SeatInventoryService service;

    @PatchMapping("/")
    public ResponseEntity getSeatInventoryForMovie(
           @RequestBody List<SeatInventoryDto> seatInventoryDtos
    ) {

        System.out.println(seatInventoryDtos);
        if (service.updateSeatInventory(seatInventoryDtos)) {
            return new ResponseEntity("Seat Inventory Updated",HttpStatus.OK);
        } else {
            return new ResponseEntity("Something went Wrong.Try Again", HttpStatus.BAD_REQUEST);
        }





    }
    @GetMapping("/{movieId}")
    public ResponseEntity< List<TimeSlotDto>> timeSlotSeats(
            @PathVariable long movieId
    ) {

        List<TimeSlotDto> timeSlotDto=service.getSeatInventoryByMovieId(movieId);

        if (timeSlotDto != null) {
            return ResponseEntity.ok(timeSlotDto);
        } else {
            return new ResponseEntity("Something went Wrong", HttpStatus.BAD_REQUEST);
        }

    }

}
