package com.example.TicketBookingApplication.entites;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import jakarta.persistence.*;

import lombok.Data;

@Getter
@Setter
@Entity
@Data
public class Movie{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String genre;
    private Integer duration; // in minutes
    private String language;
    private LocalDate releaseDate;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<Show> shows;

}