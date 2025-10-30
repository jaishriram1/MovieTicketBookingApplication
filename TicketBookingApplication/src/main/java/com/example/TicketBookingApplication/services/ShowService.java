package com.example.TicketBookingApplication.services;
import org.springframework.stereotype.Service;
import com.example.TicketBookingApplication.entites.Show;
import com.example.TicketBookingApplication.dtos.ShowDto;
import com.example.TicketBookingApplication.repositories.ShowRepository;
import com.example.TicketBookingApplication.repositories.MovieRepository;
import com.example.TicketBookingApplication.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.TicketBookingApplication.entites.Theatre;
import com.example.TicketBookingApplication.entites.Movie;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Show getShowById(Long id) {
        return showRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Show not found"));
    }

    public List<Show> getShowsByTheatre(Long theatreId){
        return showRepository.findAll().stream()
            .filter(show -> show.getTheatre().getId().equals(theatreId))
            .toList();
    }

    public List<Show> getShowsByMovie(Long movieId){
        return showRepository.findAll().stream()
            .filter(show -> show.getMovie().getId().equals(movieId))
            .toList();
    }

    public Show createShow(ShowDto showDto){
        Movie movie = movieRepository.findById(showDto.getMovieId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid movie ID"));
        Theatre theatre = theatreRepository.findById(showDto.getTheatreId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid theatre ID"));
        Show show=new Show();
        show.setShowTime(showDto.getShowTime());
        show.setPrice(showDto.getPrice());
        show.setMovie(movie);
        show.setTheatre(theatre);
        return showRepository.save(show);

    }

    public Show updateShow(Long id, Show showDetails){
        Show show = showRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Show not found"));
        show.setShowTime(showDetails.getShowTime());
        show.setPrice(showDetails.getPrice());
        show.setTheatre(showDetails.getTheatre());
        show.setMovie(showDetails.getMovie());
        return showRepository.save(show);
    }

    public void deleteShow(Long id){
        Show show = showRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Show not found"));
        showRepository.delete(show);
    }
}