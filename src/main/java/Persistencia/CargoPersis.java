package Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Cargo;
import objetos.Docente;
import objetos.Instituto;

public class CargoPersis {

    public static boolean crearCargo(EntityManagerFactory emf, Cargo cargo){
        try{
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
            em.close();
        }catch (Exception e){
            return false;
        }
        return true;
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
}
