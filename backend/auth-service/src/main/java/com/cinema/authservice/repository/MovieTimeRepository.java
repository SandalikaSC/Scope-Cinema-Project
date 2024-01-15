package com.cinema.authservice.repository;

import com.cinema.authservice.entity.Movie;
import com.cinema.authservice.entity.MovieTime;
import com.cinema.authservice.entity.TimeSlot;
import com.cinema.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieTimeRepository extends JpaRepository<MovieTime,Long> {

    List<MovieTime> findAllByUserAndMovieMovieId(User user, long movieId);

    List<MovieTime> findAllByUser(User user);

    @Query("SELECT DISTINCT mt.movie FROM MovieTime mt " +
            "WHERE mt.user = :user ")
    List<Movie>findDistinctMoviesByUser(User user);

    @Query("SELECT count(DISTINCT mt.movie) FROM MovieTime mt " +
            "WHERE mt.user = :user ")
    long countDistinctMoviesByUser(User user);

    @Query("SELECT DISTINCT mt.movie FROM MovieTime mt " +
            "WHERE mt.user = :user order by mt.movieTimeId  DESC limit 5")
    List<Movie>findDistinctMoviesByUserLimit(User user);


    List<MovieTime> findAllByMovieMovieIdOrderByUserAsc(long movieId);

    Optional<MovieTime> findTopByMovieAndUserAndTimeSlotOrderByMovieTimeIdDesc(
            Movie movie, User user, TimeSlot timeSlot);

    List<MovieTime> findAllByMovieAndUser(Movie movie, User user);

   void deleteAllByUserAndMovie(User cinema, Movie movie);

    List<MovieTime> findAllByUserAndMovie(User cinema, Movie movie);

}
