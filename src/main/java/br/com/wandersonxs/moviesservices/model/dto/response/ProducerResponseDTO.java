package br.com.wandersonxs.moviesservices.model.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProducerResponseDTO {

   List<ProducerDTO> min = new ArrayList<>();
   List<ProducerDTO> max = new ArrayList<>();

}
