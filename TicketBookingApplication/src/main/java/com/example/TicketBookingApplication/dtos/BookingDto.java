package com.example.TicketBookingApplication.dtos;
import java.time.LocalDate;
import java.util.List;

import com.example.TicketBookingApplication.entites.BookingStatus;
import lombok.Data;

@Data
public class BookingDto {

    private LocalDate bookingDate;
    private Integer numberOfSeats;
    private Double price;
    private BookingStatus bookingStatus;
    private Long userId;
    private Long showId;
    private List<String> seatNumbers;

}