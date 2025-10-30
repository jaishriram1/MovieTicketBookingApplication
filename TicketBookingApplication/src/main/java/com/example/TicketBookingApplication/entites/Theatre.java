package com.example.TicketBookingApplication.entites;
import lombok.Data;
import jakarta.persistence.Entity;
import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;

@Entity
@Data
public class Theatre{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private int totalSeats;
    private int availableSeats;

    @OneToMany(mappedBy = "theatre", fetch = FetchType.LAZY)
    private List<Show> shows;
}
