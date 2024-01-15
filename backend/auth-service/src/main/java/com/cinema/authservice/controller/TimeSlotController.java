package com.cinema.authservice.controller;

import com.cinema.authservice.config.UserService;
import com.cinema.authservice.dto.MovieRequest;
import com.cinema.authservice.dto.TimeSlotDto;
import com.cinema.authservice.entity.Movie;
import com.cinema.authservice.entity.TimeSlot;
import com.cinema.authservice.service.MovieService;
import com.cinema.authservice.service.TimeSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/time")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TimeSlotController {
    @Autowired
    private final TimeSlotService timeSlotService;

    @GetMapping("/")
    public ResponseEntity getAllSlots() {
        List<TimeSlotDto> timeSlots = timeSlotService.getAllSlots();
        if (!timeSlots.isEmpty()){
            return new ResponseEntity<>(timeSlots, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("timeSlots are not fund", HttpStatus.NOT_FOUND);
        }

    }






}

