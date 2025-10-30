package com.example.TicketBookingApplication.services;
import com.example.TicketBookingApplication.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TicketBookingApplication.entites.Movie;
import com.example.TicketBookingApplication.dtos.MovieDto;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    // Define your business logic methods here

    public List<Movie> getAllMovies() {
        // Logic to fetch all movies
        return movieRepository.findAll();
    }

    public Movie getMovieByName(String name) {
        // Logic to fetch a movie by name
        return movieRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Movie getMovieByTitle(String title) {
        // Logic to fetch a movie by name
        return movieRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public List<Movie> getMoviesByGenre(String genre) {
        // Logic to fetch movies by genre
        List<Movie> list = movieRepository.findByGenre(genre)
                .orElseThrow(()-> new RuntimeException("Genre not available"));
        return list;
    }

    public List<Movie> getMoviesByLanguage(String language) {
        // Logic to fetch movies by language
        Optional<List<Movie>> list = movieRepository.findByLanguage(language);
        if(list.isPresent()){
            return list.get();
        }
        throw new RuntimeException("Movie as per Language not found");
    }

    public Movie createMovie(MovieDto movieDto) {
        // Logic to create a new movie
        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setDescription(movieDto.getDescription());
        movie.setGenre(movieDto.getGenre());
        movie.setDuration(movieDto.getDuration());
        movie.setLanguage(movieDto.getLanguage());
        movie.setReleaseDate(movieDto.getReleaseDate());
        return movieRepository.save(movie);
    }

    public Movie updateMovieById(Long id, MovieDto movieDto) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setName(movieDto.getName());
        movie.setDescription(movieDto.getDescription());
        movie.setGenre(movieDto.getGenre());
        movie.setDuration(movieDto.getDuration());
        movie.setLanguage(movieDto.getLanguage());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movieRepository.save(movie);
        return movie;
    }

    public void deleteMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movieRepository.delete(movie);
    }
}
