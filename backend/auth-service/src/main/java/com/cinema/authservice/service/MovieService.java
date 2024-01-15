package com.cinema.authservice.service;


import com.cinema.authservice.dto.*;
import com.cinema.authservice.entity.Movie;
import com.cinema.authservice.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface MovieService {

    List<MovieDto> getAllMovies();

    long addMovie(MultipartFile image, MovieRequest movie, User userId) throws IOException, ParseException;

    MovieDto updateMovie(MultipartFile image, MovieUpdateRequest movieUpdateRequest) throws ParseException, IOException;

    List<MovieTimeDto> getMovieDetailById(long id);

    DashBoardRequest getTrendingMovieList();

    MovieDto deleteMovieById(long id);
}
