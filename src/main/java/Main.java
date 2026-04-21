import entities.EstadoEvento;
import entities.Evento;
import entities.Recinto;
import org.hibernate.Session;
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


         Recinto r2 = new Recinto(null, "Sala BSide",
                "Murcia", 500, true, new ArrayList<>());
         RecintosDAO.create(r2);

         r2.setAforo(1000);
         RecintosDAO.update(r2);

         RecintosDAO.delete(3L);
         RecintosDAO.delete(13L);

         Evento ev1 = new Evento(null, "Concierto Rosalía", "Concierto",
                 LocalDate.of(2026,5,5), 120.0,
                 EstadoEvento.PROGRAMADO, r2);


    }


}
