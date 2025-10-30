package com.example.TicketBookingApplication.repositories;
import com.example.TicketBookingApplication.entites.BookingStatus;
import com.example.TicketBookingApplication.entites.Show;
import com.example.TicketBookingApplication.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.TicketBookingApplication.entites.Booking;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Custom query methods can be defined here if needed
    List<Booking> findByUser(User user);
    List<Booking> findByShow(Show show);
    List<Booking> findByStatus(BookingStatus status);
}