package objetos;

import jakarta.persistence.*;

@Entity
@AttributeOverride(name = "id",column = @Column(name = "codigo"))
public class Asignatura extends Entidad{
    private String nombre_asignatura;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "instituto_id")
    private Instituto instituto;


    public Asignatura(){}
    public Asignatura(String nombre_asignatura, String descripcion, Docente docente, Instituto instituto) {
        this.nombre_asignatura = nombre_asignatura;
        this.descripcion = descripcion;
        this.docente = docente;
        this.instituto = instituto;
    }

    public String getNombre_asignatura() {
        return nombre_asignatura;
    }

    public void setNombre_asignatura(String nombre_asignatura) {
        this.nombre_asignatura = nombre_asignatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Instituto getInstituto() {
        return instituto;
    }

    public void setInstituto(Instituto instituto) {
        this.instituto = instituto;
    }
}
