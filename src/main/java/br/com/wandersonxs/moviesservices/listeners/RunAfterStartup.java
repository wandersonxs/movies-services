package br.com.wandersonxs.moviesservices.listeners;

import br.com.wandersonxs.moviesservices.converter.ListStringToMoviesConverter;
import br.com.wandersonxs.moviesservices.helper.FileHelper;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RunAfterStartup {

    private final MovieService movieService;
    private final FileHelper fileHelper;
    private final ListStringToMoviesConverter listStringToMoviesConverter;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {

        List<String> linhasCsv = fileHelper.readLinesCsv("/movielist.csv");
        List<Movie> movies = listStringToMoviesConverter.convert(linhasCsv);
        movieService.add(movies);

    }

}
