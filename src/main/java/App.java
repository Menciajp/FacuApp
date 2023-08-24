import Persistencia.UnidadPersistencia;
import UIcontrollers.Controladora;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static UIcontrollers.Controladora.setStage;
import static UIcontrollers.Controladora.setUp;


public  class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //Colocar persistencia desde acá.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/index.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root);
        setStage(stage);
        UnidadPersistencia up = new UnidadPersistencia();
        setUp(up);
        stage.setScene(scene);
        stage.setTitle("FacuApp");
        stage.show();
        stage.setResizable(false);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Controladora.getUp().getEmf().isOpen();
        Controladora.getUp().getEmf().close();
        Controladora.getUp().getEmf().isOpen();
    }

    public static void main(String[] args){
        launch();
    }

}
