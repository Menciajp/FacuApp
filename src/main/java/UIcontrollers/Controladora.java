package UIcontrollers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import objetos.Instituto;

import java.io.IOException;

public abstract class Controladora {
    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }


    public static void setStage(Stage stage) {
        Controladora.stage = stage;
    }


    protected void cambioEscena(String url) throws IOException{

    };
}
