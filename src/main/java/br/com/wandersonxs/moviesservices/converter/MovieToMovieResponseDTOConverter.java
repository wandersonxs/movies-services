package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieToMovieResponseDTOConverter implements Converter<Movie, MovieResponseDTO> {

    private final ListProducerToListProducerResponseDTOConverter listProducerToListProducerResponseDTOConverter;

    @Override
    public MovieResponseDTO convert(Movie movie) {
        return MovieResponseDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .studio(movie.getStudio())
                .year(movie.getYear())
                .winner(movie.isWinner())
                .producers(listProducerToListProducerResponseDTOConverter.convert(movie.getProducers()))
                .build();
    }

}
