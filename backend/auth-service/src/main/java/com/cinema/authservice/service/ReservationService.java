package com.cinema.authservice.service;

import com.cinema.authservice.kafka.kafkaDTO.KafkaReservationDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaReservedSeatDTO;

public interface ReservationService {
    void addReservation(KafkaReservationDTO kafkaReservationDTO);

    void addNewReservedSeat(KafkaReservedSeatDTO kafkaReservedSeatDTO);

    void cancelReservation(int reservationId);
}
