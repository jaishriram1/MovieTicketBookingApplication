package com.example.TicketBookingApplication.dtos;
import lombok.Data;
import lombok.Builder;
import java.util.Set;

@Data
@Builder
public class LoginResponseDto {
    private String jwtToken;
    private String username;
    private Set<String> roles;
}