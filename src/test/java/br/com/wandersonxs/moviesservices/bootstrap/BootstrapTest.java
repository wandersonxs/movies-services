package br.com.wandersonxs.moviesservices.bootstrap;

import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import br.com.wandersonxs.moviesservices.repository.MovieRepository;
import br.com.wandersonxs.moviesservices.repository.ProducerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BootstrapTest {

    @Autowired
    private Bootstrap bootstrap;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Test
    @DisplayName(value = "Given a valid csv file when the application's bootstrap then load data into database successfully.")
    public void givenValidCsvFile_whenApplicationBootstrap_Then_LoadInitialDatabaseSuccessfully() throws Exception {

        final String csvFilename = "/csv/movielist.csv";
        movieRepository.deleteAll();
        producerRepository.deleteAll();

        bootstrap.loadInitialDatabase(csvFilename,false);

        List<Movie> movies = movieRepository.findAll();
        List<Producer> producers = producerRepository.findAll();

        assertThat(movies).isNotNull();
        assertThat(producers).isNotNull();
        assertThat(movies.size()).isEqualTo(206);
        assertThat(producers.size()).isEqualTo(367);

    }

    @Test
    @DisplayName(value = "Given an inexistent csv file when the application's bootstrap then throw exception.")
    public void givenInexistentCsvFile_whenApplicationBootstrap_Then_ThrowExceptionAndAbortExecution() {

        final String csvFilename = "/csv/file_not_exist.csv";

        try {
            bootstrap.loadInitialDatabase(csvFilename, false);
        } catch (Exception ex) {
            assertThat(ex.getMessage()).isEqualTo("File not found or error on file reading.");
            return;
        }

        fail("Application should handle the exception");

    }

    @Test
    @DisplayName(value = "Given an invalid csv structure when the application's bootstrap then throw exception.")
    public void givenInvalidCsvStructure_whenApplicationBootstrap_Then_ThrowExceptionAndAbortExecution() {

        final String csvFilename = "/csv/movielist_invalid_structure.csv";

        try {
            bootstrap.loadInitialDatabase(csvFilename, false);
        } catch (Exception ex) {
            assertThat(ex.getMessage()).isEqualTo("Csv structure not valid. Please, check it out and try again.");
            return;
        }

        fail("Application should handle the exception");

    }

    @Test
    @DisplayName(value = "Given a valid csv but with a movie without composer when the application's bootstrap then ignore movie not saving it.")
    public void givenValidCsvButMovieWithoutProducer_whenApplicationBootstrap_Then_IgnoreMovieNotSavingIt() throws Exception {

        final String csvFilename = "/csv/movielist_movie_without_producer.csv";

            movieRepository.deleteAll();
            producerRepository.deleteAll();

            bootstrap.loadInitialDatabase(csvFilename, false);

            List<Movie> movies = movieRepository.findAll();
            List<Producer> producers = producerRepository.findAll();

            assertThat(movies).isNotNull();
            assertThat(producers).isNotNull();
            assertThat(movies.size()).isEqualTo(1);
            assertThat(movies.size()).isEqualTo(1);

    }

}
