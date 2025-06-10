package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import mx.edu.uacm.is.slt.ds.dst.modelos.Tarea;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.io.IOException;

public class DetallesOperacionController {

    @FXML
    private Label id;
    @FXML
    private Label nombre;
    @FXML
    private Label descripcion;
    @FXML
    private Label estado;
    @FXML
    private TableView<Tarea> tareasTable;

    private final Gestor gestor = Gestor.getInstance();

    @FXML
    public void initialize() {
        id.setText(String.valueOf(gestor.getOperacionSleccionada().getId()));
        nombre.setText(gestor.getOperacionSleccionada().getNombre());
        descripcion.setText(gestor.getOperacionSleccionada().getDescripcion());
        estado.setText(gestor.getOperacionSleccionada().getEstado());
        tareasTable.setItems(gestor.getOperacionSleccionada().getTareas());
    }

    @FXML
    public void btnRegresar() {
        try {
            gestor.navega("gestion-operaciones.fxml", "Drunk-Tasker");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
