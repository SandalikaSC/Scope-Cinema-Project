package com.cinema.authservice.kafka.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaSeatInventoryDTO {
    private long  movieTimeId;
    private int seatCount;
}
