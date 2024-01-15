package com.cinema.authservice.controller;

import com.cinema.authservice.kafka.kafkaDTO.KafkaCinemaDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaReservationDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaReservedSeatDTO;
import com.cinema.authservice.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/reser")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @KafkaListener(topics = "New_Reservation_Topic", groupId = "groupId")
    void newReservationListener(String payload) {
        System.out.println("Received raw payload: " + payload);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaReservationDTO kafkaReservationDTO = objectMapper.readValue(payload, KafkaReservationDTO.class);
            System.out.println("Deserialized MovieDto: " + kafkaReservationDTO);

            reservationService.addReservation(kafkaReservationDTO);
        } catch (Exception e) {
            System.out.println("Error deserializing MovieDto: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "New_Reserved_Seat_Topic", groupId = "groupId")
    void newReservedSeatListener(String payload) {
        System.out.println("Received raw payload: " + payload);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaReservedSeatDTO kafkaReservedSeatDTO = objectMapper.readValue(payload, KafkaReservedSeatDTO.class);
            System.out.println("Deserialized MovieDto: " + kafkaReservedSeatDTO);
            reservationService.addNewReservedSeat(kafkaReservedSeatDTO);
        } catch (Exception e) {
            System.out.println("Error deserializing MovieDto: " + e.getMessage());
        }
    }
    @KafkaListener(topics = "Reservation_Cancellation_Topic", groupId = "groupId")
    void reservationCancelListener(int reservationId) {
        System.out.println("Received raw payload: " + reservationId);
        reservationService.cancelReservation(reservationId);
    }

}
