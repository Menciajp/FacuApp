package Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Cargo;
import objetos.Docente;
import objetos.Instituto;

import java.util.List;

public class DocentePersis {

    public static  boolean crearDocente(EntityManagerFactory emf, Docente docente) {
        try{
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(docente);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public static boolean existeDocente (EntityManagerFactory emf, String dni){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Docente> query = em.createQuery("SELECT d FROM Docente d WHERE d.dni = :dni", Docente.class);
            query.setParameter("dni", dni);
            Docente docente = query.getSingleResult();
            System.out.println(docente.getId());
            em.getTransaction().commit();
            em.close();
            return (docente != null);
        }catch (Exception e){
            System.out.println("No existe");
            return false;
        }
    }
    public static Docente traerDocente(EntityManagerFactory emf, String dni){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Docente> query = em.createQuery("SELECT d FROM Docente d WHERE d.dni = :dni", Docente.class);
            query.setParameter("dni", dni);
            Docente docente = query.getSingleResult();
            em.getTransaction().commit();
            em.close();
            return docente;
        }catch (Exception e){
            System.out.println("Error con la extracción del docente.");
            return null;
        }
    }

    public static List<Docente> traerTodos(EntityManagerFactory emf, Instituto instituto){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Docente> query = em.createQuery("SELECT c.docente FROM Cargo c WHERE c.instituto = :idInsti", Docente.class);
        query.setParameter("idInsti", instituto);
        em.getTransaction().commit();
        List<Docente> docentes = query.getResultList();
        em.close();
        return docentes;

    }

}

