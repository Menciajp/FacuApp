package UIcontrollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private Stage stage;
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
