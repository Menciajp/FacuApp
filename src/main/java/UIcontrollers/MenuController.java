package UIcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MenuController {
    private Stage stage;
    @FXML
    private Button btn_crear;

    @FXML
    private Button btn_creelo;

    @FXML
    private Button btn_docente;

    @FXML
    private Button btn_institutos;

    @FXML
    private Button btn_volver;

    @FXML
    private Button btn_volverMenu;

    @FXML
    private MenuButton mb_institutos;

    @FXML
    private Pane pn_crearInstituto;

    @FXML
    private Pane pn_instSelec;

    @FXML
    private Pane pn_seleccion;

    @FXML
    private TextField tf_crearInst;

    public void cambioPantalla(ActionEvent event){
        if(event.getSource() == btn_volver || event.getSource() ==btn_volverMenu){
            pn_crearInstituto.setVisible(false);
            pn_instSelec.setVisible(false);
            pn_seleccion.setVisible(true);
        }else if (event.getSource() == btn_institutos){
            pn_instSelec.setVisible(true);
            pn_seleccion.setVisible(false);
        }else if(event.getSource() == btn_creelo){
            pn_instSelec.setVisible(false);
            pn_crearInstituto.setVisible(true);
        }
    }
    private void changeScene(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        Parent root = loader.load();

        // Obtener la instancia de la controladora de la ventana cargada
        MenuController menuController = loader.getController();
        // Crear una nueva escena
        Scene scene = new Scene(root);

        // Obtener el escenario actual
        Stage stage = this.stage;

        // Establecer la nueva escena en el escenario
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
