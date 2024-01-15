package com.cinema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeDTO {

    private long slotId;
    private Time starting;
    private Time ending;
    private String day;

}
