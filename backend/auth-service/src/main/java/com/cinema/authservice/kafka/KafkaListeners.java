package com.cinema.authservice.kafka;

import com.cinema.authservice.kafka.kafkaDTO.KafkaCinemaDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaMovieDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaMovieTimeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

    //movieAdd update
    @KafkaListener(topics = "New_Movie_Topic", groupId = "groupId")
    void listener(String payload) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaMovieDTO movieDto = objectMapper.readValue(payload, KafkaMovieDTO.class);

        } catch (Exception e) {
            log.info("Error deserializing MovieDto: " + e.getMessage());
        }
    }


//New Movie Time
    @KafkaListener(topics = "New_Movie_Time_Topic", groupId = "groupId")
    void movieTimeListener(String payload) {


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaMovieTimeDTO kafkaMovieTimeDTO = objectMapper.readValue(payload, KafkaMovieTimeDTO.class);

        } catch (Exception e) {
            log.info("Error deserializing MovieDto: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "New_Cinema_Topic", groupId = "groupId")
    void cinemaListener(String payload) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            KafkaCinemaDTO kafkaCinemaDTO = objectMapper.readValue(payload, KafkaCinemaDTO.class);

        } catch (Exception e) {
            log.info("Error deserializing MovieDto: " + e.getMessage());
        }
    }
    @KafkaListener(topics = "New_Seat_Add_Topic", groupId = "groupId")
    void seatListener(long payload) {
        log.info("Received raw payload: " + payload);

    }



}
