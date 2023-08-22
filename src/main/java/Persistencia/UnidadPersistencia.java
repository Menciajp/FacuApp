package Persistencia;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objetos.*;

import java.sql.Date;
import java.util.List;

import static Persistencia.InstitutoPersis.traerInstitutos;

public class UnidadPersistencia {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");

    public EntityManagerFactory getEmf() {
        return emf;
    }

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

    public Docente traerDocente(String dni){ return DocentePersis.traerDocente(emf,dni);}

    public ObservableList<Docente> traerTodosDocentes(Instituto instituto){
        List<Docente> docentes = DocentePersis.traerTodos(emf,instituto);
        ObservableList<Docente> retorno = FXCollections.observableArrayList(docentes);
        return retorno;
    }
    public boolean existeCargo(Docente docente,Instituto instituto){
        return  CargoPersis.existeCargo(emf, docente, instituto);
    }

    public boolean crearCargo(int horas, Docente docente, Instituto instituto){
        Cargo cargo = new Cargo(horas, docente, instituto);
        return CargoPersis.crearCargo(emf, cargo);
    }

    public boolean existeAsignatura(String asignatura, Instituto instituto){
        return AsignaturaPersis.existeAsignatura(emf, asignatura, instituto);
    }

    public boolean crearAsignatura(String nombre, Docente docente, String descripcion, Instituto instituto){
        Asignatura asignatura = new Asignatura(nombre,descripcion,docente, instituto);
        return AsignaturaPersis.crearAsignatura(emf, asignatura);
    }

    public List<Asignatura>traerTodasAsignaturas(Instituto instituto){
        return AsignaturaPersis.traerTodasAsignaturas(emf,instituto);
    }

    public boolean updateAsignatura(Asignatura asignatura){
        return AsignaturaPersis.editarAsignatura(emf,asignatura);
    }

    public boolean deleteAsignatura(Asignatura asignatura){return AsignaturaPersis.borrarAsignatura(emf,asignatura);}

    public ObservableList<Cargo> traerTodosCargos(Instituto instituto) {
        List<Cargo> cargos = CargoPersis.traerTodosCargos(emf,instituto);
        ObservableList<Cargo> retorno = FXCollections.observableArrayList(cargos);
        return retorno;
    }
    public boolean updateCargo(Cargo cargo){return CargoPersis.editarCargo(emf,cargo);}

    public boolean eliminarCargo(Cargo cargo){
        if (AsignaturaPersis.ExisteAsignatura(emf,cargo.getInstituto(),cargo.getDocente())){
            return false;
        }else{
            if (CargoPersis.contarCargoDocente(emf, cargo.getDocente())){
                return CargoPersis.eliminarCargo(emf, cargo);
            }else{
                CargoPersis.eliminarCargo(emf, cargo);
                return DocentePersis.eliminarDocente(emf,cargo.getDocente());
            }
        }
    }
}
