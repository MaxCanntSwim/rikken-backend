package com.MaxEradus.rikken.Repos;

import com.MaxEradus.rikken.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
