package br.com.wandersonxs.moviesservices.model.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDTO {

   List<MovieDTO> min = new ArrayList<>();
   List<MovieDTO> max = new ArrayList<>();

}
