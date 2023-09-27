package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PageProducerToPageProducerResponseDTOConverter implements Converter<Page<Producer>, Page<ProducerResponseDTO>> {

    private final ListMovieToListMovieResponseDTOConverter listMovieToListMovieResponseDTOConverter;

    @Override
    public Page<ProducerResponseDTO> convert(Page<Producer> producers) {
        return new PageImpl<>(producers.getContent().stream()
                .map(p -> ProducerResponseDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .movies(listMovieToListMovieResponseDTOConverter.convert(p.getMovies()))
                        .build())
                .collect(Collectors.toList()), producers.getPageable(), producers.getTotalElements());
    }

}
