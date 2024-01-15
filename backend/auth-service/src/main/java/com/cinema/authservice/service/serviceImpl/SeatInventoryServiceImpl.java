package com.cinema.authservice.service.serviceImpl;

import com.cinema.authservice.config.UserService;
import com.cinema.authservice.dto.SeatInventoryDto;
import com.cinema.authservice.dto.TimeSlotDto;
import com.cinema.authservice.entity.Movie;
import com.cinema.authservice.entity.MovieTime;
import com.cinema.authservice.entity.Seat;
import com.cinema.authservice.entity.User;
import com.cinema.authservice.kafka.kafkaDTO.KafkaMovieTimeDTO;
import com.cinema.authservice.repository.MovieRepository;
import com.cinema.authservice.repository.MovieTimeRepository;
import com.cinema.authservice.repository.ReservedSeatsRepository;
import com.cinema.authservice.repository.SeatRepository;
import com.cinema.authservice.service.SeatInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {

    @Autowired
    private MovieTimeRepository movieTimeRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReservedSeatsRepository reservedSeatsRepository;
    @Autowired
    private KafkaTemplate<String,Long> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, KafkaMovieTimeDTO> kafkaMovieTimeTemplate;

    @Transactional
    @Override
    public boolean updateSeatInventory(List<SeatInventoryDto> seatInventoryDtos) {
        for (SeatInventoryDto seatInventoryDto : seatInventoryDtos) {
            long movieTimeId=seatInventoryDto.getMovieTimeId();
            long noSeats=seatInventoryDto.getSeats();

        MovieTime movieTime=movieTimeRepository.findById(movieTimeId).orElseThrow();
        movieTime.setNoSeats(noSeats);

        long count = seatRepository.count();
        if(count<noSeats){
            for (int i = 0; i < noSeats-count; i++) {

                Seat seat= Seat.builder().build();
                Seat savedSeat = seatRepository.save(seat);
                kafkaTemplate.send("New_Seat_Add_Topic",savedSeat.getSeatId());
            }
        }

        MovieTime savedMovieTime = movieTimeRepository.save(movieTime);
        kafkaMovieTimeTemplate.send("New_Movie_Time_Topic",KafkaMovieTimeDTO.builder()
                .movieTimeId(savedMovieTime.getMovieTimeId())
                .movieId(savedMovieTime.getMovie().getMovieId())
                .seatCount((int) savedMovieTime.getNoSeats())
                .timeSlotId(savedMovieTime.getTimeSlot().getSlotId())
                .cinemaId(savedMovieTime.getUser().getId())
        .build());
        }
        return true;
    }

    @Override
    public List<TimeSlotDto> getSeatInventoryByMovieId(long movieId) {
        User user= userService.getUser();
        Movie movie=movieRepository.findById(movieId).orElseThrow();
        List<MovieTime> movieTimeList=movieTimeRepository.findAllByMovieAndUser(movie,user);


        List<TimeSlotDto> timeSlotDtoList = movieTimeList.stream()
                .map(movieTime -> new TimeSlotDto(
                     movieTime.getTimeSlot().slotId,
                        Time.valueOf(LocalTime.parse(movieTime.getTimeSlot().getStarting())),
                        Time.valueOf(LocalTime.parse(movieTime.getTimeSlot().getEnding())),
                        movieTime.getTimeSlot().day.toString(),
                        movieTime.getNoSeats(),
                        movieTime.getMovieTimeId()
                )).toList();


        return timeSlotDtoList;
    }

    @Override
    public boolean addSeats(long seatCount) throws InterruptedException {
        long count = seatRepository.count();//get current seat count
        if(count<seatCount){
            for (int i = 0; i < seatCount-count; i++) {
                Seat seat= Seat.builder().build();
                Seat savedSeat = seatRepository.save(seat);
                kafkaTemplate.send("New_Seat_Add_Topic",savedSeat.getSeatId());
            }
        }
        return true;
    }

}
