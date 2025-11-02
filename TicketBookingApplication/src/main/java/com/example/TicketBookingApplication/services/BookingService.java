package com.example.TicketBookingApplication.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TicketBookingApplication.repositories.BookingRepository;
import com.example.TicketBookingApplication.entites.Booking;
import com.example.TicketBookingApplication.entites.Show;
import com.example.TicketBookingApplication.dtos.BookingDto;
import com.example.TicketBookingApplication.entites.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.TicketBookingApplication.entites.User;
import java.time.LocalDate;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowService showService;

    @Autowired
    private UserService userService;

    public List<Booking> getShowBookings(Long showId) {
        return bookingRepository.findAll().stream()
            .filter(booking -> booking.getShow().getId().equals(showId))
            .toList();
    }

    public Booking updateBooking(Long id, BookingDto bookingDto){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Booking Not Found"));
        booking.setBookingDate(bookingDto.getBookingDate());
        booking.setSeatNumbers(bookingDto.getSeatNumbers());
        booking.setPrice(bookingDto.getPrice());
        booking.setNumberOfSeats(bookingDto.getNumberOfSeats());
        booking.setBookingStatus(bookingDto.getBookingStatus());
        return booking;
    }

    public Booking createBooking(BookingDto bookingDto) {
        Show show = showService.getShowById(bookingDto.getShowId());

        if(!seatsAvailable(show.getId(), bookingDto.getNumberOfSeats())){
            throw new IllegalArgumentException("Sorry, not enough seats available");
        }

        if(bookingDto.getSeatNumbers().size()!=bookingDto.getNumberOfSeats()){
            throw new IllegalArgumentException("Number of seats and seat numbers count do not match");
        }

        if(validateDuplicate(show.getId(), bookingDto.getSeatNumbers())){
            throw new RuntimeException("One or more selected seats are already booked");
        }
        User user = userService.getUserById(bookingDto.getUserId());
        
        Booking booking = new Booking();
        booking.setBookingDate(LocalDate.now());
        booking.setSeatNumbers(bookingDto.getSeatNumbers());
        booking.setNumberOfSeats(bookingDto.getNumberOfSeats());
        booking.setPrice(calculatePrice(show.getPrice(), bookingDto.getNumberOfSeats()));
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShow(show);
        booking.setUser(user);
        bookingRepository.save(booking);
        return booking;
    }
    
    public Double calculatePrice(Double showPrice, Integer numberOfSeats){
        return showPrice * numberOfSeats;
    }

    public boolean seatsAvailable(Long showId, Integer requestedSeats){
        Show show = showService.getShowById(showId);
        int bookedSeats = show.getBookings().stream()
            .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELED)
            .mapToInt(Booking::getNumberOfSeats)
            .sum();
        return (show.getTheatre().getTotalSeats() - bookedSeats) >= requestedSeats;
    }

    public boolean validateDuplicate(Long showId, List<String> seatNumbers){
        Show show = showService.getShowById(showId);
        
        Set<String> occupiedSeats = show.getBookings().stream()
                .filter(b -> b.getBookingStatus() != BookingStatus.CANCELED)
                .flatMap(b -> b.getSeatNumbers().stream())
                .collect(Collectors.toSet());
        List<String> duplicates = seatNumbers.stream()
                .filter(occupiedSeats::contains)
                .toList(); 
        return !duplicates.isEmpty();
    }
    
    public List<Booking> getUserBookings(Long userId){
        User user = userService.getUserById(userId);
        return bookingRepository.findByUser(user);
    }

    public void confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid Booking Id"));
        if(confirmingStatusIfPending(booking.getId())){
            throw new RuntimeException("Booking is not available");
        }
        else{
            // payment processing logic here
            booking.setBookingStatus(BookingStatus.COMPLETED);
            bookingRepository.save(booking);
        }
    }

    public boolean confirmingStatusIfPending(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Invalid booking ID"));
        return booking.getBookingStatus() != BookingStatus.PENDING;
    }

    public void cancelBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        
        if(validCancellation(booking)){
            // Payment rollback logic here if needed
            booking.setBookingStatus(BookingStatus.CANCELED);
            bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Cancellation period has expired");
        }
    }

    public boolean validCancellation(Booking booking){
        LocalDateTime cancellationTime = booking.getShow().getShowTime();
        return !LocalDateTime.now().isAfter(cancellationTime.minusHours(2));
    }

    // updating bookingStatus logic
    public BookingStatus updateBookingStatus(Long id, BookingStatus bookingStatus){
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setBookingStatus(bookingStatus);
        bookingRepository.save(booking);
        return booking.getBookingStatus();
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByBookingStatus(status);
    }
}