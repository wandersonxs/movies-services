package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.model.dto.response.MovieDTO;
import br.com.wandersonxs.moviesservices.model.dto.response.MovieResponseDTO;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDTOToMovieResponseDTOConverter implements Converter<List<MovieDTO>, MovieResponseDTO> {

    @Override
    public MovieResponseDTO convert(List<MovieDTO> movieDTOS) {

//        movieDTOS.
        return null;
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
