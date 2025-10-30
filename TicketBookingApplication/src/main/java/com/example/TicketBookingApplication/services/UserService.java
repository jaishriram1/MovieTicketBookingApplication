package com.example.TicketBookingApplication.services;
import com.example.TicketBookingApplication.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TicketBookingApplication.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long Id){
        return userRepository.findUserById(Id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

}