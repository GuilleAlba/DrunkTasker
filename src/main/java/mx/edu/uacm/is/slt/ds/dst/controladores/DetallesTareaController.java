package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.io.IOException;

public class DetallesTareaController {
    @FXML
    private Label tareaNombre;
    private final Gestor gestor = Gestor.getInstance();

    public void initialize() {
        tareaNombre.setText(gestor.getTereaSeleccionada().getNombre());
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
