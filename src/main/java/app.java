import UIcontrollers.IndexController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public  class app extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/index.fxml"));
        Parent root = fxmlLoader.load();
        IndexController indexController = fxmlLoader.getController();
        scene = new Scene(root);
        indexController.setStage(stage);
        stage.setScene(scene);
        stage.setTitle("FacuApp");
        stage.show();
        stage.setResizable(false);
    }



    public static void main(String[] args){
        launch();
    }

}
