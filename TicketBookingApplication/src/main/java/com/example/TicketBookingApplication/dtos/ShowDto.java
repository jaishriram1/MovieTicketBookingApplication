package com.example.TicketBookingApplication.dtos;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ShowDto {

    private LocalDateTime showTime; // Changed to String for simplicity
    private Double price;
    private Long theatreId;
    private Long movieId;

}