package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListMovieToListMovieResponseDTOConverter implements Converter<List<Movie>, List<MovieResponseDTO>> {

    private final ListProducerToListProducerResponseDTOConverter listProducerToListProducerResponseDTOConverter;

    @Override
    public List<MovieResponseDTO> convert(List<Movie> movies) {
        return movies.stream()
                .map(m -> MovieResponseDTO.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .studio(m.getStudio())
                        .year(m.getYear())
                        .winner(m.isWinner())
                        .producers(listProducerToListProducerResponseDTOConverter.convert(m.getProducers()))
                        .build()).toList();
    }
}