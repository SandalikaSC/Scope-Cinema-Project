package com.cinema.authservice.service;


import com.cinema.authservice.dto.SeatInventoryDto;
import com.cinema.authservice.dto.TimeSlotDto;

import java.util.List;

public interface SeatInventoryService {

    boolean updateSeatInventory(List<SeatInventoryDto> seatInventoryDtos);

    List<TimeSlotDto> getSeatInventoryByMovieId(long movieId);

    boolean addSeats(long seatCount) throws InterruptedException;
}
