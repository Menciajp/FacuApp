package Persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import objetos.Usuario;
import java.util.List;


class UsuarioPersis{

    protected static boolean crear(Usuario user, EntityManagerFactory emf) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected static Usuario verUsuario(Usuario user, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("Select s from Usuario s");
        List<Usuario> userlist = query.getResultList();
        for (Usuario usuario:
             userlist) {
            if (usuario.getUsername().equals(user.getUsername()) && usuario.getPassword().equals(user.getPassword())){
                return usuario;
            }
        }
        userlist.clear();
        em.close();
        return null;
    }
}
