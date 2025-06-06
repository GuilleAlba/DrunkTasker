package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.io.IOException;

public class DetallesOperacionController {

    @FXML
    private Label operacionSelec;

    private final Gestor gestor = Gestor.getInstance();

    @FXML
    public void initialize() {
        operacionSelec.setText(gestor.getOperacionSleccionada().getNombre());
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
