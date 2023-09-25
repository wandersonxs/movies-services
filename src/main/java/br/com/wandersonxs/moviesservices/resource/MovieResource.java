package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieResource {

    private final MovieService movieService;

    @RequestMapping(value = "/winners", method = RequestMethod.GET)
    public MovieResponseDTO getMovieWinners(){
        MovieResponseDTO movieResponseDTO = movieService.getMovieWinners();
        return movieResponseDTO;
    }




}
