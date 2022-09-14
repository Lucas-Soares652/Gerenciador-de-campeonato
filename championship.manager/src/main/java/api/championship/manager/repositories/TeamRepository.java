package api.championship.manager.repositories;

import api.championship.manager.models.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Page<Team> findByUserId(Long user_id, Pageable pageable);

    @Query(value = "SELECT * FROM tb_team WHERE user_id = :user_id AND " +
            "(name like :search OR abbreviation like :search)", nativeQuery = true)
    List<Team> findByNameOrAbbreviation(Long user_id, String search);

    @Query(value = "SELECT t.* FROM tb_team AS t " +
            "INNER JOIN championship_team AS ct ON ct.championship_id = :championship_id " +
            "WHERE t.user_id = :user_id", nativeQuery = true)
    List<Team> findByUserIdAndChampionshipId(Long user_id, Long championship_id);
}