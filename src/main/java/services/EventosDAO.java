package services;

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







}
