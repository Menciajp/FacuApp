package objetos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Instituto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int idInstituto;

    String nombreInstituto;

    //constructores
    public Instituto(String nombreInstituto) {
        this.nombreInstituto = nombreInstituto;
    }

    public Instituto() {
    }

    public int getIdInstituto() {
        return idInstituto;
    }

    public void setIdInstituto(int idInstituto) {
        this.idInstituto = idInstituto;
    }

    public String getNombreInstituto() {
        return nombreInstituto;
    }

    public void setNombreInstituto(String nombreInstituto) {
        this.nombreInstituto = nombreInstituto;
    }
}
