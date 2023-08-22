package Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Cargo;
import objetos.Docente;
import objetos.Instituto;

import java.util.List;

public class CargoPersis {

    public static boolean crearCargo(EntityManagerFactory emf, Cargo cargo){
        try{
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
            em.close();
            return true;
        }catch (Exception e){
            return false;
        }

    }
    public static boolean existeCargo(EntityManagerFactory emf, Docente docente, Instituto instituto) {
    try {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Cargo> query = em.createQuery("SELECT c FROM Cargo c WHERE c.docente = :id AND c.instituto = :idInsti", Cargo.class);
        query.setParameter("id", docente);
        query.setParameter("idInsti", instituto);
        Cargo cargo = (Cargo) query.getSingleResult();
        em.getTransaction().commit();
        return cargo != null;
    }catch (Exception e){
        return false;
    }


    }

    public static List <Cargo> traerTodosCargos(EntityManagerFactory emf, Instituto instituto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Cargo> query = em.createQuery("SELECT c FROM Cargo c WHERE c.instituto = :idInsti", Cargo.class);
        query.setParameter("idInsti", instituto);
        List<Cargo> retorno = query.getResultList();
        em.getTransaction().commit();
        return retorno;

    }
}
