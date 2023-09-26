package br.com.wandersonxs.moviesservices.service;

import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.model.dto.criteria.MovieCriteriaDTO;
import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    List<Movie> saveAll(List<String> movies);

    Page<MovieResponseDTO> getMovies(MovieCriteriaDTO movieCriteriaDTO, Pageable page) throws NotFoundException;

    MovieResponseDTO getMovieById(Long id) throws NotFoundException;

    MovieResponseDTO saveMovie(Long id, MovieRequestDTO movieRequestDTO) throws NotFoundException;

    void deleteMovie(Long id) throws NotFoundException;

}
