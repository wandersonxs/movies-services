package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/producers")
public class ProducerResource {

    private final ProducerService producerService;

    @RequestMapping(value = "/movies/winners", method = RequestMethod.GET)
    public ProducerResponseDTO getProducersWinners(){
        return producerService.getWinnersMoreThanOnce();
    }

}
