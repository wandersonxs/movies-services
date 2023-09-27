package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.bootstrap.Bootstrap;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.repository.MovieRepository;
import br.com.wandersonxs.moviesservices.repository.ProducerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MovieResourceTest {

    @Autowired
    private Bootstrap bootstrap;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    private static final String MOVIE_BASE_URL = "/v1/movies";

    private void cleanAndLoadDatabase(String filename) throws Exception {
        movieRepository.deleteAll();
        producerRepository.deleteAll();
        bootstrap.loadInitialDatabase(filename);
    }

    @DisplayName("Add Movie")
    @Test
    public void givenMovie_whenAddMovie_thenReturnCreated() throws Exception {
        String body = """
                {
                  "title": "Final Countdown 4",
                  "studio": "Warner Bros.",
                  "producers": [
                    {
                      "name": "Roberval Magnésio"
                    }
                  ],
                  "year": 2000,
                  "winner": true
                }
                """;
        mockMvc.perform(post(MOVIE_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Final Countdown 4")))
                .andExpect(jsonPath("$.producers[0].name", is("Roberval Magnésio")));
    }

    @DisplayName("Update Movie")
    @Test
    public void givenMovie_whenUpdateMovie_thenReturnOK() throws Exception {

        cleanAndLoadDatabase("/csv/movielist.csv");
        List<Movie> movies = movieRepository.findAll();
        Long id = movies.get(0).getId();

        String body = """
                {
                  "title": "Final Countdown 4",
                  "studio": "Warner Bros.",
                  "producers": [
                    {
                      "name": "Roberval Magnésio Joseca"
                    }
                  ],
                  "year": 2000,
                  "winner": true
                }
                """;
        mockMvc.perform(put(MOVIE_BASE_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Final Countdown 4")))
                .andExpect(jsonPath("$.producers[0].name", is("Roberval Magnésio Joseca")));
    }


    @DisplayName("Delete Movie")
    @Test
    public void givenMovieId_whenMovieDelete_thenReturnOK() throws Exception {

        String body = """
                {
                  "title": "Final Countdown 5",
                  "studio": "Warner Bros.",
                  "producers": [
                    {
                      "name": "Miltinho da Sapucaí"
                    }
                  ],
                  "year": 2000,
                  "winner": true
                }
                """;

        ResultActions resultActions = mockMvc.perform(post(MOVIE_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isCreated());

        MovieResponseDTO movieResponseDTO = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), MovieResponseDTO.class);


        mockMvc.perform(delete(MOVIE_BASE_URL +"/{id}", movieResponseDTO.getId()).
            contentType(MediaType.APPLICATION_JSON)).
            andDo(print()).
            andExpect(status().
            isOk());
        }

    }
