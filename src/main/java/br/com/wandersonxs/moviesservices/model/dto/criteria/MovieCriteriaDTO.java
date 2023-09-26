package br.com.wandersonxs.moviesservices.model.dto.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MovieCriteriaDTO {

    private Long id;
    private String title;
    private String studio;
    private List<Long> producersId;
    private Integer year;
    private Boolean winner;

}
