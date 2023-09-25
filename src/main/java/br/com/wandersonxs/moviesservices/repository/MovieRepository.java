package br.com.wandersonxs.moviesservices.repository;

import br.com.wandersonxs.moviesservices.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select m.id, m.title, m.studio , m.winner, m.producer, m.movie_year from movie m where producer in ( " +
            "select m1.producer from movie m1 " +
            "where m1.winner = true " +
            "group by m1.producer " +
            "having count(*) > 1 " +
            ")  order by m.producer , m.movie_year;", nativeQuery = true)
    List<Movie> findByWinners();

}
