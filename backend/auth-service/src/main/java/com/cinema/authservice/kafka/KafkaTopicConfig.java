package com.cinema.authservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

//@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic movieAdd(){ return TopicBuilder.name("New_Movie_Topic").build();}
    @Bean
    public NewTopic movieTimeAdd(){
        return TopicBuilder.name("New_Movie_Time_Topic").build();
    }

    @Bean
    public NewTopic cinemaAdd(){
        return TopicBuilder.name("New_Cinema_Topic").build();
    }
    @Bean
    public NewTopic seatAdd(){
        return TopicBuilder.name("New_Seat_Add_Topic").build();
    }
    @Bean
    public NewTopic newReservation(){
        return TopicBuilder.name("New_Reservation_Topic").build();
    }
    @Bean
    public NewTopic newReservedSeat(){
        return TopicBuilder.name("New_Reserved_Seat_Topic").build();
    }

    @Bean
    public NewTopic cancelReservedSeat(){
        return TopicBuilder.name("Reservation_Cancellation_Topic").build();
    }
}
