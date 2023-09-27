package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieRequestDTOToMovieConverter implements Converter<MovieRequestDTO, Movie> {

    private final ListProducerRequestDTOToListProducerDTOConverter listProducerRequestDTOToListProducerDTOConverter;

    @Override
    public Movie convert(MovieRequestDTO movieRequestDTO) {
        return Movie.builder()
                .id(movieRequestDTO.getId())
                .title(movieRequestDTO.getTitle())
                .studio(movieRequestDTO.getStudio())
                .year(movieRequestDTO.getYear())
                .winner(movieRequestDTO.isWinner())
                .producers(listProducerRequestDTOToListProducerDTOConverter.convert(movieRequestDTO.getProducers()))
                .build();
    }

}
