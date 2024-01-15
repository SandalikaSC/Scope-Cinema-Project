package com.cinema.authservice.controller;

import com.cinema.authservice.config.UserService;
import com.cinema.authservice.dto.*;
import com.cinema.authservice.entity.Movie;
import com.cinema.authservice.service.MovieService;
import com.cinema.authservice.service.SeatInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovieController {
    @Autowired
    private final MovieService movieService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final SeatInventoryService seatInventoryService;
    @GetMapping("/")
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        try {
            List<MovieDto> movies=movieService.getAllMovies();
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MovieTimeDto>> getMovieDetailsById(@PathVariable long id) {
        try {
            List<MovieTimeDto> movieDetail=movieService.getMovieDetailById(id);
            return ResponseEntity.ok(movieDetail);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }

    }
    @PatchMapping("/")
    public ResponseEntity updateMovie(@RequestPart("banner") MultipartFile image,
                                      @RequestParam("title") String title ,
                                      @RequestParam("language") String language ,
                                      @RequestParam("movieId") long movieId ,
                                      @RequestParam("duration") String duration ,
                                      @RequestParam("genre") String genre) {
        try {

        System.out.println(image.getOriginalFilename());
        System.out.println(duration);
        System.out.println(movieId);
        System.out.println(genre);

                    MovieUpdateRequest movieUpdateRequest=MovieUpdateRequest.builder()
                    .duration(duration)
                    .genre(genre)
                    .title(title)
                    .language(language)
                    .movieId(movieId)
                    .build();
            MovieDto movie = movieService.updateMovie(image,movieUpdateRequest);
            if (movie != null) {
                return new ResponseEntity<>("Movie Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Movie not fund", HttpStatus.NOT_FOUND);
            }
        }catch (ParseException e){
            return new ResponseEntity("Something Went Wrong with the duration parsing", HttpStatus.BAD_REQUEST);

        } catch (IOException e) {
           return new ResponseEntity("Something Went Wrong with the file Upload"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/" )
    public ResponseEntity addMovie( @RequestParam("banner") MultipartFile file,
                                    @RequestParam("title") String title ,
                                    @RequestParam("language") String language ,
                                    @RequestParam("duration") String duration ,
                                    @RequestParam("genre") String genre,
                                    @RequestParam("seats") int seats,
                                    @RequestParam("timeSlots") List<Long> timeSlots
    )  {
        try{
            Map<Long, Long> times = new HashMap<>();

            // Assuming timeSlots and seats have the same size
            for (int i = 0; i < timeSlots.size(); i++) {
                long timeSlot = timeSlots.get(i);
                times.put(timeSlot, (long) seats);
            }
            MovieRequest movieRequest=MovieRequest.builder()
                    .duration(duration)
                    .genre(genre)
                    .title(title)
                    .language(language)
                    .timeslotSeats(times)
                    .build();
            long seatCount = movieService.addMovie(file, movieRequest, userService.getUser());
            boolean addMovie=seatInventoryService.addSeats(seatCount);


            if(addMovie){

                return new ResponseEntity("New movie added", HttpStatus.OK);
            }else{
                return new ResponseEntity("Something Went Wrong.Try Again", HttpStatus.BAD_REQUEST);
            }
        }catch (ParseException e){
            return new ResponseEntity("Something Went Wrong with the duration parsing", HttpStatus.BAD_REQUEST);

        }
        catch (IOException e){

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity DeleteMovie(@PathVariable long id) {

        MovieDto movie=movieService.deleteMovieById(id);

         if(movie!=null){
             return ResponseEntity.ok("You have Deleted Movie "+movie.getTitle());

         }else {
             return ResponseEntity.ok("Failed to Delete the movie");
         }



    }

@GetMapping(value = "/dashboard")
    public ResponseEntity<DashBoardRequest> getTrendingMovies(){
    try {
        DashBoardRequest movieDetail=movieService.getTrendingMovieList();
        return ResponseEntity.ok(movieDetail);
    } catch (Exception e) {
        System.out.println(e);
        return new ResponseEntity("Nothing to display", HttpStatus.BAD_REQUEST);
    }

}


}

