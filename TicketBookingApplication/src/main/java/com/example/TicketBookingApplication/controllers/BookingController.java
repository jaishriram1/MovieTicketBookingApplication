package com.example.TicketBookingApplication.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.TicketBookingApplication.services.BookingService;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.TicketBookingApplication.entites.Booking;
import com.example.TicketBookingApplication.dtos.BookingDto;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.TicketBookingApplication.entites.BookingStatus;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Define endpoints for booking operations here
    @GetMapping("/getShowBookings/{id}")
    public ResponseEntity<List<Booking>> getShowBookings(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getShowBookings(id));
    }

    @GetMapping("/getUserBookings/{id}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getUserBookings(id));
    }

    @GetMapping("/getBookingsbystatus/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable BookingStatus status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDto bookingDto){
        Booking newBooking = bookingService.createBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }
    
    @PutMapping("/{id}/{bookingDto}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto){
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDto));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id){
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<Void> confirmBooking(@PathVariable Long id){
        bookingService.confirmBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<BookingStatus> updateBookingStatus(@PathVariable Long id, @RequestBody BookingStatus status){
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }
}