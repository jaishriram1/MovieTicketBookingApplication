package com.example.TicketBookingApplication.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.TicketBookingApplication.services.TheatreService;
import com.example.TicketBookingApplication.entites.Theatre;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.TicketBookingApplication.dtos.TheatreDto;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {
    // Define your REST endpoints here
    @Autowired
    private TheatreService theatreService;
    
    @GetMapping
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        List<Theatre> theatres = theatreService.getAllTheatres();
        return ResponseEntity.ok(theatres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable Long id) {
        Theatre theatre = theatreService.getTheatreById(id);
        return ResponseEntity.ok(theatre);
    }

    @GetMapping("/{location}")
    public ResponseEntity<List<Theatre>> getTheatresByLocation(@PathVariable String location) {
        List<Theatre> theatres = theatreService.getTheatresByLocation(location);
        return ResponseEntity.ok(theatres);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theatre> createTheatre(@RequestBody TheatreDto theatreDto) {
        Theatre createdTheatre = theatreService.createTheatre(theatreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTheatre);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theatre> updateTheatre(@PathVariable Long id, @RequestBody Theatre theatre) {
        Theatre updatedTheatre = theatreService.updateTheatre(id, theatre);
        return ResponseEntity.ok(updatedTheatre);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }
}