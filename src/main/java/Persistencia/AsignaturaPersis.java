package Persistencia;

import UIcontrollers.Alertas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Asignatura;
import objetos.Docente;
import objetos.Instituto;

import java.util.List;

class AsignaturaPersis {

    protected static boolean existeAsignatura(EntityManagerFactory emf , String nombre, Instituto instituto){
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

    protected static List<Asignatura> traerTodasAsignaturas(EntityManagerFactory emf , Instituto instituto){
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

    protected static boolean ExisteAsignatura(EntityManagerFactory emf, Instituto instituto, Docente docente){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Asignatura> query = em.createQuery("SELECT x FROM Asignatura x WHERE x.instituto = :insti AND x.docente = :docente", Asignatura.class);
            query.setParameter("insti", instituto);
            query.setParameter("docente", docente);
            em.getTransaction().commit();
            Asignatura asignatura = query.getSingleResult();
            em.close();
            System.out.println(asignatura.getId());
            return true;
        }catch (Exception e){
            return false;
        }
    }
    protected static boolean crearAsignatura(EntityManagerFactory emf, Asignatura asignatura){
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
    protected static boolean editarAsignatura(EntityManagerFactory emf, Asignatura asignatura){
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

    protected static boolean borrarAsignatura(EntityManagerFactory emf, Asignatura asignatura){

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
