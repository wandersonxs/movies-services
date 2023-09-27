package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.bootstrap.Bootstrap;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.ProducerResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import br.com.wandersonxs.moviesservices.repository.MovieRepository;
import br.com.wandersonxs.moviesservices.repository.ProducerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProducerResourceTest {

    @Autowired
    private Bootstrap bootstrap;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String PRODUCER_BASE_URL = "/v1/producers";

    private void cleanAndLoadDatabase(String filename) throws Exception {
        movieRepository.deleteAll();
        producerRepository.deleteAll();
        bootstrap.loadInitialDatabase(filename);
    }

    @Test
    @DisplayName(value = "Given a valid csv but with a movie without composer when the application's bootstrap then ignore movie not saving it.")
    public void givenDefaultCsvLoaded_whenGetProducersWinners_Then_IgnoreMovieNotSavingIt() throws Exception {

        cleanAndLoadDatabase("/csv/movielist.csv");

        String bodyResponseExpected = """
                {
                    "min": [
                        {
                            "producer": "Bo Derek",
                            "interval": 6,
                            "previousWin": 1984,
                            "followingWin": 1990
                        },
                        {
                            "producer": "Matthew Vaughn",
                            "interval": 13,
                            "previousWin": 2002,
                            "followingWin": 2015
                        }
                    ],
                    "max": [
                        {
                            "producer": "Matthew Vaughn",
                            "interval": 13,
                            "previousWin": 2002,
                            "followingWin": 2015
                        },
                        {
                            "producer": "Bo Derek",
                            "interval": 6,
                            "previousWin": 1984,
                            "followingWin": 1990
                        }
                    ]
                }
                """;

        ResultActions response = mockMvc.perform(get(PRODUCER_BASE_URL + "/movies/winners")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

        // Check the faster producer winner of two prizes
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].producer", CoreMatchers.is("Bo Derek")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].interval", CoreMatchers.is(6)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].previousWin", CoreMatchers.is(1984)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].followingWin", CoreMatchers.is(1990)));

        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].producer", CoreMatchers.is("Matthew Vaughn")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].interval", CoreMatchers.is(13)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].previousWin", CoreMatchers.is(2002)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].followingWin", CoreMatchers.is(2015)));

        // Check the producers with the longest interval of consecutive prizes
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].producer", CoreMatchers.is("Matthew Vaughn")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].interval", CoreMatchers.is(13)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].previousWin", CoreMatchers.is(2002)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].followingWin", CoreMatchers.is(2015)));

        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].producer", CoreMatchers.is("Bo Derek")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].interval", CoreMatchers.is(6)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].previousWin", CoreMatchers.is(1984)));

    }

    @DisplayName("Add Producer")
    @Test
    public void givenProducer_whenAddProducer_thenReturnCreated() throws Exception {
        String body = """
                {
                  "name": "Cleyton Pen"
                }
                """;
        mockMvc.perform(post(PRODUCER_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Cleyton Pen")));
    }

    @DisplayName("Update Producer")
    @Test
    public void givenProducer_whenUpdateProducer_thenReturnOK() throws Exception {

        cleanAndLoadDatabase("/csv/movielist.csv");
        List<Producer> producers = producerRepository.findAll();
        Long id = producers.get(0).getId();

        String body = """
                {
                  "name": "Cleyton Pen"
                }
                """;
        mockMvc.perform(put(PRODUCER_BASE_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Cleyton Pen")));
    }


    @DisplayName("Delete Producer")
    @Test
    public void givenProducerId_whenProducer_thenReturnOK() throws Exception {

        String body = """
                {
                  "name": "Cleyton Pen"
                }
                """;
        ResultActions resultActions = mockMvc.perform(post(PRODUCER_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isCreated());

        ProducerResponseDTO producerResponseDTO = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsString(), ProducerResponseDTO.class);


        mockMvc.perform(delete(PRODUCER_BASE_URL + "/{id}", producerResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }
}
