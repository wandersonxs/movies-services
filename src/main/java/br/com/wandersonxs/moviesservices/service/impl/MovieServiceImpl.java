package br.com.wandersonxs.moviesservices.service.impl;

import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.repository.MovieRepository;
import br.com.wandersonxs.moviesservices.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieDTO add(MovieRequestDTO movieRequestDTO, HttpServletRequest request) {
        return null;
    }

    @Override
    public List<Movie> add(List<Movie> movies) {
        return movieRepository.saveAll(movies);
    }

    @Override
    public MovieResponseDTO getMovieWinners() {

        List<MovieDTO> movieResponseDTOS = new ArrayList<>();
        List<Movie> movies = movieRepository.findByWinners();

        Movie movieCurrent = null;
        Movie movieNext = null;

        for (int i = 0; i < movies.size(); i++) {

            movieCurrent = movies.get(i);
            int nextElement = i + 1;

            if (i == 0) {
                MovieDTO movieResponseDTO = new MovieDTO(
                        movies.get(i).getProducer(), movies.get(nextElement).getYear() - movies.get(i).getYear(), movies.get(i).getYear(), movies.get(nextElement).getYear());

                movieResponseDTOS.add(movieResponseDTO);
                continue;
            }

            if (nextElement < movies.size()) {
                movieNext = movies.get(nextElement);

                if (movieCurrent.getProducer().equals(movieNext.getProducer())) {

                    MovieDTO movieResponseDTO = new MovieDTO(
                            movies.get(i).getProducer(), movies.get(nextElement).getYear() - movies.get(i).getYear(), movies.get(i).getYear(), movies.get(nextElement).getYear());

                    movieResponseDTOS.add(movieResponseDTO);
                } else {
                    MovieDTO movieResponseDTO = new MovieDTO(
                            movies.get(i).getProducer(), null, movies.get(i).getYear(), null);

                    movieResponseDTOS.add(movieResponseDTO);
                }

            }

        }
        movieResponseDTOS.forEach(System.out::println);

        return buildMovieResponseDTO(movieResponseDTOS);


    }

    private MovieResponseDTO buildMovieResponseDTO(List<MovieDTO> movieDTOS) {
        MovieResponseDTO movieResponseDTO = new MovieResponseDTO();

        List<String> producers = movieDTOS.stream().map(MovieDTO::getProducer).distinct().toList();

        producers.forEach(n -> {
            MovieDTO movieDTO = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).min(Comparator.comparing(MovieDTO::getInterval)).get();
            movieResponseDTO.getMin().add(movieDTO);
        });

        producers.forEach(n -> {
            MovieDTO movieDTO = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).max(Comparator.comparing(MovieDTO::getInterval)).get();
            movieResponseDTO.getMax().add(movieDTO);
        });

        return movieResponseDTO;
    }

}
