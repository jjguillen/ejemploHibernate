package services;

import entities.Asistente;
import entities.Evento;
import entities.Recinto;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;

public class EventosDAO {

    // ── CREATE (Evento) ──────────────────────────────────────────────────
    public static void create(Evento evento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(evento); // persist es para crear un nuevo registro
            session.getTransaction().commit();
            System.out.println("✅ Evento guardado con id: " + evento.getId());
        } catch (Exception e) {
            System.err.println("❌ Error al guardar el evento: " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // ── READ (por id) ─────────────────────────────────────────────────────
    public static Evento findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // session.find devuelve null si no encuentra el objeto
            return session.find(Evento.class, id);
        }
    }

    // ── READ (todos) ─────────────────────────────────────────────────────
    public static List<Evento> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: FROM NombreClase (no FROM nombre_tabla)
            return session.createQuery("FROM Evento", Evento.class).list();
        }
    }

    // ── READ (por id con asistentes inicializados) ──────────────────────
    public static Evento findByIdWithAsistentes(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Evento evento = session.find(Evento.class, id);
            if (evento != null) {
                // Inicializar la colección de asistentes dentro de la sesión activa
                org.hibernate.Hibernate.initialize(evento.getAsistentes());
            }
            return evento;
        }
    }

    // ── UPDATE (Evento) ──────────────────────────────────────────────────
    public static void update(Evento evento) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.merge(evento); // merge actualiza el objeto con el id de ese objeto
            session.getTransaction().commit();
            System.out.println("✅ Evento actualizado con id: " + evento.getId());
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar el evento: " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // ── DELETE (Long id) ──────────────────────────────────────────────────
    public static void delete(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            //Buscar el objeto por id para eliminarlo
            Evento evento = session.find(Evento.class, id);
            if (evento == null) {
                System.out.println("⚠ No se encontró evento con id: " + id);
            } else {
                session.beginTransaction();
                session.remove(evento); // remove elimina el objeto con el id de ese objeto
                session.getTransaction().commit();
                System.out.println("✅ Evento eliminado con id: " + evento.getId());
            }
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar el evento: " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // ── CONSULTAS HQL ──────────────────────────────────────────────────

    public static List<Evento> findByEstadoProgramado() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Evento e WHERE e.estado = 'PROGRAMADO'",
                    Evento.class).list();
        }
    }

    public static List<Evento> findOrderByFecha() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Evento e ORDER BY e.fechaEvento ASC",
                    Evento.class).list();
        }
    }

    public static List<Evento> findByCategory(String category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Evento e WHERE e.categoria = :categ",
                    Evento.class)
                    .setParameter("categ", category) //Evitar SQL Injection
                    .list();
        }
    }

    public static List<Evento> getEventoByCiudadRecinto(String city) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Evento e JOIN FETCH e.recinto r " +
                                    "WHERE r.ciudad = :ciudad",
                            Evento.class)
                    .setParameter("ciudad", city) //Evitar SQL Injection
                    .list();
        }
    }

    // ── VINCULAR ASISTENTE A EVENTO ─────────────────────────────────────
    public static void addAsistenteToEvento(Long idEvento, Long idAsistente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Evento evento = session.find(Evento.class, idEvento);
            Asistente asistente = session.find(Asistente.class, idAsistente);

            if (evento == null) {
                System.out.println("⚠ No se encontró evento con id: " + idEvento);
            } else if (asistente == null) {
                System.out.println("⚠ No se encontró asistente con id: " + idAsistente);
            } else {
                evento.addAsistente(asistente);
                session.merge(evento);
                session.getTransaction().commit();
                System.out.println("✅ Asistente vinculado al evento correctamente");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al vincular asistente al evento: " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // ── DESVINCULAR ASISTENTE DE EVENTO ─────────────────────────────────
    public static void removeAsistenteFromEvento(Long idEvento, Long idAsistente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Evento evento = session.find(Evento.class, idEvento);
            Asistente asistente = session.find(Asistente.class, idAsistente);

            if (evento == null) {
                System.out.println("⚠ No se encontró evento con id: " + idEvento);
            } else if (asistente == null) {
                System.out.println("⚠ No se encontró asistente con id: " + idAsistente);
            } else {
                evento.removeAsistente(asistente);
                session.merge(evento);
                session.getTransaction().commit();
                System.out.println("✅ Asistente desvinculado del evento correctamente");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al desvincular asistente del evento: " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }
}
