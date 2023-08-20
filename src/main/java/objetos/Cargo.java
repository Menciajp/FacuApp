package objetos;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.sql.Date;


@Entity
public class Cargo extends  Entidad{
    private int horas;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;
    @ManyToOne
    @JoinColumn(name = "instituto_id")
    private Instituto instituto;


    public Cargo(){}
    public Cargo(int horas, Docente docente, Instituto instituto) {
        this.horas = horas;
        this.docente = docente;
        this.instituto = instituto;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
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
