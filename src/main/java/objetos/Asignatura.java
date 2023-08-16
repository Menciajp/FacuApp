package objetos;

import jakarta.persistence.*;

@Entity
@AttributeOverride(name = "id",column = @Column(name = "codigo"))
public class Asignatura extends Entidad{
    private String nombre_Asignatura;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;


}
