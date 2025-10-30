package com.example.TicketBookingApplication.dtos;
import lombok.Data;

@Data
public class TheatreDto {
    private String name;
    private String location;
    private int totalSeats;
    private int availableSeats;

}