package UIcontrollers;

import Persistencia.UnidadPersistencia;
import javafx.stage.Stage;


import java.io.IOException;

public abstract class Controladora {
    private static Stage stage;

    private static UnidadPersistencia up;

    public static UnidadPersistencia getUp() {
        return up;
    }

    public static void setUp(UnidadPersistencia up) {
        Controladora.up = up;
    }

    public static Stage getStage() {
        return stage;
    }


    public static void setStage(Stage stage) {
        Controladora.stage = stage;
    }


    protected void cambioEscena(String url) throws IOException{

    };
}
