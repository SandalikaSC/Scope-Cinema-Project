package com.cinema.authservice.service.serviceImpl;

import com.cinema.authservice.dto.MovieTimeDto;
import com.cinema.authservice.dto.TimeSlotDto;
import com.cinema.authservice.entity.TimeSlot;
import com.cinema.authservice.repository.TimeSlotRepository;
import com.cinema.authservice.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private final TimeSlotRepository timeSlotRepository;
    @Override
    public List<TimeSlotDto> getAllSlots() {

        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        List<TimeSlotDto> timeSlotDtoList = timeSlots.stream()
                .map(time -> new TimeSlotDto(
                        time.getSlotId(),
                        Time.valueOf(LocalTime.parse(time.getStarting())),
                        Time.valueOf(LocalTime.parse(time.getEnding())),
                        time.getDay().toString(),
                        0,
                        0
                )).toList();

        return timeSlotDtoList;
    }
}
