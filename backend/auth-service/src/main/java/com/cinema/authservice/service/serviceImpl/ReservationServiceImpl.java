package com.cinema.authservice.service.serviceImpl;

import com.cinema.authservice.entity.MovieTime;
import com.cinema.authservice.entity.Reservation;
import com.cinema.authservice.entity.ReservedSeats;
import com.cinema.authservice.entity.Seat;
import com.cinema.authservice.kafka.kafkaDTO.KafkaReservationDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaReservedSeatDTO;
import com.cinema.authservice.repository.MovieTimeRepository;
import com.cinema.authservice.repository.ReservationRepository;
import com.cinema.authservice.repository.ReservedSeatsRepository;
import com.cinema.authservice.repository.SeatRepository;
import com.cinema.authservice.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservedSeatsRepository reservedSeatsRepository;
    @Autowired
    private MovieTimeRepository movieTimeRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public void addReservation(KafkaReservationDTO kafkaReservationDTO) {
            reservationRepository.save(Reservation.builder()
                            .reservationId(kafkaReservationDTO.getReservationId())
                            .total(kafkaReservationDTO.getTotalPrice())
                            .date(kafkaReservationDTO.getDate())
                    .build());

    }

    @Transactional
    @Override
    public void addNewReservedSeat(KafkaReservedSeatDTO kafkaReservedSeatDTO) {
        try {
            MovieTime movieTime = movieTimeRepository.findById(kafkaReservedSeatDTO.getMovieTimeId())
                    .orElseThrow(() -> new RuntimeException("MovieTime not found for id: " + kafkaReservedSeatDTO.getMovieTimeId()));

            Seat seat = seatRepository.findById(kafkaReservedSeatDTO.getSeatId())
                    .orElseThrow(() -> new RuntimeException("Seat not found for id: " + kafkaReservedSeatDTO.getSeatId()));


            Thread.sleep(4000);

            Reservation reservation = reservationRepository.findById(kafkaReservedSeatDTO.getReservationId())
                    .orElseThrow(() -> new RuntimeException("Reservation not found for id: " + kafkaReservedSeatDTO.getReservationId()));



            reservedSeatsRepository.save(ReservedSeats.builder()
                    .reservedSeatId(kafkaReservedSeatDTO.getReservationSeatId())
                    .movieTime(movieTime)
                    .seat(seat)
                    .reservation(reservation)
                    .movieDate(kafkaReservedSeatDTO.getMovieDate())
                    .build());




            log.info("Reserved seat added successfully. MovieTimeId: {}, SeatId: {}, ReservationId: {}",
                    kafkaReservedSeatDTO.getMovieTimeId(), kafkaReservedSeatDTO.getSeatId(), kafkaReservedSeatDTO.getReservationId());
        } catch (Exception e) {

            log.error("Error adding reserved seat. MovieTimeId: {}, SeatId: {}, ReservationId: {}",
                    kafkaReservedSeatDTO.getMovieTimeId(), kafkaReservedSeatDTO.getSeatId(), kafkaReservedSeatDTO.getReservationId(), e);

        }

    }

    @Transactional
    @Override
    public void cancelReservation(int reservationId) {
        Reservation reservation=reservationRepository.findById((long) reservationId).orElseThrow();

        reservedSeatsRepository.deleteAllByReservation(reservation);
        reservationRepository.delete(reservation);

    }
}
