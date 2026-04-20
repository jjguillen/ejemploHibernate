import entities.Recinto;
import org.hibernate.Session;
import utils.HibernateUtil;

public class Main {

    static void main() {

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        sesion.beginTransaction();

        Recinto r1 = new Recinto(null, "Palacio de Deportes",
                "Madrid", 15000, true);
        sesion.persist(r1); //Persistir en la base de datos, lo graba en bbdd


        sesion.getTransaction().commit(); //Termina la transacción y confirma los datoszº
        sesion.close();


    }


}
