package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.request.ProducerRequestDTO;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListProducerRequestDTOToListProducerDTOConverter implements Converter<List<ProducerRequestDTO>, List<Producer>> {

    @Override
    public List<Producer> convert(List<ProducerRequestDTO> producerRequestDTOS) {
        return producerRequestDTOS.stream()
                .map(p -> Producer.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .build()).toList();
    }
}