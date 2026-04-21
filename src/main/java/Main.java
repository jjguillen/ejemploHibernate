import entities.Recinto;
import org.hibernate.Session;
import services.RecintosDAO;
import utils.HibernateUtil;

public class Main {

    static void main() {

        /*
        Session sesion = HibernateUtil.getSessionFactory().openSession();

        sesion.beginTransaction();

        Recinto r1 = new Recinto(null, "Estadio Nueva Condomina",
                "Murcia", 35000, false);

        sesion.persist(r1); //Persistir en la base de datos, lo graba en bbdd
        r1.setAforo(12000); //Esto va a generar un UPDATE en BBDD


        sesion.getTransaction().commit(); //Termina la transacción y confirma los datoszº
        sesion.close();

        //Aquí podemos tocar r1 que no afecta a BBDD, ya se cerró la sesión
        r1.setAforo(19000);
        */

         Recinto r1 = RecintosDAO.findById(5L);
         if (r1 != null) {
             System.out.println("Recinto encontrado: " + r1.getNombre());
         } else {
             System.out.println("Recinto no encontrado.");
         }

         RecintosDAO.findAll().forEach(System.out::println);

         Recinto r2 = new Recinto(null, "Sala BSide",
                "Murcia", 500, true);
         RecintosDAO.create(r2);

         r2.setAforo(1000);
         RecintosDAO.update(r2);

         RecintosDAO.delete(3L);
         RecintosDAO.delete(13L);

    }


}
