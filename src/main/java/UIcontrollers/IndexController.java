package UIcontrollers;

import Persistencia.UnidadPersistencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import objetos.Usuario;

import java.io.IOException;

public class IndexController {


    @FXML
    private BorderPane bp_login;

    @FXML
    private BorderPane bp_registro;

    @FXML
    private Button btn_ingresar;

    @FXML
    private Button btn_registrarme;

    @FXML
    private Button btn_registrate;

    @FXML
    private Button btn_yaTengo;

    @FXML
    private PasswordField pf_contraseñalg;

    @FXML
    private PasswordField pf_contraseñasu;

    @FXML
    private TextField tf_usuariolg;

    @FXML
    private TextField tf_usuariosu;
    private Stage stage;


    //cambio de login a singUp
    public void cambiarPantalla(ActionEvent event){
        if (event.getSource() == btn_registrate){
            bp_registro.setVisible(true);
            bp_login.setVisible(false);
        } else if(event.getSource() == btn_yaTengo){
            bp_registro.setVisible(false);
            bp_login.setVisible(true);
        }
    }

    //crear Usuario
    public void crearUsuario() throws IOException {
        if(pf_contraseñasu.getText().length()>4) {
            Usuario user = new Usuario(tf_usuariosu.getText(), pf_contraseñasu.getText());
            UnidadPersistencia up = new UnidadPersistencia();
            if (up.nuevoUsuario(user)) {
                Alertas.avisoAccion("Usuario creado correctamente!");
                bp_registro.setVisible(false);
                bp_login.setVisible(true);
            } else {
                Alertas.avisoError("Usurio ya existente.");
            }
        }else{
            Alertas.avisoError("La contraseña debe ser mayor a 4 digitos");
        };
    }
    //login
    public void login() throws IOException {
        if(!(pf_contraseñalg.getText().isEmpty()||tf_usuariolg.getText().isEmpty())){
            Usuario user = new Usuario(tf_usuariolg.getText(),pf_contraseñalg.getText());
            UnidadPersistencia up = new UnidadPersistencia();
            Usuario usuario = up.verUsuario(user);
            if (usuario != null){
                Alertas.avisoAccion("INGRESOOO");
                changeScene();
            }else{
                Alertas.avisoError("Contraseña y/o usuario erroneo.");
            }
        }else{
            Alertas.avisoError("Complete los campos.");
        }
    }
    private void changeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/menu.fxml"));

        Parent root = loader.load();

        // Obtener la instancia de la controladora de la ventana cargada
        MenuController menuController = loader.getController();
        menuController.setStage(this.stage);
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

