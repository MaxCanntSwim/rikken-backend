package com.MaxEradus.rikken.Repos;

import com.MaxEradus.rikken.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
