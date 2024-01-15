package com.cinema.authservice.kafka.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaCinemaDTO {
    private long cinemaId;
    private String cinemaName;
    private String address;
    private String contact;
}
