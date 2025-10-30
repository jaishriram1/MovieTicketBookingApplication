package com.example.TicketBookingApplication.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import com.example.TicketBookingApplication.services.AuthenticationService;
import com.example.TicketBookingApplication.dtos.RegisterRequestDto;
import com.example.TicketBookingApplication.entites.User;
import com.example.TicketBookingApplication.dtos.LoginRequestDto;
import com.example.TicketBookingApplication.dtos.LoginResponseDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    // Define authentication endpoints here (e.g., login, register)
    @PostMapping("/register")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerRequestDto));
    }

    @PostMapping("/loginUser")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authenticationService.loginUser(loginRequestDto));
    }

}