package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.io.IOException;

public class DetallesTareaController {
    @FXML
    private Label id;
    @FXML
    private Label nombre;
    @FXML
    private Label descripcion;
    @FXML
    private Label estado;
    @FXML
    private Label prioridad;
    @FXML
    private Label pausable;
    private final Gestor gestor = Gestor.getInstance();

    public void initialize() {
        id.setText(String.valueOf(gestor.getTereaSeleccionada().getId()));
        nombre.setText(gestor.getTereaSeleccionada().getNombre());
        descripcion.setText(gestor.getTereaSeleccionada().getDescripcion());
        estado.setText(gestor.getTereaSeleccionada().getEstado());
        prioridad.setText(gestor.getTereaSeleccionada().getPrioridad());
        pausable.setText(gestor.getTereaSeleccionada().isPausa() ? "SI" : "NO");
    }
    @FXML
    public void btnRegresar() {
        try {
            gestor.navega("crea-operacion.fxml", "Drunk-Tasker");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
