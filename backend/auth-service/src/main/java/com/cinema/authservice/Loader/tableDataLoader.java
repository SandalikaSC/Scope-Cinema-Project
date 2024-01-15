package com.cinema.authservice.Loader;

import com.cinema.authservice.entity.Day;
import com.cinema.authservice.entity.TimeSlot;
import com.cinema.authservice.repository.TimeSlotRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class tableDataLoader implements CommandLineRunner {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (timeSlotRepository.count() == 0) {
            // Add sample data
            TimeSlot timeSlot1 = TimeSlot.builder()
                    .starting("09:00")
                    .ending("12:00")
                    .day(Day.Weekday)
                    .build();

            TimeSlot timeSlot2 = TimeSlot.builder()
                    .starting("14:00")
                    .ending("17:00")
                    .day(Day.Sunday)
                    .build();

            // Save data to the database
            timeSlotRepository.saveAll(Arrays.asList(timeSlot1, timeSlot2));

            log.info("Sample data added to t_timeslot table.");
        } else {
            log.info("Data already exists in t_timeslot table.");
        }
    }
}