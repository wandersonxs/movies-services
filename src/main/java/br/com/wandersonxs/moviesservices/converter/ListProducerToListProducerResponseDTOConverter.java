package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListProducerToListProducerResponseDTOConverter implements Converter<List<Producer>, List<ProducerResponseDTO>> {

    @Override
    public List<ProducerResponseDTO> convert(List<Producer> producers) {
         return producers.stream()
                .map(p -> ProducerResponseDTO.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .build()).toList();
    }
}