package com.cinema.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_timeslot")
public class TimeSlot{

    @Id
    @GeneratedValue
    public long slotId;
    public String starting;
    public String ending;
    @Enumerated(EnumType.STRING)
    public  Day day;

    @OneToMany(mappedBy = "timeSlot")
    private List<MovieTime> movieTimes;

}
