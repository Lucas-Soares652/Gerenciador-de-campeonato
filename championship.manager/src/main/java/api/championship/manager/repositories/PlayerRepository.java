package api.championship.manager.repositories;

import api.championship.manager.models.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findByTeamIdAndDeletionDateIsNull(Long id, Pageable pageable);
}
