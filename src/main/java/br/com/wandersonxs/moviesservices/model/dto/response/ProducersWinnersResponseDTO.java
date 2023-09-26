package br.com.wandersonxs.moviesservices.model.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProducersWinnersResponseDTO {

   List<ProducerWinnerResponseDTO> min = new ArrayList<>();
   List<ProducerWinnerResponseDTO> max = new ArrayList<>();

}
