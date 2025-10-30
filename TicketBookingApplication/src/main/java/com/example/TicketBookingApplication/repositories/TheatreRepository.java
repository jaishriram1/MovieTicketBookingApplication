package com.example.TicketBookingApplication.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TicketBookingApplication.entites.Theatre;
import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    // Define your data access methods here
    public List<Theatre> findByLocation(String location);
}