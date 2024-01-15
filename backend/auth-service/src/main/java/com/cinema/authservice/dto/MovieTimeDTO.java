package com.cinema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieTimeDTO {

    private long movieTimeId;
    private long noSeats;
    private long movieId;
    private long timeSlot;
    private  long cinemaId;

}
