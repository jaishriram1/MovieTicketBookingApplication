package com.example.TicketBookingApplication.dtos;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
}