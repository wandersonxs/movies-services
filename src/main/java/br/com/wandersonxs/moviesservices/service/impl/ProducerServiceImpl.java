package br.com.wandersonxs.moviesservices.service.impl;

import br.com.wandersonxs.moviesservices.converter.*;
import br.com.wandersonxs.moviesservices.handler.exception.BusinessException;
import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.helper.FileHelper;
import br.com.wandersonxs.moviesservices.model.dto.criteria.ProducerCriteriaDTO;
import br.com.wandersonxs.moviesservices.model.dto.request.ProducerRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerWinnerResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducersWinnersResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import br.com.wandersonxs.moviesservices.repository.ProducerRepository;
import br.com.wandersonxs.moviesservices.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PageProducerToPageProducerResponseDTOConverter pageProducerToPageProducerResponseDTOConverter;
    private final ProducerRequestDTOToProducerConverter producerRequestDTOToProducerConverter;
    private final ProducerToProducerResponseDTOConverter producerToProducerResponseDTOConverter;

    @Override
    public void saveAll(List<String> linhasCsv) {
        List<String> rawProducers = fileHelper.getRawProducers(linhasCsv).stream().filter(n -> !"".equals(n)).toList();
        List<Producer> producers = listStringToProducersConverter.convert(rawProducers);
        listStringToProducersConverter.convert(rawProducers);
        producerRepository.saveAll(producers);
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

    @Override
    public Page<ProducerResponseDTO> getProducers(ProducerCriteriaDTO producerCriteriaDTO, Pageable page) throws NotFoundException {
        Page<Producer> producers = producerRepository.findByLikeSearch(producerCriteriaDTO.getName(), page);
        if (producers.isEmpty()) throw new NotFoundException();
        return pageProducerToPageProducerResponseDTOConverter.convert(producers);
    }

    @Override
    public void deleteProducer(Long id) throws NotFoundException, BusinessException {

        Producer producer = producerRepository.findById(id).orElseThrow(NotFoundException::new);
        if (producer.getMovies().size() > 0) {
            throw new BusinessException("Producer has movies bound to it. Delete the association of movies before deleting producer.");
        }
        producerRepository.deleteById(id);

    }

    @Override
    public ProducerResponseDTO getProducerById(Long id) throws NotFoundException {
        Producer producer = producerRepository.findById(id).orElseThrow(NotFoundException::new);
        return producerToProducerResponseDTOConverter.convert(producer);
    }

    @Override
    public ProducerResponseDTO saveProducer(Long id, ProducerRequestDTO producerRequestDTO) throws NotFoundException {
        Producer producer = producerRequestDTOToProducerConverter.convert(producerRequestDTO);

        if (id != null) {
            producerRepository.findById(id).orElseThrow(NotFoundException::new);
            producer.setId(id);
        }

        return producerToProducerResponseDTOConverter.convert(producerRepository.save(producer));
    }

    private ProducersWinnersResponseDTO buildProducerResponseDTO(List<ProducerWinnerResponseDTO> producerWinnerResponseDTOS) {

        List<ProducerWinnerResponseDTO> producersDTOMin = new ArrayList<>();
        List<ProducerWinnerResponseDTO> producersDTOMax = new ArrayList<>();

        List<String> producers = producerWinnerResponseDTOS.stream().map(ProducerWinnerResponseDTO::getProducer).distinct().toList();

        producers.forEach(n -> {

            ProducerWinnerResponseDTO producerWinnerResponseDTOMin = producerWinnerResponseDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).findFirst().get();
            producersDTOMin.add(producerWinnerResponseDTOMin);

            ProducerWinnerResponseDTO producerWinnerResponseDTOMax = producerWinnerResponseDTOS.stream().filter(k -> k.getProducer().equals(n) && k.getInterval() != null).max(Comparator.comparing(ProducerWinnerResponseDTO::getInterval)).get();
            producersDTOMax.add(producerWinnerResponseDTOMax);
        });

        return new ProducersWinnersResponseDTO(List.of(producersDTOMin.stream().min(Comparator.comparing(ProducerWinnerResponseDTO::getInterval)).get()),
                List.of(producersDTOMax.stream().max(Comparator.comparing(ProducerWinnerResponseDTO::getInterval)).get()));

    }
}
