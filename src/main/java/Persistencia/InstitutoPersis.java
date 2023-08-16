package Persistencia;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import javafx.collections.ObservableList;
import objetos.Instituto;
import objetos.Usuario;

import java.util.List;

public class InstitutoPersis {
    public static boolean crearInstituto(Instituto instituto, EntityManagerFactory emf) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(instituto);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Instituto> traerInstitutos(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("Select s from Instituto s");
        List<Instituto> instList = query.getResultList();
        return instList;
    }
}