package com.cinema.authservice.service.serviceImpl;

import com.cinema.authservice.config.UserService;
import com.cinema.authservice.dto.*;
import com.cinema.authservice.entity.*;
import com.cinema.authservice.kafka.kafkaDTO.KafkaMovieDTO;
import com.cinema.authservice.kafka.kafkaDTO.KafkaMovieTimeDTO;
import com.cinema.authservice.repository.*;
import com.cinema.authservice.service.MovieService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    private final MovieRepository movieRepository;
    @Autowired
    private final TimeSlotRepository timeSlotRepository;
    @Autowired
    private final MovieTimeRepository movieTimeRepository;
    @Autowired
    private final ReservedSeatsRepository reservedSeatsRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private KafkaTemplate<String, KafkaMovieDTO> kafkaMovieTemplate;
    @Autowired
    private KafkaTemplate<String, KafkaMovieTimeDTO> kafkaMovieTimeTemplate;
    @Autowired
    private KafkaTemplate<String,Long> kafkaTemplate;
    @Autowired
    private final UserService userService;

    @Value("${file.upload.directory}")
    private String folderPath;

    @Value("${file.upload2.directory}")
    private String folderPath2;
    @Value("${application.customer.location}")
    private String customerLocation;
    @Override
    public List<MovieDto> getAllMovies() {
        User cinema=userService.getUser();

        List<Movie> movies=movieTimeRepository.findDistinctMoviesByUser(cinema);//TODO make distinct movie


        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> new MovieDto(
                        movie.getMovieId(),
                        movie.getTitle(),
                        movie.getBanner(),
                        movie.getGenre(),
                        movie.getLanguage(),
                        movie.getDuration()

                        )).toList();
        return movieDtos;
    }

    @Transactional
    @Override
    public long addMovie(MultipartFile image, MovieRequest movieRequest, User user) throws IOException {

            if (image.isEmpty()) {
                throw  new IOException("Please select a file to upload");
            }


            String destinationDirectory1 = folderPath;
            String fileName =image.getOriginalFilename();
            File destFile1 = new File(destinationDirectory1 + File.separator + image.getOriginalFilename());

            String destinationDirectory2 =folderPath2;
            String fileName2 =UUID.randomUUID()+"_"+ image.getOriginalFilename();
            File destFile2 = new File(destinationDirectory2 + File.separator + fileName2);


            Files.copy(image.getInputStream(), destFile1.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(image.getInputStream(), destFile2.toPath(), StandardCopyOption.REPLACE_EXISTING);


            Movie movie = Movie.builder()
                    .genre(movieRequest.getGenre())
                    .title(movieRequest.getTitle())
                    .banner(fileName)
                    .language(movieRequest.getLanguage())
                    .duration(stringToTime(movieRequest.getDuration()))
                    .build();

            Movie savedMovie = movieRepository.save(movie);

            KafkaMovieDTO movieDto = KafkaMovieDTO.builder()
                    .movieId(savedMovie.getMovieId())
                    .banner(customerLocation+fileName2)
                    .title(savedMovie.getTitle())
                    .genre(savedMovie.getGenre())
                    .language(savedMovie.getLanguage())
                    .duration(savedMovie.getDuration())
                    .build();
            kafkaMovieTemplate.send("New_Movie_Topic", movieDto);
        long seats =0;
        try {
            Thread.sleep(5000);
            if (!movieRequest.getTimeslotSeats().isEmpty()) {

                for (Map.Entry<Long, Long> entry : movieRequest.getTimeslotSeats().entrySet()) {
                    Long slotId = entry.getKey();
                     seats = entry.getValue();

                    TimeSlot timeSlot = timeSlotRepository.findById(slotId)
                            .orElseThrow(() -> new NotFoundException("TimeSlot not found with id: " + slotId));



                    MovieTime movieTime = MovieTime.builder()
                            .noSeats(seats)
                            .timeSlot(timeSlot)
                            .user(user)
                            .movie(savedMovie)
                            .build();

                    MovieTime saved = movieTimeRepository.save(movieTime);

                    KafkaMovieTimeDTO kafkaMovieTimeDTO = KafkaMovieTimeDTO.builder()
                            .movieTimeId(saved.getMovieTimeId())
                            .cinemaId(user.getId())
                            .timeSlotId(timeSlot.getSlotId())
                            .seatCount((int) saved.getNoSeats())
                            .movieId(savedMovie.getMovieId())
                            .build();
                    kafkaMovieTimeTemplate.send("New_Movie_Time_Topic", kafkaMovieTimeDTO);


                }
            }


        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return seats;
    }

    @Override
    public MovieDto updateMovie(MultipartFile image, MovieUpdateRequest movieUpdateRequest) throws ParseException, IOException {

        if (image.isEmpty()) {
            throw  new IOException("Please select a file to upload");
        }
        Movie movie=movieRepository.findById(movieUpdateRequest.getMovieId()).orElseThrow();

        String destinationDirectory1 = folderPath;
        String fileName =image.getOriginalFilename();
        File destFile1 = new File(destinationDirectory1 + File.separator + image.getOriginalFilename());

        String destinationDirectory2 =folderPath2;
        String fileName2 =UUID.randomUUID()+"_"+ image.getOriginalFilename();
        File destFile2 = new File(destinationDirectory2 + File.separator + fileName2);


        Files.copy(image.getInputStream(), destFile1.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(image.getInputStream(), destFile2.toPath(), StandardCopyOption.REPLACE_EXISTING);


         movie.setTitle(movieUpdateRequest.getTitle());
         movie.setBanner(fileName);
         movie.setDuration(stringToTime(movieUpdateRequest.getDuration()));
         movie.setGenre(movieUpdateRequest.getGenre());
         movie.setLanguage(movieUpdateRequest.getLanguage());


        Movie savedMovie = movieRepository.save(movie);

        KafkaMovieDTO moviekafkaDto = KafkaMovieDTO.builder()
                .movieId(savedMovie.getMovieId())
                .banner(customerLocation+fileName2)
                .title(savedMovie.getTitle())
                .genre(savedMovie.getGenre())
                .language(savedMovie.getLanguage())
                .duration(savedMovie.getDuration())
                .build();
        kafkaMovieTemplate.send("New_Movie_Topic", moviekafkaDto);

        MovieDto movieDto=MovieDto.builder()
                .movieId(savedMovie.getMovieId())
                .banner(null)
                .title(savedMovie.getTitle())
                .genre(savedMovie.getGenre())
                .language(savedMovie.getLanguage())
                .duration(savedMovie.getDuration())
                .build();

        return movieDto;
    }

    @Override
    public   List<MovieTimeDto> getMovieDetailById(long id) {

        User cinema=userService.getUser();

        List<MovieTime> movies=movieTimeRepository.findAllByUserAndMovieMovieId(cinema,id);

        Movie movieG=movies.get(0).getMovie();
        MovieDto movieDto=MovieDto.builder()
                .banner(null)//TODO set movie image url
                .title(movieG.getTitle())
                .duration(movieG.getDuration())
                .genre(movieG.getGenre())
                .language(movieG.getLanguage())
                .movieId(movieG.getMovieId())
                .build();

            List<MovieTimeDto> movieTimeDtos = movies.stream()
                    .map(movie -> new MovieTimeDto(
                             movie.getMovieTimeId(),
                            movie.getNoSeats(),
                            movieDto,
                            movie.getTimeSlot().getSlotId(),
                            stringToTime(movie.getTimeSlot().getStarting()),
                            stringToTime(movie.getTimeSlot().getEnding()),
                            movie.getTimeSlot().getDay().toString()

                    )).toList();


        return movieTimeDtos;
    }

    @Override
    public DashBoardRequest getTrendingMovieList() {


        long movieCount=movieTimeRepository.countDistinctMoviesByUser(userService.getUser());
        long todayTicketBooked=reservedSeatsRepository.reservationTicketsToday();
        long todaySeatBook=reservedSeatsRepository.countReservationSeatsToday();
        List<Long> top5MovieTimeIds=reservedSeatsRepository.findTop5MovieTimeIds();
        List<Movie> movies=movieRepository.findAll();

        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> new MovieDto(
                        movie.getMovieId(),
                        movie.getTitle(),
                        movie.getBanner(),
                        movie.getGenre(),
                        movie.getLanguage(),
                        movie.getDuration()

                )).toList();


        return DashBoardRequest.builder()
                .seatsBooked(todaySeatBook)
                .totalMovies(movieCount)
                .ticketsReservedToday(todayTicketBooked)
                .trendingMovieDtoList(movieDtos)
                .build();
    }

    @Transactional
    @Override
    public MovieDto deleteMovieById(long id) {
        User cinema=userService.getUser();
        Movie movie=movieRepository.findById(id).orElseThrow();
        List<MovieTime> movieTimeList = movieTimeRepository.findAllByUserAndMovie(cinema, movie);
        movieTimeRepository.deleteAllByUserAndMovie(cinema, movie);
        return MovieDto.builder()
                .title(movie.getTitle())
                .movieId(movie.getMovieId())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .banner(movie.getBanner())
                .build();
    }

    private Time stringToTime(String timeString){
        LocalTime localTime = LocalTime.parse(timeString);
        return Time.valueOf(localTime);
    }

}
