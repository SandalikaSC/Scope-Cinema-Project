package com.cinema.authservice.repository;

import com.cinema.authservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReservedSeatsRepository extends JpaRepository<ReservedSeats,Long> {
//    @Query(value = "SELECT rs.seat FROM ReservedSeats rs WHERE rs.movieTime.movieTimeId = :movieTimeId AND rs.date = :date")
//    List<Seat> findSeatsByMovieTimeMovieTimeIdAndReservationDate(long movieTimeId, Date date);

    void deleteAllByReservation(Reservation reservation);

    @Query("SELECT MAX(COUNT(rs)) FROM ReservedSeats rs " +
            "WHERE rs.movieTime.movie.movieId = :movieId " +
            "GROUP BY rs.movieDate")
    Integer getMaxRowCountByMovieIdAndDate(long movieId);



    @Query("SELECT COUNT(r) FROM ReservedSeats r WHERE DATE(r.movieDate) = CURRENT_DATE")
    long countReservationSeatsToday();

    @Query(value = "SELECT distinct mt.movieTime.movieTimeId FROM ReservedSeats mt ")
    List<Long> findTop5MovieTimeIds();

    @Query(value = "SELECT count(distinct mt.reservation) FROM ReservedSeats mt where DATE(mt.movieDate) = CURRENT_DATE")
    Long reservationTicketsToday();
}
