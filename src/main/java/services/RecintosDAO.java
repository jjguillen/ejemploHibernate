package services;

import entities.Recinto;
import org.hibernate.Session;
import utils.HibernateUtil;

import java.util.List;

public class RecintosDAO {

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



}
