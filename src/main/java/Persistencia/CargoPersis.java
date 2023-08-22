package Persistencia;

import UIcontrollers.Alertas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Asignatura;
import objetos.Cargo;
import objetos.Docente;
import objetos.Instituto;

import java.util.List;

class CargoPersis {

    protected static boolean crearCargo(EntityManagerFactory emf, Cargo cargo){
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
    protected static boolean existeCargo(EntityManagerFactory emf, Docente docente, Instituto instituto) {
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

    protected static List <Cargo> traerTodosCargos(EntityManagerFactory emf, Instituto instituto) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Cargo> query = em.createQuery("SELECT c FROM Cargo c WHERE c.instituto = :idInsti", Cargo.class);
        query.setParameter("idInsti", instituto);
        List<Cargo> retorno = query.getResultList();
        em.getTransaction().commit();
        return retorno;

    }

    protected static boolean editarCargo(EntityManagerFactory emf, Cargo cargo){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Cargo viejoCargo = em.find(Cargo.class,cargo.getId());
            viejoCargo.setHoras(cargo.getHoras());
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            Alertas.avisoError("Error editando");
            return false;
        }
    }

    protected static boolean contarCargoDocente(EntityManagerFactory emf, Docente docente){

            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Cargo> query = em.createQuery("Select c FROM Cargo c WHERE c.docente = :idDocente",Cargo.class);
            query.setParameter("idDocente", docente);
            em.getTransaction().commit();
            int cantidadCargos = query.getResultList().size();
            em.close();
            return (cantidadCargos != 1);


    }
    protected static boolean eliminarCargo(EntityManagerFactory emf, Cargo cargo){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Cargo cargoViejo = em.find(Cargo.class, cargo.getId());
            if (cargoViejo != null) {
                em.remove(cargoViejo);
                em.getTransaction().commit();
                return true;
            }else { return false;}
        }catch (Exception e){
            return false;
        }
    }
}
