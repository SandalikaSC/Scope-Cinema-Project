package com.cinema.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_movietime_seat")
public class ReservedSeats {
    @Id
    public long reservedSeatId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seatId")
    private Seat seat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movieTimeId")
    private MovieTime movieTime;


    private  Date movieDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservationId")
    private Reservation reservation;




}
