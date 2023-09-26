package br.com.wandersonxs.moviesservices.service.impl;

import br.com.wandersonxs.moviesservices.converter.StringToMovieConverter;
import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.repository.MovieRepository;
import br.com.wandersonxs.moviesservices.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final StringToMovieConverter stringToMovieConverter;

    @Override
    public ProducerDTO add(MovieRequestDTO movieRequestDTO, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<Movie> saveAll(List<String> linesMovies) {

        List<Movie> movies = new ArrayList<>();

        for (String lineMovie : linesMovies) {
            Movie movie = stringToMovieConverter.convert(lineMovie);
            movies.add(movie);
        }

        return movieRepository.saveAll(movies);
    }

}
