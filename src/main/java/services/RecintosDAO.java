package services;

import entities.Recinto;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;

public class RecintosDAO {

    // ── CREATE (Recinto) ──────────────────────────────────────────────────
    public static void create(Recinto recinto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.persist(recinto); // persist es para crear un nuevo registro
            session.getTransaction().commit();
            System.out.println("✅ Recinto guardado con id: " + recinto.getId());
        } catch (Exception e) {
            System.err.println("❌ Error al guardar el recinto: " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // ── READ (por id) ─────────────────────────────────────────────────────
    public static Recinto findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // session.find devuelve null si no encuentra el objeto
            return session.find(Recinto.class, id);
        }
    }

    // ── READ (todos) ─────────────────────────────────────────────────────
    public static List<Recinto> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL: FROM NombreClase (no FROM nombre_tabla)
            return session.createQuery("FROM Recinto", Recinto.class).list();
        }
    }

    // ── UPDATE (Recinto) ──────────────────────────────────────────────────
    public static void update(Recinto recinto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.merge(recinto); // merge actualiza el objeto con el id de ese objeto
            session.getTransaction().commit();
            System.out.println("✅ Recinto actualizado con id: " + recinto.getId());
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar el recinto: " + e.getMessage());
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
            Recinto recinto = session.find(Recinto.class, id);
            if (recinto == null) {
                System.out.println("⚠ No se encontró recinto con id: " + id);
            } else {
                session.beginTransaction();
                session.remove(recinto); // remove elimina el objeto con el id de ese objeto
                session.getTransaction().commit();
                System.out.println("✅ Recinto eliminado con id: " + recinto.getId());
            }
        } catch (Exception e) {
            System.err.println("❌ Error al eliminar el recinto: " + e.getMessage());
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    // ── READ con Eventos (por id) ─────────────────────────────────────────────────────
    public static Recinto findByIdWithEventos(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Recinto recinto = session.find(Recinto.class, id);
            if (recinto != null) {
                //Cargamos eventos de ese recinto en el objeto
                Hibernate.initialize(recinto.getEventos());
                return recinto;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ Error al cargar el recinto con eventos: " + e.getMessage());
            return null;
        } finally {
            session.close();
        }

    }



}

