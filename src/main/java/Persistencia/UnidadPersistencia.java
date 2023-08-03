package Persistencia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import objetos.Usuario;

public class UnidadPersistencia {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");


    public UnidadPersistencia() {};

    public boolean nuevoUsuario(Usuario user) {
        if(UsuarioPersis.crearUsuario(user,emf)){
            return true;
        }
        return false;
    }
    public Usuario verUsuario(Usuario user){
        Usuario usuario = UsuarioPersis.verUsuario(user, emf);
        return usuario;
    }
}
