package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.model.dto.criteria.MovieCriteriaDTO;
import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.resource.api.MoviesApi;
import br.com.wandersonxs.moviesservices.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieResource implements MoviesApi {

    private final MovieService movieService;

    @Override
    public ResponseEntity<Page<MovieResponseDTO>> getMovies(String title, String studio, Integer year, Boolean winner, Pageable page) throws NotFoundException {
        Page<MovieResponseDTO> movieResponseDTOS = movieService.getMovies(new MovieCriteriaDTO(null, title, studio, null, year, winner), sort(page));
        return ResponseEntity.ok(movieResponseDTOS);
    }

    @Override
    public ResponseEntity<MovieResponseDTO> getMovieById(Long id) throws NotFoundException {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @Override
    public ResponseEntity<MovieResponseDTO> addMovie(MovieRequestDTO movieRequestDTO) throws NotFoundException {
        return new ResponseEntity<>(movieService.saveMovie(null, movieRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MovieResponseDTO> updateMovie(Long id, MovieRequestDTO movieRequestDTO) throws NotFoundException {
        return ResponseEntity.ok(movieService.saveMovie(id, movieRequestDTO));
    }

    @Override
    public void deleteMovie(Long id) throws NotFoundException {
        movieService.deleteMovie(id);
    }

    private Pageable sort(Pageable page) {
        String DEFAULT_SORT_BY_TITLE_ASC = "m.title";
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(DEFAULT_SORT_BY_TITLE_ASC));
    }
}

