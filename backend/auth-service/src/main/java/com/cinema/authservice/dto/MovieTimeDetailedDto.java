package com.cinema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieTimeDetailedDto {

    private Long cinemaId;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaContact;
    private List<TimeSlotDto> timeSlotList;
    private Long movieId;


}
