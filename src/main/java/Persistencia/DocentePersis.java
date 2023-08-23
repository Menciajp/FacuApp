package Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Docente;
import objetos.Instituto;


import java.util.List;

 class DocentePersis {

    protected static  boolean crearDocente(EntityManagerFactory emf, Docente docente) {
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
    protected static boolean existeDocente (EntityManagerFactory emf, String dni){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Docente> query = em.createQuery("SELECT d FROM Docente d WHERE d.dni = :dni", Docente.class);
            query.setParameter("dni", dni);
            Docente docente = query.getSingleResult();
            System.out.println(docente.getId());
            em.getTransaction().commit();
            em.close();
            return true;
        }catch (Exception e){
            System.out.println("No existe");
            return false;
        }
    }
    protected static Docente traerDocente(EntityManagerFactory emf, String dni){
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

    protected static List<Docente> traerTodos(EntityManagerFactory emf, Instituto instituto){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Docente> query = em.createQuery("SELECT c.docente FROM Cargo c WHERE c.instituto = :idInsti", Docente.class);
        query.setParameter("idInsti", instituto);
        em.getTransaction().commit();
        List<Docente> docentes = query.getResultList();
        em.close();
        return docentes;

    }

    protected static Boolean eliminarDocente(EntityManagerFactory emf, Docente docente) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Docente docenteViejo = em.find(Docente.class, docente.getId());
            if (docenteViejo != null) {
                em.remove(docenteViejo);
                em.getTransaction().commit();
                em.close();
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    protected static List<Docente> TraerdocentesOtroInstituto(EntityManagerFactory emf, Instituto instituto){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Docente> query = em.createQuery("SELECT d FROM Docente d WHERE " +
                "d NOT IN (SELECT c.docente FROM Cargo c WHERE c.instituto = :instituto)", Docente.class);
        query.setParameter("instituto", instituto);
        em.getTransaction().commit();
        List<Docente> docentes = query.getResultList();
        em.close();
        return docentes;

    }


}

