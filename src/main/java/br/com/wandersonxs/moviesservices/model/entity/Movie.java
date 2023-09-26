package br.com.wandersonxs.moviesservices.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movie")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String studio;

    @ManyToMany
    @JoinTable(
            name="movie_producer",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private List<Producer> producers;

    @Column(name ="movie_year")
    private Integer year;
    private boolean winner;

}
