package com.cinema.authservice.kafka;

import com.cinema.authservice.kafka.kafkaDTO.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    //Kafka running location
    @Value("${spring.kafka.bootstrap-servers}")
    private  String bootstrapServers;

    public Map<String,Object> consumerConfig(){
        Map<String,Object> props=new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return  props;
    }
//New Movie
    @Bean
    public ConsumerFactory<String, KafkaMovieDTO> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(KafkaMovieDTO.class, false));
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,KafkaMovieDTO>> factory(
            ConsumerFactory<String, KafkaMovieDTO> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<String,KafkaMovieDTO> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


//    New Movie Time
    @Bean
    public ConsumerFactory<String, KafkaMovieTimeDTO> kafkaMovieTimeConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(KafkaMovieTimeDTO.class, false));
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,KafkaMovieTimeDTO>> kafkaMovieTimeFactory(
            ConsumerFactory<String, KafkaMovieTimeDTO> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<String,KafkaMovieTimeDTO> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    //New Cinema
    @Bean
    public ConsumerFactory<String, KafkaCinemaDTO> cinemaConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(KafkaCinemaDTO.class));
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaCinemaDTO>> cinemaFactory(
            ConsumerFactory<String, KafkaCinemaDTO> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<String, KafkaCinemaDTO> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
//new Seat Reservation
    @Bean
    public ConsumerFactory<String, KafkaReservationDTO> reservationConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(KafkaReservationDTO.class));
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaReservationDTO>> eservationFactory(
            ConsumerFactory<String, KafkaReservationDTO> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<String, KafkaReservationDTO> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    //new reservedSeat
    @Bean
    public ConsumerFactory<String, KafkaReservedSeatDTO> reservedSeatConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(KafkaReservedSeatDTO.class));
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, KafkaReservedSeatDTO>> reservedSeatFactory(
            ConsumerFactory<String, KafkaReservedSeatDTO> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<String, KafkaReservedSeatDTO> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


//update seat Inventory

    @Bean
    public ConsumerFactory<String, Long> SeatConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(),
                new LongDeserializer());
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Long>> seatFactory(
            ConsumerFactory<String, Long> consumerFactory
    ){
        ConcurrentKafkaListenerContainerFactory<String, Long> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }




}
