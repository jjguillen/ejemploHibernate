package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asistentes")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Asistente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String email;

    private Integer edad;

    @Column(nullable = false)
    private String telefono;

    @ManyToMany(mappedBy = "asistentes") //Nombre del atributo en la entidad Evento
    @ToString.Exclude
    private List<Evento> eventos = new ArrayList<>();
}
