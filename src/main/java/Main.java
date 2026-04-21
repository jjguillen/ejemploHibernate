import entities.EstadoEvento;
import entities.Evento;
import entities.Recinto;
import org.hibernate.Session;
import services.EventosDAO;
import services.RecintosDAO;
import utils.HibernateUtil;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    static void main() {

        /*
         Recinto r1 = RecintosDAO.findById(5L);
         if (r1 != null) {
             System.out.println("Recinto encontrado: " + r1.getNombre());
         } else {
             System.out.println("Recinto no encontrado.");
         }

         RecintosDAO.findAll().forEach(System.out::println);
         */

         //RecintosDAO.delete(3L);
         //RecintosDAO.delete(13L);

         // Usa findByIdWithEventos para cargar el recinto con sus eventos
         Recinto r2 = RecintosDAO.findByIdWithEventos(6L);

         /*
         Evento ev1 = new Evento(null, "Concierto Rosalía", "Concierto",
                 LocalDate.of(2026,5,5), 120.0,
                 EstadoEvento.PROGRAMADO, r2);
         EventosDAO.create(ev1);

         Evento ev2 = new Evento(null, "Obra de teatro Hamlet",
                 "Teatro",   LocalDate.of(2026,6,10),
                 50.0, EstadoEvento.PROGRAMADO, r2);
         EventosDAO.create(ev2);
         */

         System.out.println(r2);
         r2.getEventos().forEach(System.out::println);

         //Consulta a BBDD con WHERE
         EventosDAO.findByEstadoProgramado().forEach(System.out::println);

         //Consulta a BBDD con todos, y el filtro con Streams
         EventosDAO.findAll().stream()
                 .filter(e -> e.getEstado().equals(EstadoEvento.PROGRAMADO))
                 .forEach(System.out::println);

         EventosDAO.findOrderByFecha().forEach(System.out::println);

         EventosDAO.findByCategory("Teatro").forEach(System.out::println);

         EventosDAO.getEventoByCiudadRecinto("Murcia").forEach(System.out::println);


    }


}
