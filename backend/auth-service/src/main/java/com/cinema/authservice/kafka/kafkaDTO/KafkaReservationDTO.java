package com.cinema.authservice.kafka.kafkaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KafkaReservationDTO {
    private long reservationId;
    private Date date;
    private Double totalPrice;

}
