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
    private  Cargo cargo;

    private  ObservableList<Docente> listaDocentes;
    private ObservableList<Docente> listaDocenteAjeno;
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
    private Button btn_otroDocente;

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
        btn_crear.setOnAction(event -> {crearAsignatura();});
        //botones de inscribir docente
        btn_inscribirDoc.setOnAction(event -> {
            mostrarPane(btn_inscribirDoc,pn_inscribirDocente);
            actualizarTablaDocentes();
        });
        btn_otroDocente.setOnAction(event -> {
            try {
                Docente docente = ventanaDocentes(listaDocenteAjeno);
                if (docente != null) {
                    tf_nombre.setText(docente.getNombre());
                    tf_apellido.setText(docente.getApellido());
                    tf_dni.setText(docente.getDni());
                    dp_fechNac.setValue(docente.getFechNac().toLocalDate());
                    tf_dirNotif.setText(docente.getNotifDir());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        //Botones de modificar asignatura.
        btn_modifAsig.setOnAction(event -> {
            configComboAsignaturas();
            configComboListener();
            mostrarPane(btn_modifAsig,pn_modifAsignatura);});
        btn_cambiarDoc.setOnAction(event -> {
            actualizarTablaDocentes();
            try {
                Docente docente = ventanaDocentes(listaDocentes);
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
                configTvCargoListener();
        });
        btn_updateCargo.setOnAction(event ->{updateCargo();});

        btn_eliminarCargo.setOnAction(event -> {deleteCargo();});
    }



    @FXML
    private void inscribirDocente(){
        if (camposCompletos()){
            if (getUp().existeDocente(tf_dni.getText())) {
                System.out.println("existe docente");
                Docente docente = getUp().traerDocente(tf_dni.getText());
                if (getUp().existeCargo(docente, instituto)) {
                    Alertas.avisoError("El docente ya existe en el sistema y tiene un cargo en este instituto.");
                } else {
                    if (getUp().crearCargo(Integer.parseInt(tf_hrCargo.getText()), docente, instituto)) {
                        Alertas.avisoAccion("Se creo el cargo para el docente existente.");
                    }
                }
            } else {
                getUp().crearDocente(tf_dni.getText(),tf_nombre.getText(),tf_apellido.getText(), Date.valueOf(dp_fechNac.getValue()),tf_dirNotif.getText());
                Docente docente = getUp().traerDocente(tf_dni.getText());
                getUp().crearCargo(Integer.parseInt(tf_hrCargo.getText()), docente, instituto);
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
        if (tf_hrCargo.getText().isBlank()
            || tf_dni.getText().isBlank()
            || tf_apellido.getText().isBlank()
            || tf_nombre.getText().isBlank()
            || dp_fechNac.toString().isBlank()){
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
        listaDocenteAjeno = getUp().TraerdocentesOtroInstituto(instituto);
        listaDocentes = getUp().traerTodosDocentes(instituto);
        tc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tc_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tv_docentes.setItems(listaDocentes);
    }
    private void crearAsignatura(){
        if (tf_nombreAsignatura.getText().isBlank() || ta_descripcion.getText().isBlank()) {
            Alertas.avisoError("Complete todos los campos.");
        } else if (getUp().existeAsignatura(tf_nombreAsignatura.getText(), instituto)) {
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
                    if( getUp().crearAsignatura(tf_nombreAsignatura.getText(),
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
        Button[] botones = {btn_inscribirDoc, btn_crearAsig, btn_modifAsig, btn_modifCarg};
        Pane[] panes = {pn_inscribirDocente, pn_crearAsignatura, pn_modifAsignatura, pn_modifCargo};

        for (Button boton :
             botones) {
            boton.setDisable(boton == botonSelec);
        }
        for (Pane pane:
             panes) {
            pane.setVisible(pane == paneSelec);
        }
    }
    private Docente ventanaDocentes(ObservableList<Docente> lista) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/ventanaDocentes.fxml"));
        Parent root = loader.load();
        VentanaDocController ventana = loader.getController();
        ventana.setDocentes(lista);
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
        List<Asignatura>asignaturas = getUp().traerTodasAsignaturas(instituto);
        ObservableList<String>listaNombre = FXCollections.observableArrayList();
        for (Asignatura asignatura:
             asignaturas) {
            listaAsignaturas.add(asignatura);
            listaNombre.add(asignatura.getNombre_asignatura());
        }
        listaAsignaturas.sort(Comparator.comparing(Asignatura::getNombre_asignatura));
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
                btn_eliminarAsig.setDisable(false);
                btn_updateAsig.setDisable(false);
                btn_cambiarDoc.setDisable(false);
                int index = cb_asignaturas.getSelectionModel().getSelectedIndex();
                asignatura = listaAsignaturas.get(index);
                tf_modNombreAsig.setText(asignatura.getNombre_asignatura());
                ta_modDescAsig.setText(asignatura.getDescripcion());
                lbl_modDocenteAsig.setText(asignatura.getDocente().getNombre() + " " + asignatura.getDocente().getApellido());
            }else{
                btn_eliminarAsig.setDisable(true);
                btn_updateAsig.setDisable(true);
                btn_cambiarDoc.setDisable(true);
            }
            });
    }
    private void updateAsignatura(){
        if (tf_modNombreAsig.getText().isBlank() && ta_modDescAsig.getText().isBlank()){
            Alertas.avisoError("Complete todos los campos.");
        }else{
            asignatura.setNombre_asignatura(tf_modNombreAsig.getText());
            asignatura.setDescripcion(ta_modDescAsig.getText());
            if(getUp().updateAsignatura(asignatura)){
                Alertas.avisoAccion("Modificacion exitosa");
                configComboAsignaturas();
            }
        }
    }
    private void deleteAsignatura(){
        Alert decision = new Alert(Alert.AlertType.CONFIRMATION);
        decision.setHeaderText("Seguro que desea borrar la siguente asignatura?");
        decision.setContentText(
                "Nombre: " + asignatura.getNombre_asignatura() + "\n"
                        + "Docente: " + asignatura.getDocente().getNombre()
                        + " " + asignatura.getDocente().getApellido() +
                        "\n" + "Descripción: " + asignatura.getDescripcion());
        decision.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (getUp().deleteAsignatura(asignatura)) {
                    Alertas.avisoAccion("Asignatura borrada correctamente");
                    configComboAsignaturas();
                }else{Alertas.avisoAccion("Acci[on cancelada");}
            }
        });

    }

    private void actualizarTablaCargos(){
        listaCargos = getUp().traerTodosCargos(instituto);
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
        tv_modifCargos.getSelectionModel().clearSelection();
    }
    private void configTvCargoListener(){
        tv_modifCargos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                int index = tv_modifCargos.getSelectionModel().getSelectedIndex();
                btn_updateCargo.setDisable(false);
                btn_eliminarCargo.setDisable(false);
                cargo = listaCargos.get(index);
                lbl_modifCdocente.setText(cargo.getDocente().getNombre() + " " + cargo.getDocente().getApellido());
                tf_modifChoras.setText(String.valueOf(cargo.getHoras()));
            }else{
                btn_updateCargo.setDisable(true);
                btn_eliminarCargo.setDisable(true);
                lbl_modifCdocente.setText("");
                tf_modifChoras.setText("");
            }
        });
    }
    private void updateCargo(){
        if(tf_modifChoras.getText().isBlank()){
            Alertas.avisoError("Complete el campo de hora.");
        }else{
            cargo.setHoras(Integer.parseInt(tf_modifChoras.getText()));
            if(getUp().updateCargo(cargo)){
                Alertas.avisoAccion("Cargo actualizado");
                actualizarTablaCargos();
            };
        }
    }
    private void deleteCargo(){
        if(getUp().eliminarCargo(cargo)){
            Alertas.avisoAccion("El cargo se a eliminado con exito");
            actualizarTablaCargos();
        }else{
            Alertas.avisoError("El docente tiene cargos en este instituto.");
        };
    }

    @Override
    protected void cambioEscena(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        Parent root = loader.load();

        // Crear una nueva escena
        Scene scene = new Scene(root);


        // Establecer la nueva escena en el escenario
        getStage().setScene(scene);
        getStage().show();
        getStage().centerOnScreen();
    }

}
