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
    @OneToMany(mappedBy = "docente")
    private List<Asignatura> asignaturas;

    @OneToMany(mappedBy = "docente")
    private List<Cargo> cargos;


    public Docente(){}
    public Docente(String dni, String nombre, String apellido, Date fechNac, String notifDir) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechNac = fechNac;
        this.notifDir = notifDir;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechNac() {
        return fechNac;
    }

    public void setFechNac(Date fechNac) {
        this.fechNac = fechNac;
    }

    public String getNotifDir() {
        return notifDir;
    }

    public void setNotifDir(String notifDir) {
        this.notifDir = notifDir;
    }


}
