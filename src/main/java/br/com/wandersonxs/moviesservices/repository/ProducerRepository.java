package br.com.wandersonxs.moviesservices.repository;

import br.com.wandersonxs.moviesservices.model.entity.Producer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    @Query(value = "select * from producer where name in(:names)", nativeQuery = true)
    List<Producer> findByNameIn(List<String> names);

    @Query(value = "select p.id, p.name, count(p.name) from producer p " +
            " inner join movie_producer mp on p.id = mp.producer_id " +
            " inner join movie m on mp.movie_id = m.id " +
            " where m.winner = true " +
            " group by p.id, p.name " +
            " having count(*) > 1 ", nativeQuery = true)
    List<Producer> findByWinnersMoreThanOnce();

    @Query(value = "select p.id, p.name from producer p " +
            " where " +
            " (upper(p.name) like UPPER('%'||:name||'%') or :name is null) " , nativeQuery = true)
    Page<Producer> findByLikeSearch(String name, Pageable page);
}
