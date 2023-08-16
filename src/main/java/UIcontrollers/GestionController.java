package UIcontrollers;

import Persistencia.UnidadPersistencia;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import objetos.Docente;
import objetos.Instituto;

import java.sql.Date;

public class GestionController extends Controladora{
    private Instituto instituto;

    @FXML
    private Button btn_inscribir;

    @FXML
    private DatePicker dp_fechNac;
    @FXML
    private Label lbl_nombreInsti;

    @FXML
    private Pane pn_inscribirDocente;

    @FXML
    private TextField tf_apellido;

    @FXML
    private TextField tf_dirNotif;

    @FXML
    private TextField tf_dni;

    @FXML
    private TextField tf_hrCargo;

    @FXML
    private TextField tf_nombre;

    @FXML
    private void initialize(){

    }



    @FXML
    private void inscribirDocente(){
        UnidadPersistencia up = new UnidadPersistencia();
        System.out.println(up.existeDocente(tf_dni.getText()));
    }
    public Instituto getInstituto() {
        return instituto;
    }

    public void setInstituto(Instituto instituto) {
        this.instituto = instituto;
        lbl_nombreInsti.setText(instituto.getNombreInstituto());
    }
}
