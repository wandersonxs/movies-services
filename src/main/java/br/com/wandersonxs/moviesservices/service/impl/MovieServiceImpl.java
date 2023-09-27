package br.com.wandersonxs.moviesservices.service.impl;

import br.com.wandersonxs.moviesservices.converter.MovieRequestDTOToMovieConverter;
import br.com.wandersonxs.moviesservices.converter.MovieToMovieResponseDTOConverter;
import br.com.wandersonxs.moviesservices.converter.PageMovieToPageMovieResponseDTOConverter;
import br.com.wandersonxs.moviesservices.converter.StringToMovieConverter;
import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.model.dto.criteria.MovieCriteriaDTO;
import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import br.com.wandersonxs.moviesservices.repository.MovieRepository;
import br.com.wandersonxs.moviesservices.repository.ProducerRepository;
import br.com.wandersonxs.moviesservices.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;
    private final StringToMovieConverter stringToMovieConverter;

    private final PageMovieToPageMovieResponseDTOConverter pageMovieToPageMovieResponseDTOConverter;
    private final MovieToMovieResponseDTOConverter movieToMovieResponseDTOConverter;
    private final MovieRequestDTOToMovieConverter movieRequestDTOToMovieConverter;

    private final ObjectMapper objectMapper;

    @Override
    public MovieResponseDTO saveMovie(Long id, MovieRequestDTO movieRequestDTO) throws NotFoundException {

        Movie movie = movieRequestDTOToMovieConverter.convert(movieRequestDTO);

        if (id != null) {
            movieRepository.findById(id).orElseThrow(NotFoundException::new);
            movie.setId(id);
        }

        movie.setProducers(producerRepository.saveAll(movie.getProducers()));
        return movieToMovieResponseDTOConverter.convert(movieRepository.save(movie));
    }

    @Override
    public void deleteMovie(Long id) throws NotFoundException {
        movieRepository.findById(id).orElseThrow(NotFoundException::new);
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> saveAll(List<String> linesMovies) {

        List<Movie> movies = new ArrayList<>();

        for (String lineMovie : linesMovies) {

            Movie movie = stringToMovieConverter.convert(lineMovie);

            if(movie.getProducers() != null && movie.getProducers().size() > 0){
                movies.add(movie);
            }
        }

        return movieRepository.saveAll(movies);
    }

    @Override
    public Page<MovieResponseDTO> getMovies(MovieCriteriaDTO movieCriteriaDTO, Pageable page) throws NotFoundException {
        Page<Movie> movies = movieRepository.findByLikeSearch(movieCriteriaDTO.getTitle(), movieCriteriaDTO.getStudio(), movieCriteriaDTO.getProducersId(), movieCriteriaDTO.getYear(), movieCriteriaDTO.getWinner(), page);
        if (movies.isEmpty()) throw new NotFoundException();
        return pageMovieToPageMovieResponseDTOConverter.convert(movies);
    }

    @Override
    public MovieResponseDTO getMovieById(Long id) throws NotFoundException {
        Movie movie = movieRepository.findById(id).orElseThrow(NotFoundException::new);
        return movieToMovieResponseDTOConverter.convert(movie);
    }

}
