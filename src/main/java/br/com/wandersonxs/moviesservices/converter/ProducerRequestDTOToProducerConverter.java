package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.request.MovieRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.request.ProducerRequestDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerRequestDTOToProducerConverter implements Converter<ProducerRequestDTO, Producer> {

    private final ListProducerRequestDTOToListProducerDTOConverter listProducerRequestDTOToListProducerDTOConverter;

    @Override
    public Producer convert(ProducerRequestDTO producerRequestDTO) {
        return Producer.builder()
                .id(producerRequestDTO.getId())
                .name(producerRequestDTO.getName())
                .build();
    }

}
