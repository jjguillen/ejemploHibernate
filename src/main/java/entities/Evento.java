package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recinto_id")  //FK
    @ToString.Exclude
    private Recinto recinto;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "evento_asistente",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "asistente_id"))
    @ToString.Exclude
    private List<Asistente> asistentes = new ArrayList<>();

    // ── Vincular asistente al evento ──
    public void addAsistente(Asistente asistente) {
        // Evitar LazyInitializationException verificando si está inicializado
        if (asistente != null && !this.asistentes.contains(asistente)) {
            this.asistentes.add(asistente);
            // Solo sincronizar el otro lado si está correctamente inicializado
            if (!asistente.getEventos().contains(this)) {
                asistente.getEventos().add(this);
            }
        }
    }

    // ── Desvincular asistente del evento ──
    public void removeAsistente(Asistente asistente) {
        if (asistente != null && this.asistentes.contains(asistente)) {
            this.asistentes.remove(asistente);
            if (asistente.getEventos().contains(this)) {
                asistente.getEventos().remove(this);
            }
        }
    }

}
