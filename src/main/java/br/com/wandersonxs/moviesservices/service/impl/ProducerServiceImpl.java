package br.com.wandersonxs.moviesservices.service.impl;

import br.com.wandersonxs.moviesservices.converter.ListStringToProducersConverter;
import br.com.wandersonxs.moviesservices.helper.FileHelper;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import br.com.wandersonxs.moviesservices.repository.ProducerRepository;
import br.com.wandersonxs.moviesservices.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerRepository producerRepository;
    private final FileHelper fileHelper;
    private final ListStringToProducersConverter listStringToProducersConverter;

    @Override
    public List<Producer> saveAll(List<String> linhasCsv) {
        List<String> rawProducers = fileHelper.getRawProducers(linhasCsv);
        List<Producer> producers = listStringToProducersConverter.convert(rawProducers);
        assert producers != null;
        return producerRepository.saveAll(producers);
    }

    @Override
    public List<Producer> findByNameIn(List<String> names) {
        return producerRepository.findByNameIn(names);
    }

    @Override
    public ProducerResponseDTO getWinnersMoreThanOnce() {

        List<ProducerDTO> producerDTOS = new ArrayList<>();
        List<Producer> producers = producerRepository.findByWinnersMoreThanOnce();

        for (Producer producer : producers) {

            List<Movie> movies = producer.getMovies().stream().filter(Movie::isWinner).sorted(Comparator.comparing(Movie::getYear)).toList();

            for (int i = 0; i < movies.size(); i++) {
                Movie movieCurrent = movies.get(i);
                Movie movieNext = i + 1 < movies.size() ? movies.get(i + 1) : null;

                if (movieNext != null) {
                    ProducerDTO movieResponseDTO = new ProducerDTO(producer.getName(), movieNext.getYear() - movieCurrent.getYear(), movieCurrent.getYear(), movieNext.getYear());
                    producerDTOS.add(movieResponseDTO);

                } else {
                    ProducerDTO movieResponseDTO = new ProducerDTO(producer.getName(), null, movieCurrent.getYear(), null);
                    producerDTOS.add(movieResponseDTO);
                }

            }
        }
        return buildProducerResponseDTO(producerDTOS);
    }

    private ProducerResponseDTO buildProducerResponseDTO(List<ProducerDTO> movieDTOS) {
        ProducerResponseDTO movieResponseDTO = new ProducerResponseDTO();

        List<String> producers = movieDTOS.stream().map(ProducerDTO::getProducer).distinct().toList();

        producers.forEach(n -> {
            ProducerDTO movieDTO = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).min(Comparator.comparing(ProducerDTO::getInterval)).get();
            movieResponseDTO.getMin().add(movieDTO);
        });

        producers.forEach(n -> {
            ProducerDTO movieDTO = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).max(Comparator.comparing(ProducerDTO::getInterval)).get();
            movieResponseDTO.getMax().add(movieDTO);
        });

        return movieResponseDTO;
    }
}
