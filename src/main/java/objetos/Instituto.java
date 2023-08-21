package objetos;

import jakarta.persistence.*;

import java.util.List;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "codigo"))
public class Instituto extends Entidad{

    @Column(unique = true)
    String nombreInstituto;

    @OneToMany(mappedBy = "instituto")
    private List<Cargo> cargos;

    @OneToMany(mappedBy = "instituto")
    private List<Asignatura> asignaturas;
    //constructores
    public Instituto(String nombreInstituto) {
        this.nombreInstituto = nombreInstituto;
    }

    public Instituto() {
    }

    public int getIdInstituto() {
        return super.getId();
    }

    public void setIdInstituto(int idInstituto) {
        super.setId(idInstituto);
    }

    public String getNombreInstituto() {
        return nombreInstituto;
    }

    public void setNombreInstituto(String nombreInstituto) {
        this.nombreInstituto = nombreInstituto;
    }
}
