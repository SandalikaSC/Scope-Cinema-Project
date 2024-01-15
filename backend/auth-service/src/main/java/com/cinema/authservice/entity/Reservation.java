package com.cinema.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_reservation")
public class Reservation {

    @Id
    private long reservationId;
    private double  total;
    private Date date;
    @OneToMany(mappedBy = "reservation" )
    private List<ReservedSeats> reservedSeats ;

}
