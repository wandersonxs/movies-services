package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MovieResourceTest {


    @Autowired
    private MovieService movieService;

    @Test
    public void getMovieWinners() {
//        ProducerResponseDTO movieResponseDTO =  movieService.getMovieWinners();
//        movieResponseDTO.getMin().forEach(System.out::println);
//        movieResponseDTO.getMax().forEach(System.out::println);
    }

}
