package br.com.wandersonxs.moviesservices.service.impl;

import br.com.wandersonxs.moviesservices.converter.ListStringToProducersConverter;
import br.com.wandersonxs.moviesservices.helper.FileHelper;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerWinnerResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducersWinnersResponseDTO;
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
        return producerRepository.saveAll(producers);
    }

    @Override
    public List<Producer> findByNameIn(List<String> names) {
        return producerRepository.findByNameIn(names);
    }

    @Override
    public ProducersWinnersResponseDTO getWinnersMoreThanOnce() {

        List<ProducerWinnerResponseDTO> producerDTOS = new ArrayList<>();
        List<Producer> producers = producerRepository.findByWinnersMoreThanOnce();

        for (Producer producer : producers) {

            List<Movie> movies = producer.getMovies().stream().filter(Movie::isWinner).sorted(Comparator.comparing(Movie::getYear)).toList();

            for (int i = 0; i < movies.size(); i++) {
                Movie movieCurrent = movies.get(i);
                Movie movieNext = i + 1 < movies.size() ? movies.get(i + 1) : null;

                if (movieNext != null) {
                    ProducerWinnerResponseDTO movieResponseDTO = new ProducerWinnerResponseDTO(producer.getName(), movieNext.getYear() - movieCurrent.getYear(), movieCurrent.getYear(), movieNext.getYear());
                    producerDTOS.add(movieResponseDTO);

                } else {
                    ProducerWinnerResponseDTO movieResponseDTO = new ProducerWinnerResponseDTO(producer.getName(), null, movieCurrent.getYear(), null);
                    producerDTOS.add(movieResponseDTO);
                }

            }
        }
        return buildProducerResponseDTO(producerDTOS);
    }

    private ProducersWinnersResponseDTO buildProducerResponseDTO(List<ProducerWinnerResponseDTO> movieDTOS) {
        ProducersWinnersResponseDTO movieResponseDTO = new ProducersWinnersResponseDTO();
        List<ProducerWinnerResponseDTO> producersDTOMin = new ArrayList<>();
        List<ProducerWinnerResponseDTO> producersDTOMax = new ArrayList<>();

        List<String> producers = movieDTOS.stream().map(ProducerWinnerResponseDTO::getProducer).distinct().toList();

        producers.forEach(n -> {

            // TODO: PEGAR SOMENTE ATÉ O SEGUNDO PREMIO
//            ProducerDTO movieDTOMin = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).min(Comparator.comparing(ProducerDTO::getInterval)).get();
//            producersDTOMin.add(movieDTOMin);

            List<ProducerWinnerResponseDTO> teste = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).limit(2).toList();

//            ProducerDTO movieDTOMin = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).limit(2).min(Comparator.comparing(ProducerDTO::getInterval)).get();
            ProducerWinnerResponseDTO movieDTOMin = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).findFirst().get();
            producersDTOMin.add(movieDTOMin);

            ProducerWinnerResponseDTO movieDTOMax = movieDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).max(Comparator.comparing(ProducerWinnerResponseDTO::getInterval)).get();
            producersDTOMax.add(movieDTOMax);
        });

        movieResponseDTO.setMin(producersDTOMin.stream().sorted(Comparator.comparing(ProducerWinnerResponseDTO::getInterval)).toList());
        movieResponseDTO.setMax(producersDTOMax.stream().sorted(Comparator.comparing(ProducerWinnerResponseDTO::getInterval).reversed()).toList());

        return movieResponseDTO;
    }
}
