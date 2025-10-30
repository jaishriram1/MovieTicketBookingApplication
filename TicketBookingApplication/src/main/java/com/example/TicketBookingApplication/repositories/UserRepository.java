package com.example.TicketBookingApplication.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.TicketBookingApplication.entites.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here if needed
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long Id);
}