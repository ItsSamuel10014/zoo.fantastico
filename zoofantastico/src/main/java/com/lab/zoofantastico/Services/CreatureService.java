package com.lab.zoofantastico.Services;

import com.lab.zoofantastico.Entity.Creature;
import com.lab.zoofantastico.Repository.CreatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreatureService {
    private final CreatureRepository creatureRepository;

    @Autowired
    public CreatureService(CreatureRepository creatureRepository) {
        this.creatureRepository = creatureRepository;
    }

    public Creature createCreature(Creature creature) {
        if (creature.getSize() < 0) {
            throw new IllegalArgumentException("Invalid parameters: Size must be non-negative");
        }
        if (creature.getDangerLevel() < 1 || creature.getDangerLevel() > 10) {
            throw new IllegalArgumentException("Invalid parameters: Danger level must be between 1 and 10");
        }
        return creatureRepository.save(creature);
    }

    public List<Creature> getAllCreatures() {
        return creatureRepository.findAll();
    }

    public Creature getCreatureById(Long id) {
        return creatureRepository.findById(id)
                .orElseThrow();
    }

    public Creature updateCreature(Long id, Creature updatedCreature) {
        Creature creature = getCreatureById(id);
        creature.setName(updatedCreature.getName());
        creature.setSpecies(updatedCreature.getSpecies());
        creature.setSize(updatedCreature.getSize());
        creature.setDangerLevel(updatedCreature.getDangerLevel());
        creature.setHealthStatus(updatedCreature.getHealthStatus());
        return creatureRepository.save(creature);
    }

    public boolean deleteCreature(Long id) {
        Creature creature = getCreatureById(id);
        if (!"critical".equals(creature.getHealthStatus())) {
            creatureRepository.delete(creature);
            return true;
        } else {
            throw new IllegalStateException("Cannot delete a creature in critical health");
        }
    }

    private static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String creatureNotFound) {
            super(creatureNotFound);
        }
    }
}