package UIcontrollers;

import Persistencia.UnidadPersistencia;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import objetos.Docente;

import java.sql.Date;

public class VentanaEditDocController {

    private UnidadPersistencia up;

    private boolean respuesta = false;
    private Stage stage;
    private Docente docente;
    @FXML
    private Button btn_cancelar;

    @FXML
    private Button btn_guardar;

    @FXML
    private TextField tf_apellido;

    @FXML
    private TextField tf_direcNotif;

    @FXML
    private TextField tf_dni;

    @FXML
    private DatePicker dp_fechNac;

    @FXML
    private TextField tf_nombre;

    @FXML
    private void initialize(){
        dp_fechNac.setEditable(false);
        btn_guardar.setOnAction(event -> guardarCambios());
        btn_cancelar.setOnAction(event -> {
            respuesta = false;
            stage.close();
        });
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
        tf_nombre.setText(docente.getNombre().trim());
        tf_apellido.setText(docente.getApellido().trim());
        tf_dni.setText(docente.getDni().trim());
        tf_direcNotif.setText(docente.getNotifDir().trim());
        dp_fechNac.setValue(docente.getFechNac().toLocalDate());
    }

   private void guardarCambios() {
        if (tf_nombre.getText().isBlank()
                || tf_apellido.getText().isBlank()
                || tf_dni.getText().isBlank()
                || tf_direcNotif.getText().isBlank()
                || dp_fechNac.toString().isBlank()) {
            Alertas.avisoError("Complete todos los campos");
        } else if (tf_dni.getText().matches("^[0-9]*$")) {
            docente.setNombre(tf_nombre.getText());
            docente.setApellido(tf_apellido.getText());
            docente.setDni(tf_dni.getText());
            docente.setFechNac(Date.valueOf(dp_fechNac.getValue()));
            docente.setNotifDir(tf_direcNotif.getText());
            if(up.actualizarDocente(docente)){
                respuesta = true;
                stage.close();
            }else{
                respuesta = false;
                stage.close();
            }
        }else{
            Alertas.avisoError("Solo se aceptan números en el campo dni");
        }

    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setUp(UnidadPersistencia up) {
        this.up = up;
    }
    public boolean isRespuesta() {
        return respuesta;
    }

}
