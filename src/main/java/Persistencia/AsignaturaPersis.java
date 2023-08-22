package Persistencia;

import UIcontrollers.Alertas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Asignatura;
import objetos.Instituto;

import java.util.List;

public class AsignaturaPersis {

    public static boolean existeAsignatura(EntityManagerFactory emf , String nombre, Instituto instituto){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Asignatura> query = em.createQuery("SELECT x FROM Asignatura x WHERE x.nombre_asignatura = :asig AND x.instituto = :insti", Asignatura.class);
            query.setParameter("asig", nombre);
            query.setParameter("insti", instituto);
            Asignatura asignatura = query.getSingleResult();
            System.out.println(asignatura.getId());
            em.getTransaction().commit();
            em.close();
            return  (asignatura != null);
        }catch (Exception e){
            return false;
        }
    }

    public static List<Asignatura> traerTodasAsignaturas(EntityManagerFactory emf , Instituto instituto){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Asignatura> query = em.createQuery("SELECT x FROM Asignatura x WHERE x.instituto = :insti", Asignatura.class);
            query.setParameter("insti", instituto);
            List <Asignatura> asignaturas = query.getResultList();
            em.getTransaction().commit();
            em.close();
            return  asignaturas;
        }catch (Exception e){
            return null;
        }
    }


    public static boolean crearAsignatura(EntityManagerFactory emf, Asignatura asignatura){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(asignatura);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            Alertas.avisoError("Error");
            return false;
        }
    }

    public static boolean editarAsignatura(EntityManagerFactory emf, Asignatura asignatura){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Asignatura viejaAsig = em.find(Asignatura.class,asignatura.getId());
            viejaAsig.setNombre_asignatura(asignatura.getNombre_asignatura());
            viejaAsig.setDescripcion(asignatura.getDescripcion());
            viejaAsig.setDocente(asignatura.getDocente());
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            Alertas.avisoError("Error editando");
            return false;
        }
    }

    public  static boolean borrarAsignatura(EntityManagerFactory emf, Asignatura asignatura){

        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Asignatura asig = em.find(Asignatura.class, asignatura.getId());
            if (asig != null) {
                em.remove(asig);
                em.getTransaction().commit();
                return true;
            }else { return false;}
        }catch (Exception e){
            return false;
        }
    }


}
