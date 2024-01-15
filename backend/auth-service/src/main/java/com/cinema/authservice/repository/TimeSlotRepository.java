package com.cinema.authservice.repository;

import com.cinema.authservice.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> {
}
