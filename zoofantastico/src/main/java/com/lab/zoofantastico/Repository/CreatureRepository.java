package com.lab.zoofantastico.Repository;

import com.lab.zoofantastico.Entity.Creature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreatureRepository extends JpaRepository<Creature, Long> {
}
