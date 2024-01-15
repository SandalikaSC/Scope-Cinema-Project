package com.cinema.authservice.service;


import com.cinema.authservice.dto.TimeSlotDto;

import java.io.IOException;
import java.util.List;

public interface TimeSlotService {

    List<TimeSlotDto> getAllSlots();
}
