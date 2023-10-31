package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Movie;
import mk.finki.ukim.mk.lab.repository.MovieRepository;
import mk.finki.ukim.mk.lab.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl( MovieRepository movieRepository ) {
        this.movieRepository=movieRepository;
    }

    @Override
    public List<Movie> listAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> searchMovies( String text ) {
        return movieRepository.searchMovies(text);
    }

    @Override
    public List<Movie> searchMovies( String text, double rating ) {
        return movieRepository.searchMovies(text,rating);
    }

    @Override
    public List<Movie> searchMovies( double rating ) {
        return movieRepository.searchMovies(rating);
    }
}
