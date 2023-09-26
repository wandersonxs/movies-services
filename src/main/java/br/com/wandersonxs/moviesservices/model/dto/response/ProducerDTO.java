package br.com.wandersonxs.moviesservices.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProducerDTO {

    private String producer;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
