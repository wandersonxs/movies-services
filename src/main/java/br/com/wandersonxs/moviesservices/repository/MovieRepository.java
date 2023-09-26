package br.com.wandersonxs.moviesservices.repository;

import br.com.wandersonxs.moviesservices.model.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = "select m.* from producer p " +
            " inner join movie_producer mp on p.id = mp.producer_id " +
            " inner join movie m on mp.movie_id = m.id " +
            " where " +
//            " (m.id = :id or :id = -1)  and " +
            " (upper(m.title) like UPPER('%'||:title||'%') or :title is null)  and " +
            " (upper(m.studio) like UPPER('%'||:studio||'%') or :studio is null)  and " +
            " (p.id in (:producersId) or :producersId is null) and " +
            " (m.movie_year = :year or :year is null)  and " +
            " (m.winner = :winner or :winner is null) "
            , nativeQuery = true)
    Page<Movie> findByLikeSearch(String title, String studio, List<Long> producersId, Integer year, Boolean winner, Pageable page);
}
