
package br.com.wandersonxs.moviesservices.converter;

import br.com.wandersonxs.moviesservices.helper.FileHelper;
import br.com.wandersonxs.moviesservices.model.entity.Movie;
import br.com.wandersonxs.moviesservices.model.entity.Producer;
import br.com.wandersonxs.moviesservices.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StringToMovieConverter implements Converter<String, Movie> {

    private final ProducerService producerService;
    private final FileHelper fileHelper;

    @Override
    public Movie convert(String movieLine) {
        String[] data = movieLine.split(";");

        if (data.length >= 4) {
            Integer year = validateYear(data);

            if (year != null) {
                return
                        Movie.builder()
                                .title(data[1])
                                .studio(data[2])
                                .producers(getProducers(movieLine))
                                .year(year)
                                .winner(validadeWinner(data))
                                .build();
            }
        }
        return null;
    }

    private List<Producer> getProducers(String data) {
        List<String> producerNames = fileHelper.getRawProducers(data);
        return producerService.findByNameIn(producerNames);
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
