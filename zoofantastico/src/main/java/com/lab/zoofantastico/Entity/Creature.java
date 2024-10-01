package com.lab.zoofantastico.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data // hace por default los setters y getters
@NoArgsConstructor
public class Creature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String species;
    private double size;
    private int dangerLevel;
    private String healthStatus;

    @ManyToOne @JoinColumn(name = "zone_id")
    @JsonBackReference //otorga al json como manejar la referencias evitando bucles
    private Zone zone;
}

