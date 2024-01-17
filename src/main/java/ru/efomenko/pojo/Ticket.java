package ru.efomenko.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@Builder
public class Ticket {
     private String origin;
     private String origin_name;
     private String destination;
     private String destination_name;
     private LocalDate departure_date;
     private LocalTime departure_time;
     private LocalDate arrival_date;
     private LocalTime arrival_time;
     private String carrier;
     private int stops;
     private int price;
}
