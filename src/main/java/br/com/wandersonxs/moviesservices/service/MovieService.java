package br.com.wandersonxs.moviesservices.service;

import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MovieService {

    MovieDTO add(MovieRequestDTO movieRequestDTO, HttpServletRequest request);

    List<Movie> add(List<Movie> movies);


    MovieResponseDTO getMovieWinners();
}
