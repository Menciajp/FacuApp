package Persistencia;

import UIcontrollers.Alertas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Docente;

public class DocentePersis {

    public static  boolean crearDocente(EntityManagerFactory emf, Docente docente) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(docente);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }

    }
    public static boolean existeDocente (EntityManagerFactory emf, String dni){
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Docente> query = em.createQuery("SELECT d FROM Docente d WHERE d.dni = :dni", Docente.class);
            query.setParameter("dni", dni);
            Docente docente = query.getSingleResult();
            System.out.println(docente.getId());
            em.getTransaction().commit();
            em.close();
            return (docente != null);

    }

}

