package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.entity.Producer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListStringToProducersConverter implements Converter<List<String>, List<Producer>> {

    @Override
    public List<Producer> convert(List<String> producers) {
        return producers.stream().map(n -> Producer.builder()
                .name(n)
                .build()).toList();
    }

}
