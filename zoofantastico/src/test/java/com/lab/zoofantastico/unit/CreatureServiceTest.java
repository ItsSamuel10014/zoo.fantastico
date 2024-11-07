package com.lab.zoofantastico.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.lab.zoofantastico.Services.CreatureService;
import com.lab.zoofantastico.Entity.Creature;
import com.lab.zoofantastico.Repository.CreatureRepository;

@ActiveProfiles("test")
public class CreatureServiceTest {

    @Mock
    private CreatureRepository creatureRepository;

    @InjectMocks
    private CreatureService creatureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCreature_ShouldReturnSavedCreature() {
        Creature creature = new Creature();
        creature.setName("Fénix");
        when(creatureRepository.save(any(Creature.class))).thenReturn(creature);
        Creature savedCreature = creatureService.createCreature(creature);
        assertNotNull(savedCreature);
        assertEquals("Fénix", savedCreature.getName());
    }

    @Test
    void testGetCreatureById_ShouldReturnCreature() {
        Creature creature = new Creature();
        creature.setId(1L);
        creature.setName("Dragón");
        when(creatureRepository.findById(1L)).thenReturn(Optional.of(creature));
        Creature foundCreature = creatureService.getCreatureById(1L);
        assertNotNull(foundCreature);
        assertEquals("Dragón", foundCreature.getName());
    }

    @Test
    void testUpdateCreature_ShouldReturnUpdatedCreature() {
        Creature existingCreature = new Creature();
        existingCreature.setId(1L);
        existingCreature.setName("Unicornio");

        Creature updatedCreature = new Creature();
        updatedCreature.setId(1L);
        updatedCreature.setName("Pegaso");

        when(creatureRepository.findById(1L)).thenReturn(Optional.of(existingCreature));
        when(creatureRepository.save(any(Creature.class))).thenReturn(updatedCreature);

        Creature result = creatureService.updateCreature(1L, updatedCreature);
        assertNotNull(result);
        assertEquals("Pegaso", result.getName());
    }

    @Test
    void testDeleteCreature_ShouldReturnTrue() {
        Creature creature = new Creature();
        creature.setId(1L);
        when(creatureRepository.findById(1L)).thenReturn(Optional.of(creature));
        doNothing().when(creatureRepository).delete(creature);
        boolean isDeleted = creatureService.deleteCreature(1L);
        assertTrue(isDeleted);
        verify(creatureRepository, times(1)).delete(creature);
    }

    @Test
    void testDeleteCreature_ShouldThrowException_WhenCreatureIsCritical() {
        Creature creature = new Creature();
        creature.setId(1L);
        creature.setName("Griffin");
        creature.setHealthStatus("critical");

        when(creatureRepository.findById(1L)).thenReturn(Optional.of(creature));

        Exception exception = assertThrows(IllegalStateException.class, ()
                -> creatureService.deleteCreature(1L));

        String expectedMessage = "Cannot delete a creature in critical health";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testCreateCreature_ShouldThrowException_WhenSizeIsNegative() {
        Creature creature = new Creature();
        creature.setName("Invalid Creature");
        creature.setSize(-1);
        creature.setDangerLevel(5);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> creatureService.createCreature(creature));

        String expectedMessage = "Invalid parameters: Size must be non-negative";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testCreateCreature_ShouldThrowException_WhenDangerLevelIsOutOfRange() {
        Creature creature = new Creature();
        creature.setName("Invalid Creature");
        creature.setSize(5);
        creature.setDangerLevel(11);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> creatureService.createCreature(creature));

        String expectedMessage = "Invalid parameters: Danger level must be between 1 and 10";
        assertEquals(expectedMessage, exception.getMessage());
    }



}
