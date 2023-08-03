package UIcontrollers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {
        private static String titulo = "FacuApp";

        public static void avisoError(String detalle){
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle(titulo);
            alerta.setHeaderText(null);
            alerta.setContentText(detalle);
            alerta.showAndWait();
        }

        public static void avisoAccion(String detalle){
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle(titulo);
            alerta.setHeaderText(null);
            alerta.setContentText(detalle);
            alerta.showAndWait();
        }

}

