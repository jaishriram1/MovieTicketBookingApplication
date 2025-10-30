package com.example.TicketBookingApplication.jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import com.example.TicketBookingApplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException
{
    final String authorizationHeader = request.getHeader("Authorization");
    final String jwtToken;
    final String username;

    if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
        filterChain.doFilter(request, response);
        return;
    }
    // extract JWT token from Header
    jwtToken = authorizationHeader.substring(7);
    username = jwtService.extractUsername(jwtToken);

    if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
        var userdetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(jwtService.isTokenValid(jwtToken, userdetails)){
            List<SimpleGrantedAuthority> roles = userdetails.getRoles().stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails, null, roles);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
}