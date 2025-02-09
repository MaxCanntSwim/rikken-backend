package com.MaxEradus.rikken.Repos;

import com.MaxEradus.rikken.model.Ronde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RondeRepository extends JpaRepository<Ronde, Long> {
}
