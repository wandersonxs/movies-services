package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerToProducerResponseDTOConverter implements Converter<Producer, ProducerResponseDTO> {

    private final ListMovieToListMovieResponseDTOConverter listMovieToListMovieResponseDTOConverter;

    @Override
    public ProducerResponseDTO convert(Producer producer) {
        return ProducerResponseDTO.builder()
                .id(producer.getId())
                .name(producer.getName())
                .movies(producer.getMovies() != null ? listMovieToListMovieResponseDTOConverter.convert(producer.getMovies()) : null)
                .build();
    }

}
