package com.cinema.authservice.kafka;

import com.cinema.authservice.kafka.kafkaDTO.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private  String bootstrapServers;

    public Map<String,Object> producerConfig(){
        Map<String,Object> props=new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return  props;
    }

    @Bean
    public ProducerFactory<String, KafkaMovieDTO> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String,KafkaMovieDTO> kafkaTemplate(
            ProducerFactory<String, KafkaMovieDTO> producerFactory)
    {
        return  new KafkaTemplate<>(producerFactory);
    }
@Bean
    public ProducerFactory<String, KafkaMovieTimeDTO> movieTimeProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String,KafkaMovieTimeDTO> movieTimeKafkaTemplate(
            ProducerFactory<String, KafkaMovieTimeDTO> producerFactory)
    {
        return  new KafkaTemplate<>(producerFactory);
    }

    //for sending cinema
    @Bean
    public ProducerFactory<String, KafkaCinemaDTO> cinemaProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, KafkaCinemaDTO> cinemaKafkaTemplate(
            ProducerFactory<String, KafkaCinemaDTO> producerFactory)
    {
        return  new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, KafkaReservationDTO> reservationProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, KafkaReservationDTO> reservationKafkaTemplate(
            ProducerFactory<String, KafkaReservationDTO> producerFactory)
    {
        return  new KafkaTemplate<>(producerFactory);
    }


    @Bean
    public ProducerFactory<String, KafkaReservedSeatDTO> reservedSeatProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, KafkaReservedSeatDTO> reservedSeatKafkaTemplate(
            ProducerFactory<String, KafkaReservedSeatDTO> producerFactory) {
        return new KafkaTemplate<>(producerFactory);

    }

    @Bean
    public ProducerFactory<String, Long> integerProducerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, Long> integerKafkaTemplate(
            ProducerFactory<String, Long> producerFactory)
    {
        return  new KafkaTemplate<>(producerFactory);
    }


}
