package com.example.TicketBookingApplication.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TicketBookingApplication.entites.Show;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    // Define your data access methods here

    Optional<Show> findShowById(Long aLong);
}