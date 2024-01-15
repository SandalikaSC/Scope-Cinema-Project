package com.cinema.authservice.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_movie")
public class Movie {

    @Id
    @GeneratedValue
    public long movieId;
    public String title;
    public String banner;
    public String genre;
    public String language;
    public Time duration;

    @OneToMany(mappedBy = "movie")
    private List<MovieTime> movieTimes;




}
