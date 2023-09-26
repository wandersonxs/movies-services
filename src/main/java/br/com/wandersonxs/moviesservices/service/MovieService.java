package br.com.wandersonxs.moviesservices.service;

import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MovieService {

    ProducerDTO add(MovieRequestDTO movieRequestDTO, HttpServletRequest request);

    List<Movie> saveAll(List<String> movies);

}
