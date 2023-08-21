package UIcontrollers;

import Persistencia.UnidadPersistencia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objetos.Instituto;

import java.io.IOException;
import java.util.List;

public class MenuController extends Controladora {
    private List<Instituto> institutos;
    ObservableList<String> nombresInst = FXCollections.observableArrayList();

    @FXML
    private Button btn_crear;

    @FXML
    private Button btn_creelo;

    @FXML
    private Button btn_ingresar;

    @FXML
    private Button btn_volver;

    @FXML
    private ComboBox<String> cb_seleccion;

    @FXML
    private Pane pn_crearInstituto;

    @FXML
    private Pane pn_instSelec;

    @FXML
    private TextField tf_crearInst;

    @FXML
    private void initialize(){
        cargarCbInstitutos();
    }
    public void btn_crear(){
        if(!tf_crearInst.getText().isEmpty()){
            UnidadPersistencia up = new UnidadPersistencia();
            Instituto inst = new Instituto(tf_crearInst.getText());
            if(up.nuevoInstituto(inst)){
                Alertas.avisoAccion("Instituto creado con exito!");
                pn_crearInstituto.setVisible(false);
                pn_instSelec.setVisible(true);
                cargarCbInstitutos();
            }else{
                Alertas.avisoError("El instituto ya existe!");
                pn_crearInstituto.setVisible(false);
                pn_instSelec.setVisible(true);

            }
        }
    }

    public void btn_ingresar() throws IOException {
        try {
            cambioEscena("../fxml/gestion.fxml");
        }catch (Exception e){
            Alertas.avisoError("Seleccione un instituto.");
        }
    }
    public void saltoPantallas(ActionEvent event){
        if(event.getSource() == btn_volver){
            pn_crearInstituto.setVisible(false);
            pn_instSelec.setVisible(true);
        }else if(event.getSource() == btn_creelo){
            pn_instSelec.setVisible(false);
            pn_crearInstituto.setVisible(true);
        }
    }

    private void cargarCbInstitutos(){
        nombresInst.clear();
        UnidadPersistencia up = new UnidadPersistencia();
        this.institutos = up.verInstitutos();
        for (Instituto instituto:
             this.institutos) {
            nombresInst.add(instituto.getNombreInstituto());
        }
        cb_seleccion.setItems(nombresInst);
    }

    @Override
    protected void cambioEscena(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        Parent root = loader.load();

        // Obtener la instancia de la controladora de la ventana cargada
        GestionController gestionController = loader.getController();
        Instituto insti = institutos.get(nombresInst.indexOf(cb_seleccion.getValue()));
        gestionController.setInstituto(insti);
        gestionController.setStage(getStage());
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
