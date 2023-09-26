package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieResource {

    private final MovieService movieService;


}
