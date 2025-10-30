package com.example.TicketBookingApplication.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.TicketBookingApplication.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import com.example.TicketBookingApplication.dtos.RegisterRequestDto;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;

    // Define admin-specific endpoints here
    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(authenticationService.registerAdminUser(registerRequestDto));
    }

    // @PostMapping("/createTheatreOwner")
    // public ResponseEntity<User> createTheatreOwner(@RequestBody LoginRequestDto loginRequestDto){
    //     return ResponseEntity.ok(authenticationService.loginTheatreOwner(loginRequestDto));
    // }
}