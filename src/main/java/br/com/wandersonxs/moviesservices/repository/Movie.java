package br.com.wandersonxs.moviesservices.repository;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String studio;
    private String producer;

    @Column(name ="movie_year")
    private Integer year;
    private boolean winner;

}
