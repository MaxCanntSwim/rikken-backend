package com.MaxEradus.rikken.Repos;

import com.MaxEradus.rikken.model.Takkie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TakkieRepository extends JpaRepository<Takkie, Long> {
}
