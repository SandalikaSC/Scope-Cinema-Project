package com.cinema.authservice.kafka.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaReservedSeatDTO {
    private long reservationSeatId;
    private long seatId;
    private long movieTimeId;
    private long reservationId;
    private Date movieDate;
}
