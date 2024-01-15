package com.cinema.authservice;

import com.cinema.authservice.kafka.kafkaDTO.KafkaMovieDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaMovieTimeDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaReservationDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;


@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableScheduling
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, KafkaMovieDTO> kafkaTemplate){// Parse the time string
//        LocalTime localTime = LocalTime.parse("17:40");
//        Time timeValue = Time.valueOf(localTime);
//        return args -> {
//
//            kafkaTemplate.send("MovieAdd",
//                    KafkaMovieDTO.builder()
//                    .movieId(123)
//                    .banner(null)
//                    .title("efewfewfewfewf")
//                    .genre("hfefew")
//                    .language("cefeewf")
//                    .duration(timeValue)
//                    .build());
//        };
//    }
//    @Bean
//    CommandLineRunner commandLineRunnerMovieTime(KafkaTemplate<String, KafkaMovieTimeDTO> kafkaTemplate){// Parse the time string
//
//        return args -> {
//
//            kafkaTemplate.send("MovieTimeAdd",
//                    KafkaMovieTimeDTO.builder()
//                            .movieTimeId(122)
//                            .cinemaId(12)
//                            .timeSlotId(1)
//                            .seatCount(5)
//                            .movieId(123)
//                    .build());
//        };
//    }
//        @Bean
//    CommandLineRunner commandLineRunnerMovieTime(KafkaTemplate<String, Long> kafkaTemplate){// Parse the time string
//
//        return args -> {
//            kafkaTemplate.send("SeatAdd",
//                    5L
//            );
//        };
//    }
//
//    @Bean
//    CommandLineRunner commandLineRunnerMovieTime(KafkaTemplate<String, KafkaReservationDTO> kafkaTemplate){// Parse the time string
//
//        return args -> {
//
//            kafkaTemplate.send("NewReservation",
//                    KafkaReservationDTO.builder()
//                            .reservationId(2)
//                            .totalPrice(525.23)
//                            .date(new Date())
//                    .build());
//        };
//    }




}
