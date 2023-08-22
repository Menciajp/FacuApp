package UIcontrollers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objetos.Docente;


public class VentanaDocController {

    private Stage stage;
    private Docente docente;
    private ObservableList<Docente> docentes;


    @FXML
    private Button btn_cancelar;

    @FXML
    private Button btn_confirmar;

    @FXML
    private Label lbl_docSelec;

    @FXML
    private TableColumn<Docente, String> tc_apellido;

    @FXML
    private TableColumn<Docente, String> tc_dni;

    @FXML
    private TableColumn<Docente, String> tc_nombre;

    @FXML
    private TableView<Docente> tv_docentes;

    @FXML
    public void initialize(){
        tv_docentes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                String docenteSelec = newSelection.getNombre() + " " + newSelection.getApellido();
                lbl_docSelec.setText(docenteSelec);
            }
        });
        btn_cancelar.setOnAction(event -> {
            docente = null;
            stage.close();
            });

        btn_confirmar.setOnAction(event -> {
            docente = docentes.get(tv_docentes.getFocusModel().getFocusedIndex());
            stage.close();
        });
    }


    public Docente getDocente() {
        return docente;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setDocentes(ObservableList<Docente> docentes) {
        this.docentes = docentes;
        cargarTabla();
    }

    private void cargarTabla(){
        tc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_apellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tc_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tv_docentes.setItems(docentes);
    }
}
