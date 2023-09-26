package br.com.wandersonxs.moviesservices.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieResponseDTO {

    private Long id;
    private String title;
    private String studio;
    private List<ProducerResponseDTO> producers;
    private Integer year;
    private boolean winner;

}
