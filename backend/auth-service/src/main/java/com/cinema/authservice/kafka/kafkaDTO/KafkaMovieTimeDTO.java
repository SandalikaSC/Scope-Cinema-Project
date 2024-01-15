package com.cinema.authservice.kafka.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMovieTimeDTO {
    private long  movieTimeId;
    private long  timeSlotId;
    private long  movieId;
    private long  cinemaId;
    private int seatCount;
}
