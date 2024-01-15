package com.cinema.authservice.repository;

import com.cinema.authservice.entity.Reservation;
import com.cinema.authservice.entity.ReservedSeats;
import com.cinema.authservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT COUNT(r) FROM Reservation r WHERE DATE(r.date) = CURRENT_DATE")
    long countReservationsForToday();

}
