package Persistencia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import objetos.Cargo;
import objetos.Docente;
import objetos.Instituto;
import objetos.Usuario;

import java.sql.Date;
import java.util.List;

import static Persistencia.InstitutoPersis.traerInstitutos;

public class UnidadPersistencia {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");


    public UnidadPersistencia() {};

    public boolean nuevoUsuario(Usuario user) {
        if(UsuarioPersis.crear(user,emf)){
            return true;
        }
        return false;
    }
    public Usuario verUsuario(Usuario user){
        Usuario usuario = UsuarioPersis.verUsuario(user, emf);
        return usuario;
    }

    public boolean nuevoInstituto(Instituto inst) {
        if(InstitutoPersis.crearInstituto(inst,emf)){
            return true;
        }
        return false;
    }

    public List<Instituto> verInstitutos(){
        return traerInstitutos(emf);
    }

    public boolean crearDocente(String dni, String nombre, String apellido, Date fechNac, String notifDir){
        Docente docente = new Docente(dni, nombre, apellido, fechNac, notifDir);
        System.out.println(docente);
        return DocentePersis.crearDocente(emf, docente);

    }

    public boolean existeDocente(String dni){
        return DocentePersis.existeDocente(emf, dni);
    }

    public boolean existeCargo(int id_docente,int id_instituto){
        return  CargoPersis.existeCargo(emf, id_docente, id_instituto);
    }

    public boolean crearCargo(int horas, Docente docente, Instituto instituto){
        Cargo cargo = new Cargo(horas, docente, instituto);
        return CargoPersis.crearCargo(emf, cargo);
    }

}
