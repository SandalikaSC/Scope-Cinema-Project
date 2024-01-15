package com.cinema.authservice.service.serviceImpl;

import com.cinema.authservice.dto.MovieTimeDetailedDto;
import com.cinema.authservice.dto.SeatRequestDTO;
import com.cinema.authservice.dto.TimeSlotDto;
import com.cinema.authservice.entity.*;
import com.cinema.authservice.repository.*;
import com.cinema.authservice.service.MovieInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class MovieInformationServiceImpl implements MovieInformationService {
    @Autowired
    private MovieTimeRepository movieTimeRepository;
    @Autowired
    private ReservedSeatsRepository reservedSeatsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Override
    public List<MovieTimeDetailedDto> getAllMovieDetails(long movieId) {
        List<MovieTime> movieTimes=movieTimeRepository.findAllByMovieMovieIdOrderByUserAsc(movieId);
        List<MovieTimeDetailedDto>movieTimeDetailedDtos=new ArrayList<MovieTimeDetailedDto>();
        long currentUser=0;
        for (int i = 0; i < movieTimes.size(); i++) {

            if (i==0 || currentUser!=movieTimes.get(i).getUser().getId()){

                MovieTime movieTime=movieTimes.get(i);
                User user=movieTime.getUser();
                Movie movie =movieTime.getMovie();
                TimeSlot timeSlot=movieTime.getTimeSlot();

                List<TimeSlotDto> timeSlotDtoList=new ArrayList<TimeSlotDto>();

                TimeSlotDto timeSlotDto= TimeSlotDto.builder()
                        .day(timeSlot.getDay().toString())
                        .slotId(timeSlot.getSlotId())
                        .starting(stringToTime(timeSlot.getStarting()))
                        .ending(stringToTime(timeSlot.getEnding()))
                        .noOfSeats(movieTime.getNoSeats())
                        .movieTimeId(movieTime.getMovieTimeId())
                        .build();

                timeSlotDtoList.add(timeSlotDto);

                MovieTimeDetailedDto movieTimeDetailedDto=MovieTimeDetailedDto.builder()
                        .cinemaId(user.getId())
                        .cinemaName(user.getName())
                        .cinemaAddress(user.getAddress())
                        .cinemaContact(user.getContact())
                        .movieId(movie.getMovieId())
                        .timeSlotList(timeSlotDtoList)
                        .build();
                movieTimeDetailedDtos.add(movieTimeDetailedDto);
                currentUser=movieTime.getUser().getId();

            }else if( currentUser==movieTimes.get(i).getUser().getId()){
                MovieTime newMovieTime=movieTimes.get(i);
                int elementCount=movieTimeDetailedDtos.size();
                MovieTimeDetailedDto currentMovieDetailList=movieTimeDetailedDtos.get(elementCount-1);

                TimeSlot timeSlot=newMovieTime.getTimeSlot();

                List<TimeSlotDto> timeSlotDtoList=currentMovieDetailList.getTimeSlotList();

                TimeSlotDto timeSlotDto= TimeSlotDto.builder()
                        .day(timeSlot.getDay().toString())
                        .slotId(timeSlot.getSlotId())
                        .starting(stringToTime(timeSlot.getStarting()))
                        .ending(stringToTime(timeSlot.getEnding()))
                        .noOfSeats(newMovieTime.getNoSeats())
                        .movieTimeId(newMovieTime.getMovieTimeId())
                        .build();

                timeSlotDtoList.add(timeSlotDto);
                currentUser=newMovieTime.getUser().getId();


            }

        }
        return movieTimeDetailedDtos;
    }

    @Override
    public boolean[] getSeatAvailability(SeatRequestDTO seatRequestDTO) {
        User user=userRepository.findById(Long.valueOf(seatRequestDTO.getCinemaId())).orElseThrow();
        Movie movie=movieRepository.findById(Long.valueOf(seatRequestDTO.getMovieId())).orElseThrow();
        TimeSlot timeSlot=timeSlotRepository.findById(Long.valueOf(seatRequestDTO.getTimeSlotId())).orElseThrow();


         MovieTime movieTime=movieTimeRepository.findTopByMovieAndUserAndTimeSlotOrderByMovieTimeIdDesc(movie, user,timeSlot).orElseThrow();

         List<Seat> seats=null;
        boolean[] totalSeatAvailablity=new boolean[(int) movieTime.getNoSeats()];
        List<Integer> seatArray=new ArrayList<Integer>(seats.size());
        for(Seat seat:seats){
            seatArray.add((int) seat.getSeatId());
        }

        for (int i = 1; i <= (int) movieTime.getNoSeats(); i++) {
            if(seatArray.contains(i)){
                totalSeatAvailablity[i-1]=true;
            }else {
                totalSeatAvailablity[i-1]=false;
            }

        }
        return totalSeatAvailablity;
    }


    private Time stringToTime(String timeString){
        LocalTime localTime = LocalTime.parse(timeString);
        return Time.valueOf(localTime);
    }

}
