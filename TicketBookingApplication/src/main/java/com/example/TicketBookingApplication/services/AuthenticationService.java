package com.example.TicketBookingApplication.services;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.TicketBookingApplication.dtos.RegisterRequestDto;
import com.example.TicketBookingApplication.entites.User;
import com.example.TicketBookingApplication.repositories.UserRepository;  
import org.springframework.security.crypto.password.PasswordEncoder; 
import com.example.TicketBookingApplication.dtos.LoginRequestDto;
import com.example.TicketBookingApplication.dtos.LoginResponseDto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import com.example.TicketBookingApplication.jwt.JwtService;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    
    public User registerNormalUser(RegisterRequestDto registerRequestDto) {
        // Implement registration logic using userService
        if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");

        User newUser = new User();
        newUser.setName(registerRequestDto.getUsername());
        newUser.setPassword(registerRequestDto.getPassword());// In real applications, ensure to hash
        newUser.setRoles(new ArrayList<>(roles));
        return userRepository.save(newUser);
    }

    public User registerAdminUser(RegisterRequestDto registerRequestDto) {
        // Implement registration logic for admin users
        if(userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");

        User newUser = new User();
        newUser.setName(registerRequestDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword())); // In real applications, ensure to hash
        newUser.setEmail(registerRequestDto.getEmail());
        newUser.setRoles((List<String>) roles);
        return userRepository.save(newUser);
    }

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        if(!user.getName().equals(loginRequestDto.getName()) && !passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtService.generateToken(user);
        return LoginResponseDto.builder().jwtToken(token).username(user.getUsername()).roles(new HashSet<>(user.getRoles())).build();
    }

}