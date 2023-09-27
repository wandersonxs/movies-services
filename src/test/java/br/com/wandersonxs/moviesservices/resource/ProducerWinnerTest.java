package br.com.wandersonxs.moviesservices.resource;

import br.com.wandersonxs.moviesservices.bootstrap.Bootstrap;
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
public class ProducerWinnerTest {

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
    @DisplayName(value = "Given the default csv provided to the test, getting the winners.")
    public void givenDefaultCsvLoaded_whenGetProducersWinners_Then_GetWinnersBasedOnRequirements() throws Exception {

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
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].followingWin", CoreMatchers.is(1990)));


    }

    @Test
    @DisplayName(value = "Given another csv movielist_01.csv, getting the winners.")
    public void givenMovieList01CsvLoaded_whenGetProducersWinners_Then_GetWinnersBasedOnRequirements() throws Exception {

        String csvFileContent = """
                1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes
                1985;Can't Stop the Music 2;Associated Film Distribution;Allan Carr;yes
                1988;Cruising;Lorimar Productions, United Artists;Jerry Weintraub,Allan Carr;yes
                """;

        cleanAndLoadDatabase("/csv/movielist_01.csv");

        ResultActions response = mockMvc.perform(get(PRODUCER_BASE_URL + "/movies/winners")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

        // Check the faster producer winner of two prizes
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].producer", CoreMatchers.is("Allan Carr")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].interval", CoreMatchers.is(5)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].previousWin", CoreMatchers.is(1980)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].followingWin", CoreMatchers.is(1985)));

        // Check the producers with the longest interval of consecutive prizes
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].producer", CoreMatchers.is("Allan Carr")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].interval", CoreMatchers.is(5)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].previousWin", CoreMatchers.is(1980)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].followingWin", CoreMatchers.is(1985)));

    }


    @Test
    @DisplayName(value = "Given another csv movielist_02.csv, getting the winners.")
    public void givenMovieList02CsvLoaded_whenGetProducersWinners_Then_GetWinnersBasedOnRequirements() throws Exception {

        String csvFileContent = """
                1980;Can't Stop the Music;Associated Film Distribution;Allan Carr;yes
                1985;Can't Stop the Music 2;Associated Film Distribution;Allan Carr;yes
                1988;Cruising;Lorimar Productions, United Artists;Jerry Weintraub,Allan Carr;yes
                1981;Cruising;Lorimar Productions, United Artists;Jerry Weintraub,Allan Carr;
                1982;Cruising;Lorimar Productions, United Artists;Jerry Weintraub,Allan Carr;yes
                """;

        cleanAndLoadDatabase("/csv/movielist_02.csv");

        ResultActions response = mockMvc.perform(get(PRODUCER_BASE_URL + "/movies/winners")
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

        // Check the faster producer winner of two prizes
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].producer", CoreMatchers.is("Allan Carr")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].interval", CoreMatchers.is(2)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].previousWin", CoreMatchers.is(1980)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[0].followingWin", CoreMatchers.is(1982)));

        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].producer", CoreMatchers.is("Jerry Weintraub")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].interval", CoreMatchers.is(6)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].previousWin", CoreMatchers.is(1982)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.min[1].followingWin", CoreMatchers.is(1988)));

        // Check the producers with the longest interval of consecutive prizes

        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].producer", CoreMatchers.is("Jerry Weintraub")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].interval", CoreMatchers.is(6)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].previousWin", CoreMatchers.is(1982)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[0].followingWin", CoreMatchers.is(1988)));

        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].producer", CoreMatchers.is("Allan Carr")));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].interval", CoreMatchers.is(3)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].previousWin", CoreMatchers.is(1982)));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.max[1].followingWin", CoreMatchers.is(1985)));

    }
}
