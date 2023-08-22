import Persistencia.UnidadPersistencia;
import UIcontrollers.Alertas;
import UIcontrollers.Controladora;
import UIcontrollers.IndexController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public  class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //Colocar persistencia desde acá.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/index.fxml"));
        Parent root = fxmlLoader.load();
        scene = new Scene(root);
        Controladora.setStage(stage);
        UnidadPersistencia up = new UnidadPersistencia();
        Controladora.setUp(up);
        stage.setScene(scene);
        stage.setTitle("FacuApp");
        stage.show();
        stage.setResizable(false);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Controladora.getUp().getEmf().close();
    }

    public static void main(String[] args){
        launch();
    }

}
