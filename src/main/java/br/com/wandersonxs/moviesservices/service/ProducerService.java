package br.com.wandersonxs.moviesservices.service;

import br.com.wandersonxs.moviesservices.model.dto.response.ProducersWinnersResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Producer;

import java.util.List;

public interface ProducerService {

    List<Producer> saveAll(List<String> producers);

    List<Producer> findByNameIn(List<String> names);

    ProducersWinnersResponseDTO getWinnersMoreThanOnce();

}
