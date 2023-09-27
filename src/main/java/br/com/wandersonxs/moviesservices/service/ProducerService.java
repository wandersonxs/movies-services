package br.com.wandersonxs.moviesservices.service;

import br.com.wandersonxs.moviesservices.handler.exception.BusinessException;
import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.model.dto.criteria.ProducerCriteriaDTO;
import br.com.wandersonxs.moviesservices.model.dto.request.ProducerRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducersWinnersResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProducerService {

    void saveAll(List<String> producers);

    List<Producer> findByNameIn(List<String> names);

    ProducersWinnersResponseDTO getWinnersMoreThanOnce();

    Page<ProducerResponseDTO> getProducers(ProducerCriteriaDTO producerCriteriaDTO, Pageable sort) throws NotFoundException;

    void deleteProducer(Long id) throws NotFoundException, BusinessException;

    ProducerResponseDTO getProducerById(Long id) throws NotFoundException;

    ProducerResponseDTO saveProducer(Long id, ProducerRequestDTO producerRequestDTO) throws NotFoundException;
}
