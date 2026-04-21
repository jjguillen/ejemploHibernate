package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "eventos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Evento {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false, name = "fecha_evento")
    private LocalDate fechaEvento;

    @Column(nullable = false, name = "precio_entrada")
    private Double precioEntrada;

    @Enumerated(EnumType.STRING)
    private EstadoEvento estado;




}
