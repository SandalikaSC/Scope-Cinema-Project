package com.cinema.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_movie_timeslot_cinema")
public class MovieTime {
    @Id
    @GeneratedValue
    private long movieTimeId;
    public long noSeats;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeSlot;

    @OneToMany(mappedBy = "movieTime" )
    private List<ReservedSeats> reservedSeats;



}
