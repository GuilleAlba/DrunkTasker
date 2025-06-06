package mx.edu.uacm.is.slt.ds.dst.controladores;

import javafx.fxml.FXML;
import mx.edu.uacm.is.slt.ds.dst.servicios.Gestor;

import java.io.IOException;

public class AcercaDeController {

    private final Gestor gestor = Gestor.getInstance();
    @FXML
    public void btnRegresar() {
        try {
            gestor.navega("hello-view.fxml", "Drunk-Tasker");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
