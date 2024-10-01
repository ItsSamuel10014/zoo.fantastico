package com.lab.zoofantastico.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data // hace por default los setters y getters
@NoArgsConstructor


public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long zone_id;
    private String name;
    private String description;
    private int capacity;

    @OneToMany(mappedBy = "zone")
    @JsonBackReference //otorga al json como manejar la referencias evitando bucles
    private List<Creature> creatures;

}
