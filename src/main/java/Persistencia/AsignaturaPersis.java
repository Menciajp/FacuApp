package Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import objetos.Asignatura;
import objetos.Docente;

public class AsignaturaPersis {

    public static boolean existeAsignatura(EntityManagerFactory emf , String Asignatura){
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            TypedQuery<objetos.Asignatura> query = em.createQuery("SELECT a FROM Asignatura a WHERE a.nombre_Asignatura = :asignatura", Asignatura.class);
            query.setParameter("asignatura", Asignatura);
            Asignatura asignatura = query.getSingleResult();
            System.out.println(asignatura.getId());
            em.getTransaction().commit();
            em.close();
            return (asignatura != null);
        }catch (Exception e){
            System.out.println("No existe");
            return false;
        }
    }
}
