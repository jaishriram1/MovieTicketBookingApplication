package com.example.TicketBookingApplication.dtos;
import lombok.Data;
import lombok.Builder;
    
@Data
@Builder
public class LoginRequestDto {
    private String name;
    private String email;
    private String password;
}