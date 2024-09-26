package Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data // Esto es opcional pueden usarlo o crear los getter y setters
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

    //@ManyToOne
    //@JoinColumn(name = "zone_id")
    //private Zone zone;
}

