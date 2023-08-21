package UIcontrollers;

import Persistencia.UnidadPersistencia;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objetos.Docente;
import objetos.Instituto;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class GestionController extends Controladora{
    private Instituto instituto;

    ObservableList<Docente> listaDocentes;

    @FXML
    private Button btn_crear;

    @FXML
    private Button btn_crearAsig;

    @FXML
    private Button btn_inscribir;

    @FXML
    private Button btn_inscribirDoc;

    @FXML
    private Button btn_modifAsig;

    @FXML
    private Button btn_modifCarg;

    @FXML
    private Button btn_selecInsti;


    @FXML
    private DatePicker dp_fechNac;

    @FXML
    private Label lbl_nombreInsti;

    @FXML
    private Pane pn_crearAsignatura;

    @FXML
    private Pane pn_inscribirDocente;

    @FXML
    private TextArea ta_descripcion;

    @FXML
    private TableColumn<Docente, String> tc_apellido;

    @FXML
    private TableColumn<Docente , String> tc_dni;

    @FXML
    private TableColumn<Docente, String> tc_nombre;

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
    private TextField tf_nombreAsignatura;

    @FXML
    private TableView<Docente> tv_docentes;

    @FXML
    private void initialize(){
        btn_selecInsti.setOnAction(event -> {
            try {
                cambioEscena("../fxml/menu.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btn_crearAsig.setOnAction(event -> {
            actualizarTablaDocentes();
            mostrarPane(btn_crearAsig,pn_crearAsignatura);});
        btn_inscribirDoc.setOnAction(event -> {
            mostrarPane(btn_inscribirDoc,pn_inscribirDocente);
        });

        btn_crear.setOnAction(event -> {crearAsignatura();});



    }



    @FXML
    private void inscribirDocente(){
        UnidadPersistencia up = new UnidadPersistencia();
        if (camposCompletos()){
            if (up.existeDocente(tf_dni.getText())) {
                System.out.println("existe docente");
                Docente docente = up.traerDocente(tf_dni.getText());
                if (up.existeCargo(docente, instituto)) {
                    Alertas.avisoError("El docente ya existe en el sistema y tiene un cargo en este instituto.");
                } else {
                    if (up.crearCargo(Integer.parseInt(tf_hrCargo.getText()), docente, instituto)) {
                        Alertas.avisoAccion("Se creo el cargo para el docente existente.");
                    }
                }
            } else {
                up.crearDocente(tf_dni.getText(),tf_nombre.getText(),tf_apellido.getText(), Date.valueOf(dp_fechNac.getValue()),tf_dirNotif.getText());
                Docente docente = up.traerDocente(tf_dni.getText());
                up.crearCargo(Integer.parseInt(tf_hrCargo.getText()), docente, instituto);
                Alertas.avisoAccion("Docente y cargo creados correctamente.");
            }
        }
    }
    public Instituto getInstituto() {
        return instituto;
    }

    public void setInstituto(Instituto instituto) {
        this.instituto = instituto;
        lbl_nombreInsti.setText(instituto.getNombreInstituto());
    }

    private boolean camposCompletos(){
        if (tf_hrCargo.getText().isEmpty()
            || tf_dni.getText().isEmpty()
            || tf_apellido.getText().isEmpty()
            || tf_nombre.getText().isEmpty()
            || dp_fechNac.toString().isEmpty()){
            Alertas.avisoError("Complete todos los campos");
            return false;
        }else if (tf_hrCargo.getText().matches("^[0-9]*$") && tf_dni.getText().matches("^[0-9]*$")){
            return true;
        }else{
            Alertas.avisoError("Los campos DNI y hora solo aceptan números.");
            return false;
        }

    }

    private void actualizarTablaDocentes(){
        UnidadPersistencia up = new UnidadPersistencia();
        listaDocentes = up.traerTodosDocentes(instituto);
        tc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tc_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tv_docentes.setItems(listaDocentes);
    }
    private void crearAsignatura(){
        UnidadPersistencia up = new UnidadPersistencia();
        if (tf_nombreAsignatura.getText().isEmpty() || ta_descripcion.getText().isEmpty()) {
            Alertas.avisoError("Complete todos los campos.");
        } else if (up.existeAsignatura(tf_nombreAsignatura.getText(), instituto)) {
            Alertas.avisoError("Ya existe la asignatura");

        }else{
            Alert decision = new Alert(Alert.AlertType.CONFIRMATION);
            decision.setHeaderText("Seguro que desea crear una asignatura con la siguiente información?");
            decision.setContentText(
                    "Nombre: " + tf_nombreAsignatura.getText() + "\n"
                    + "Docente: " + listaDocentes.get(tv_docentes.getFocusModel().getFocusedIndex()).getNombre()
                    + " " + listaDocentes.get(tv_docentes.getFocusModel().getFocusedIndex()).getApellido() +
                    "\n" + "Descripción: " + ta_descripcion.getText());
            decision.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    if( up.crearAsignatura(tf_nombreAsignatura.getText(),
                        listaDocentes.get(tv_docentes.getFocusModel().getFocusedIndex()),
                        ta_descripcion.getText(), instituto)){
                            Alertas.avisoAccion("Asignatura creada.");
                    }else{
                        Alertas.avisoError("Error en la creación de la asignatura.");
                    };
                }
            });
        }
    }
    private void mostrarPane(Button botonSelec, Pane paneSelec){
        Button botones[] = {btn_inscribirDoc,btn_crearAsig,btn_modifAsig,btn_modifCarg};
        Pane panes[] = {pn_inscribirDocente,pn_crearAsignatura};
        for (Button boton :
             botones) {
            if (boton == botonSelec){
                boton.setDisable(true);
            }else{
                boton.setDisable(false);
            }
        }
        for (Pane pane:
             panes) {
            if (pane == paneSelec){
                pane.setVisible(true);
            }else{
                pane.setVisible(false);
            }
        }
    }
    @Override
    protected void cambioEscena(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        Parent root = loader.load();

        // Obtener la instancia de la controladora de la ventana cargada
        MenuController menuController = loader.getController();
        menuController.setStage(getStage());
        // Crear una nueva escena
        Scene scene = new Scene(root);

        // Obtener el escenario actual
        Stage stage = getStage();

        // Establecer la nueva escena en el escenario
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
