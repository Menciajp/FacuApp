package objetos;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "legajo_Docente"))
public class Docente extends Entidad {
    @Column(unique = true)
    private String dni;
    private String nombre;
    private String apellido;
    private Date fechNac;
    private String notifDir;


    public Docente(){}
    public Docente(String dni, String nombre, String apellido, Date fechNac, String notifDir) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechNac = fechNac;
        this.notifDir = notifDir;
    }

    @OneToMany(mappedBy = "docente")
    private List<Asignatura> asignaturas;

    @OneToMany(mappedBy = "docente")
    private List<Cargo> cargos;
}
