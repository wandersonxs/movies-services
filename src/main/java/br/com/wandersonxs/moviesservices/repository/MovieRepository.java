package br.com.wandersonxs.moviesservices.repository;

import br.com.wandersonxs.moviesservices.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
