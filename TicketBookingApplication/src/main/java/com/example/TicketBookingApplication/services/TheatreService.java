package com.example.TicketBookingApplication.services;  
import com.example.TicketBookingApplication.dtos.TheatreDto;
import com.example.TicketBookingApplication.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TicketBookingApplication.entites.Theatre;
import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    // Define your business logic methods here
    @Autowired
    private TheatreRepository theatreRepository;

    public List<Theatre> getAllTheatres() {
        // Logic to fetch all theatres
        return theatreRepository.findAll();
    }

    public Theatre getTheatreById(Long id) {
        // Logic to fetch a theatre by ID
        return theatreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Theatre not found"));
    }

    public List<Theatre> getTheatresByLocation(String location){
        Optional<List<Theatre>> list = Optional.ofNullable(theatreRepository.findByLocation(location));
        if(list.isPresent()){
            return list.get();
        }
        throw new RuntimeException("Theatre as per Location not found");
    }

    public Theatre createTheatre(TheatreDto theatreDto) {
        // Logic to create a new theatre
        Theatre theatre = new Theatre();
        theatre.setName(theatreDto.getName());
        theatre.setLocation(theatreDto.getLocation());
        theatre.setTotalSeats(theatreDto.getTotalSeats());
        theatre.setAvailableSeats(theatreDto.getTotalSeats());
        return theatreRepository.save(theatre);
    }

    public Theatre updateTheatre(Long id, Theatre theatreDetails){
        Theatre theatre = theatreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Theatre not found"));
        theatre.setName(theatreDetails.getName());
        theatre.setLocation(theatreDetails.getLocation());
        theatre.setTotalSeats(theatreDetails.getTotalSeats());
        theatre.setAvailableSeats(theatreDetails.getAvailableSeats());
        return theatreRepository.save(theatre);
    }

    public void deleteTheatre(Long id) {
        // Logic to delete a theatre by ID
        Theatre theatre = theatreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Theatre not found"));
        theatreRepository.delete(theatre);
    }
}