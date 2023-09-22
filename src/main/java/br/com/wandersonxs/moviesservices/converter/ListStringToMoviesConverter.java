package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.entity.Movie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListStringToMoviesConverter implements Converter<List<String>, List<Movie>> {

    @Override
    public List<Movie> convert(List<String> linhas) {
        List<Movie> movies = new ArrayList<>();

          linhas.forEach(linha -> {
            String[] data = linha.split(";");
            if (data.length >= 4) {
                Integer year = validateYear(data);
                if (year != null) {
                    Movie movie =
                            Movie.builder()
                                    .title(data[1])
                                    .studio(data[2])
                                    .producer(data[3])
                                    .year(year)
                                    .winner(validadeWinner(data))
                                    .build();

                    movies.add(movie);
                }


            }
        });
          return movies;
    }

    private Integer validateYear(String[] data) {
        try {
            return Integer.valueOf(data[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean validadeWinner(String[] data) {
        if (data.length < 5) return false;
        return data[4].equalsIgnoreCase("yes");
    }
}
