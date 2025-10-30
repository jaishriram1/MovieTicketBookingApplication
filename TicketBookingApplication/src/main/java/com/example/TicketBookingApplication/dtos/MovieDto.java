package com.example.TicketBookingApplication.dtos;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;
import java.time.LocalDate;

@Getter
@Setter
@Data
public class MovieDto {

    private String name;
    private String description;
    private String genre;
    private Integer duration; // in minutes
    private String language;
    private LocalDate releaseDate;
}