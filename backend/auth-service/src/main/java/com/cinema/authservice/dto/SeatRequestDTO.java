package com.cinema.authservice.dto;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatRequestDTO {
    private Integer movieId;
    private Integer cinemaId;
    private Integer timeSlotId;
    private Date date;
}