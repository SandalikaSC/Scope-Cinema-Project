package com.cinema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class ReservationDTO {
    private Integer reservationId;
    private Integer movieId;
    private Integer cinemaId;
    private Integer timeSlotId;
    private List<Integer> seatSelection;
    private Date movieDate;
}