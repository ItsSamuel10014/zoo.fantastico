package com.lab.zoofantastico.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lab.zoofantastico.Entity.Creature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.lab.zoofantastico.Services.ZoneService;
import com.lab.zoofantastico.Entity.Zone;
import com.lab.zoofantastico.Repository.ZoneRepository;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")

public class ZoneServiceTest {

    @Mock
    private ZoneRepository zoneRepository;

    @InjectMocks
    private ZoneService zoneService;

    @Test
    void testCreateZone_ShouldReturnSavedZone() {
        Zone zone = new Zone();
        zone.setName("Mountain");
        when(zoneRepository.save(any(Zone.class))).thenReturn(zone);
        Zone savedZone = zoneService.createZone(zone);
        assertNotNull(savedZone);
        assertEquals("Mountain", savedZone.getName());
    }

    @Test
    void testGetZoneById_ShouldReturnZone() {
        Zone zone = new Zone();
        zone.setZone_id(1L);
        zone.setName("Forest");
        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zone));
        Zone foundZone = zoneService.getZoneById(1L);
        assertNotNull(foundZone);
        assertEquals("Forest", foundZone.getName());
    }

    @Test
    void testUpdateZone_ShouldReturnUpdatedZone() {
        Zone existingZone = new Zone();
        existingZone.setZone_id(1L);
        existingZone.setName("Desert");

        Zone updatedZone = new Zone();
        updatedZone.setZone_id(1L);
        updatedZone.setName("Beach");

        when(zoneRepository.findById(1L)).thenReturn(Optional.of(existingZone));
        when(zoneRepository.save(any(Zone.class))).thenReturn(updatedZone);

        Zone result = zoneService.updateZone(1L, updatedZone);
        assertNotNull(result);
        assertEquals("Beach", result.getName());
    }

    @Test
    void testDeleteZone_ShouldReturnTrue() {
        Zone zone = new Zone();
        zone.setZone_id(1L);
        zone.setCreatures(new ArrayList<>());

        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zone));
        doNothing().when(zoneRepository).delete(zone);

        boolean isDeleted = zoneService.deleteZone(1L);
        assertTrue(isDeleted);
        verify(zoneRepository, times(1)).delete(zone);
    }

    @Test
    void testDeleteZone_ShouldThrowException_WhenZoneHasCreatures() {
        Zone zone = new Zone();
        zone.setZone_id(1L);
        zone.setCreatures(List.of(new Creature()));

        when(zoneRepository.findById(1L)).thenReturn(Optional.of(zone));

        Exception exception = assertThrows(IllegalStateException.class, () -> zoneService.deleteZone(1L));

        String expectedMessage = "Cannot delete a zone with assigned creatures";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
