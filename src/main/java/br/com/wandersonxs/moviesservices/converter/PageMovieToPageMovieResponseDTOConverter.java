package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PageMovieToPageMovieResponseDTOConverter implements Converter<Page<Movie>, Page<MovieResponseDTO>> {

    @Nullable
    @Synchronized
    @Override
    public Page<MovieResponseDTO> convert(Page<Movie> movies) {
        return new PageImpl<>(movies.getContent().stream()
                .map(m -> MovieResponseDTO.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .studio(m.getStudio())
                        .year(m.getYear())
                        .winner(m.isWinner())
                        .build())
                .collect(Collectors.toList()), movies.getPageable(), movies.getTotalElements());
    }

}
