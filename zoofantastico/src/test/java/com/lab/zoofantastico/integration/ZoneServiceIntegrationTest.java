package com.lab.zoofantastico.integration;

import com.lab.zoofantastico.Entity.Creature;
import com.lab.zoofantastico.Repository.CreatureRepository;
import com.lab.zoofantastico.Services.CreatureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ZoneServiceIntegrationTest {

    @Autowired
    private CreatureService creatureService;

    @Autowired
    private CreatureRepository creatureRepository;


    @Test
    void testCreateCreature() {
        Creature creature = new Creature();
        creature.setName("Dragon");
        creature.setSpecies("Fire");
        creature.setSize(10);
        creature.setDangerLevel(5);
        creature.setHealthStatus("healthy");

        Creature savedCreature = creatureService.createCreature(creature);
        assertNotNull(savedCreature);
        assertNotNull(savedCreature.getId());
    }

    @Test
    void testGetAllCreatures() {
        Creature creature1 = new Creature();
        creature1.setName("Unicorn");
        creature1.setSpecies("Mystical");
        creature1.setSize(5);
        creature1.setDangerLevel(2);
        creature1.setHealthStatus("healthy");

        Creature creature2 = new Creature();
        creature2.setName("Griffin");
        creature2.setSpecies("Mythical");
        creature2.setSize(7);
        creature2.setDangerLevel(6);
        creature2.setHealthStatus("healthy");

        creatureService.createCreature(creature1);
        creatureService.createCreature(creature2);

        List<Creature> creatures = creatureService.getAllCreatures();
        assertEquals(2, creatures.size());
    }

    @Test
    void testGetCreatureById() {
        Creature creature = new Creature();
        creature.setName("Phoenix");
        creature.setSpecies("Firebird");
        creature.setSize(8);
        creature.setDangerLevel(4);
        creature.setHealthStatus("immortal");

        Creature savedCreature = creatureService.createCreature(creature);
        Creature foundCreature = creatureService.getCreatureById(savedCreature.getId());

        assertNotNull(foundCreature);
        assertEquals("Phoenix", foundCreature.getName());
    }

    @Test
    void testUpdateCreature() {
        Creature creature = new Creature();
        creature.setName("Mermaid");
        creature.setSpecies("Aquatic");
        creature.setSize(6);
        creature.setDangerLevel(3);
        creature.setHealthStatus("healthy");

        Creature savedCreature = creatureService.createCreature(creature);

        Creature updatedCreature = new Creature();
        updatedCreature.setName("Mermaid");
        updatedCreature.setSpecies("Aquatic");
        updatedCreature.setSize(6);
        updatedCreature.setDangerLevel(2);
        updatedCreature.setHealthStatus("injured");

        Creature result = creatureService.updateCreature(savedCreature.getId(), updatedCreature);

        assertEquals("injured", result.getHealthStatus());
        assertEquals(2, result.getDangerLevel());
    }

    @Test
    void testDeleteCreature() {
        Creature creature = new Creature();
        creature.setName("Basilisk");
        creature.setSpecies("Serpent");
        creature.setSize(9);
        creature.setDangerLevel(7);
        creature.setHealthStatus("healthy");

        Creature savedCreature = creatureService.createCreature(creature);
        assertDoesNotThrow(() -> creatureService.deleteCreature(savedCreature.getId()));

        Optional<Creature> deletedCreature = creatureRepository.findById(savedCreature.getId());
        assertTrue(deletedCreature.isEmpty());
    }

    @Test
    void testDeleteCreatureInCriticalHealth() {
        Creature creature = new Creature();
        creature.setName("Kraken");
        creature.setSpecies("Sea Monster");
        creature.setSize(15);
        creature.setDangerLevel(9);
        creature.setHealthStatus("critical");

        Creature savedCreature = creatureService.createCreature(creature);
        assertThrows(IllegalStateException.class, () -> creatureService.deleteCreature(savedCreature.getId()));
    }
}
