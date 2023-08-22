package UIcontrollers;

import Persistencia.UnidadPersistencia;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objetos.Asignatura;
import objetos.Cargo;
import objetos.Docente;
import objetos.Instituto;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GestionController extends Controladora{
    private Instituto instituto;
    private  Asignatura asignatura;

    private  ObservableList<Docente> listaDocentes;
    private ObservableList<Asignatura> listaAsignaturas = FXCollections.observableArrayList();
    private ObservableList<Cargo> listaCargos = FXCollections.observableArrayList();

    @FXML
    private Button btn_cambiarDoc;

    @FXML
    private Button btn_crear;

    @FXML
    private Button btn_crearAsig;

    @FXML
    private Button btn_eliminarAsig;

    @FXML
    private Button btn_eliminarCargo;

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
    private Button btn_updateAsig;

    @FXML
    private Button btn_updateCargo;

    @FXML
    private ComboBox<String> cb_asignaturas;

    @FXML
    private DatePicker dp_fechNac;

    @FXML
    private Label lbl_modDocenteAsig;

    @FXML
    private Label lbl_modifCdocente;

    @FXML
    private Label lbl_nombreInsti;

    @FXML
    private Pane pn_crearAsignatura;

    @FXML
    private Pane pn_inscribirDocente;

    @FXML
    private Pane pn_modifAsignatura;

    @FXML
    private Pane pn_modifCargo;

    @FXML
    private TextArea ta_descripcion;
    @FXML
    private TextArea ta_modDescAsig;


    @FXML
    private TableColumn<Cargo, String> tc_modifCapellido;
    @FXML
    private TableColumn<Cargo, String> tc_modifCdni;
    @FXML
    private TableColumn<Cargo, String> tc_modifChoras;
    @FXML
    private TableColumn<Cargo, String> tc_modifCnombre;
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
    private TextField tf_modifChoras;
    @FXML
    private TextField tf_modNombreAsig;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_nombreAsignatura;

    @FXML
    private TableView<Docente> tv_docentes;
    @FXML
    private TableView<Cargo> tv_modifCargos;

    @FXML
    private void initialize(){
        //boton de volver al menu de institutos.
        btn_selecInsti.setOnAction(event -> {
            try {
                cambioEscena("../fxml/menu.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        //Botones de crear asignatura
        btn_crearAsig.setOnAction(event -> {
            actualizarTablaDocentes();
            mostrarPane(btn_crearAsig,pn_crearAsignatura);});
        //botones de inscribir docente
        btn_inscribirDoc.setOnAction(event -> {
            mostrarPane(btn_inscribirDoc,pn_inscribirDocente);
        });
        btn_crear.setOnAction(event -> {crearAsignatura();});
        //Botones de modificar asignatura.
        btn_modifAsig.setOnAction(event -> {
            configComboAsignaturas();
            configComboListener();
            mostrarPane(btn_modifAsig,pn_modifAsignatura);});
        btn_cambiarDoc.setOnAction(event -> {
            actualizarTablaDocentes();
            try {
                Docente docente = ventanaDocentes();
                if (docente != null){
                    asignatura.setDocente(docente);
                    lbl_modDocenteAsig.setText(asignatura.getDocente().getNombre() + " " + asignatura.getDocente().getApellido());
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btn_updateAsig.setOnAction(event -> {updateAsignatura();});
        btn_eliminarAsig.setOnAction(event -> {deleteAsignatura();});
        //botones de modificar cargo
        btn_modifCarg.setOnAction(event -> {mostrarPane(
                btn_modifCarg,pn_modifCargo);
                actualizarTablaCargos();
        });
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
        Pane panes[] = {pn_inscribirDocente,pn_crearAsignatura,pn_modifAsignatura,pn_modifCargo};
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
    private Docente ventanaDocentes() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ventanaDocentes.fxml"));
        Parent root = loader.load();
        VentanaDocController ventana = loader.getController();
        ventana.setDocentes(listaDocentes);
        ventana.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        if (ventana.getDocente() == null){
            Alertas.avisoAccion("Cambio de docente cancelado.");
            return null;
        }else{
            return ventana.getDocente();
        }
    }
    private void configComboAsignaturas(){
        listaAsignaturas.clear();
        UnidadPersistencia up = new UnidadPersistencia();
        List<Asignatura>asignaturas = up.traerTodasAsignaturas(instituto);
        ObservableList<String>listaNombre = FXCollections.observableArrayList();
        for (Asignatura asignatura:
             asignaturas) {
            listaAsignaturas.add(asignatura);
            listaNombre.add(asignatura.getNombre_asignatura());
        }
        Collections.sort(listaAsignaturas, Comparator.comparing(Asignatura::getNombre_asignatura));
        Collections.sort(listaNombre);
        cb_asignaturas.setItems(listaNombre);
        ta_modDescAsig.setText("");
        tf_modNombreAsig.setText("");
        lbl_modDocenteAsig.setText("");
        cb_asignaturas.getSelectionModel().clearSelection();
    }
    private void configComboListener(){
        cb_asignaturas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int index = cb_asignaturas.getSelectionModel().getSelectedIndex();
                asignatura = listaAsignaturas.get(index);
                tf_modNombreAsig.setText(asignatura.getNombre_asignatura());
                ta_modDescAsig.setText(asignatura.getDescripcion());
                lbl_modDocenteAsig.setText(asignatura.getDocente().getNombre() + " " + asignatura.getDocente().getApellido());
            }
            });
    }
    private void updateAsignatura(){
        if (tf_modNombreAsig.getText().isEmpty() && ta_modDescAsig.getText().isEmpty()){
            Alertas.avisoError("Complete todos los campos.");
        }else{
            asignatura.setNombre_asignatura(tf_modNombreAsig.getText());
            asignatura.setDescripcion(ta_modDescAsig.getText());
            UnidadPersistencia up = new UnidadPersistencia();
            if(up.updateAsignatura(asignatura)){
                Alertas.avisoAccion("Modificacion exitosa");
                configComboAsignaturas();
            }
        }
    }
    private void deleteAsignatura(){
        UnidadPersistencia up = new UnidadPersistencia();
        Alert decision = new Alert(Alert.AlertType.CONFIRMATION);
        decision.setHeaderText("Seguro que desea borrar la siguente asignatura?");
        decision.setContentText(
                "Nombre: " + asignatura.getNombre_asignatura() + "\n"
                        + "Docente: " + asignatura.getDocente().getNombre()
                        + " " + asignatura.getDocente().getApellido() +
                        "\n" + "Descripción: " + asignatura.getDescripcion());
        decision.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (up.deleteAsignatura(asignatura)) {
                    Alertas.avisoAccion("Asignatura borrada correctamente");
                    configComboAsignaturas();
                }else{Alertas.avisoAccion("Acci[on cancelada");}
            }
        });

    }

    private void actualizarTablaCargos(){
        UnidadPersistencia up = new UnidadPersistencia();
        listaCargos = up.traerTodosCargos(instituto);
        tc_modifCnombre.setCellValueFactory(valor -> {
            Docente docente = valor.getValue().getDocente();
            return new SimpleStringProperty(docente.getNombre());
        });
        tc_modifCapellido.setCellValueFactory(valor -> {
            Docente docente = valor.getValue().getDocente();
            return new SimpleStringProperty(docente.getApellido());
        });
        tc_modifCdni.setCellValueFactory(valor -> {
            Docente docente = valor.getValue().getDocente();
            return new SimpleStringProperty(docente.getDni());
        });
        tc_modifChoras.setCellValueFactory(new PropertyValueFactory<>("horas"));
        tv_modifCargos.setItems(listaCargos);
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
