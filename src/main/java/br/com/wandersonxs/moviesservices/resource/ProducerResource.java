package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.handler.exception.BusinessException;
import br.com.wandersonxs.moviesservices.handler.exception.NotFoundException;
import br.com.wandersonxs.moviesservices.model.dto.criteria.ProducerCriteriaDTO;
import br.com.wandersonxs.moviesservices.model.dto.request.ProducerRequestDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducersWinnersResponseDTO;
import br.com.wandersonxs.moviesservices.resource.api.ProducersApi;
import br.com.wandersonxs.moviesservices.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerResource implements ProducersApi {

    private final ProducerService producerService;

    public ResponseEntity<ProducersWinnersResponseDTO> getProducersWinners() {
        return ResponseEntity.ok(producerService.getWinnersMoreThanOnce());
    }

    @Override
    public ResponseEntity<Page<ProducerResponseDTO>> getProducers(String name, Pageable page) throws NotFoundException {
        Page<ProducerResponseDTO> producerResponseDTOS = producerService.getProducers(new ProducerCriteriaDTO(null, name), sort(page));
        return ResponseEntity.ok(producerResponseDTOS);
    }

    @Override
    public ResponseEntity<ProducerResponseDTO> getProducerById(Long id) throws NotFoundException {
        return ResponseEntity.ok(producerService.getProducerById(id));
    }

    @Override
    public ResponseEntity<ProducerResponseDTO> addProducer(ProducerRequestDTO producerRequestDTO) throws NotFoundException {
        return new ResponseEntity<>(producerService.saveProducer(null, producerRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProducerResponseDTO> updateProducer(Long id, ProducerRequestDTO producerRequestDTO) throws NotFoundException {
        return new ResponseEntity<>(producerService.saveProducer(id, producerRequestDTO), HttpStatus.OK);
    }

    @Override
    public void deleteProducer(Long id) throws NotFoundException, BusinessException {
        producerService.deleteProducer(id);
    }

    private Pageable sort(Pageable page) {
        String DEFAULT_SORT_BY_NAME_ASC = "p.name";
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by(DEFAULT_SORT_BY_NAME_ASC));
    }
}
